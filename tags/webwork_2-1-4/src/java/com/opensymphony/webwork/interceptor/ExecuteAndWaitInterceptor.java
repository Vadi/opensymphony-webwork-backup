/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

import java.io.Serializable;
import java.util.Map;


/**
 * Executes an action in a background thread and then returns
 * to the "wait" result. On the next request, if the action
 * is done executing, the original result is sent back. This allows
 * long running actions to execute while you present the user with a
 * "Please, wait" message. The wait JSP should reload itself by using
 * the following HTML in the HEAD area:
 * <p/>
 * <p/>
 * <pre>
 *  &lt;meta http-equiv="refresh" content="5;url=&lyww:url includeParams="'all'" /&gt"/&gt;
 * </pre>
 * <p/>
 * <p/>
 * This will cause the request to be reloaded every 5 seconds. <b>You will need
 * to specify a result of "wait" in your action for this to work.</b>
 *
 * @author <a href="plightbo@yahoo.com">Pat Lightbody</a>
 */
public class ExecuteAndWaitInterceptor implements Interceptor {
    //~ Static fields/initializers /////////////////////////////////////////////

    public static final String KEY = "__execWait";

    //~ Methods ////////////////////////////////////////////////////////////////

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        String name = actionInvocation.getProxy().getActionName();
        Map session = actionInvocation.getInvocationContext().getSession();

        synchronized (session) {
            BackgroundProcess bp = (BackgroundProcess) session.get(KEY + name);

            if (bp == null) {
                bp = new BackgroundProcess(actionInvocation);
                session.put(KEY + name, bp);
            }

            if (!bp.done) {
                return "wait";
            } else {
                session.remove(KEY + name);
                actionInvocation.getStack().push(bp.action);

                return bp.result;
            }
        }
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    class BackgroundProcess implements Serializable {
        Action action;
        ActionInvocation invocation;
        String result;
        boolean done;

        public BackgroundProcess(final ActionInvocation invocation) {
            this.invocation = invocation;
            this.action = invocation.getAction();

            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        result = action.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    done = true;
                }
            });
            t.start();
        }
    }
}

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

    //~ Instance fields ////////////////////////////////////////////////////////

    private int threadPriority = Thread.NORM_PRIORITY;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        String name = actionInvocation.getProxy().getActionName();
        Map session = actionInvocation.getInvocationContext().getSession();

        synchronized (session) {
            BackgroundProcess bp = (BackgroundProcess) session.get(KEY + name);

            if (bp == null) {
                bp = new BackgroundProcess(name + "BackgroundThread", actionInvocation, threadPriority);
                session.put(KEY + name, bp);
            }

            if (!bp.isDone()) {
                actionInvocation.getStack().push(bp.getAction());
                return "wait";
            } else {
                session.remove(KEY + name);
                actionInvocation.getStack().push(bp.getAction());

                // if an exception occured during action execution, throw it here
                if (bp.getException() != null) throw bp.getException();

                return bp.getResult();
            }
        }
    }


    public void destroy() {
    }

    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    static class BackgroundProcess implements Serializable {
        private Action action;
        private ActionInvocation invocation;

        private String result;
        private Exception exception;

        private boolean done;

        public BackgroundProcess(final String threadName, final ActionInvocation invocation, final int threadPriority) {
            this.invocation = invocation;
            this.action = invocation.getAction();

            final Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        result = action.execute();
                    } catch (Exception e) {
                        exception = e;
                    }

                    done = true;
                }
            });
            t.setName(threadName);
            t.setPriority(threadPriority);
            t.start();
        }

        public Action getAction() {
            return action;
        }

        public ActionInvocation getInvocation() {
            return invocation;
        }

        public String getResult() {
            return result;
        }

        public Exception getException() {
            return exception;
        }

        public boolean isDone() {
            return done;
        }
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.config.entities.ResultConfig;
import com.opensymphony.xwork.interceptor.Interceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.Map;


/**
 * <!-- START SNIPPET: description -->
 * TODO: Give a description of the Interceptor.
 * <!-- END SNIPPET: description -->
 *
 * <!-- START SNIPPET: parameters -->
 * TODO: Describe the paramters for this Interceptor.
 * <!-- END SNIPPET: parameters -->
 *
 * <!-- START SNIPPET: extending -->
 * TODO: Discuss some possible extension of the Interceptor.
 * <!-- END SNIPPET: extending -->
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;!-- TODO: Describe how the Interceptor reference will effect execution --&gt;
 * &lt;action name="someAction" class="com.examples.SomeAction"&gt;
 *      TODO: fill in the interceptor reference.
 *     &lt;interceptor-ref name=""/&gt;
 *     &lt;result name="success"&gt;good_result.ftl&lt;/result&gt;
 * &lt;/action&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * Executes an action in a background thread and then returns
 * to the "wait" result. On the next request, if the action
 * is done executing, the original result is sent back. This allows
 * long running actions to execute while you present the user with a
 * "Please, wait" message. The wait JSP should reload itself by using
 * the following HTML in the HEAD area:
 * <p/>
 * <p/>
 * <pre>
 *  &lt;meta http-equiv="refresh" content="5;url=&lt;ww:url includeParams="all" /&gt;"/&gt;
 * </pre>
 * <p/>
 * <p/>
 * This will cause the request to be reloaded every 5 seconds. <b>You will need
 * to specify a result of "wait" in your action for this to work.</b>
 *
 * @author <a href="plightbo@gmail.com">Pat Lightbody</a>
 */
public class ExecuteAndWaitInterceptor implements Interceptor {
    private static final Log LOG = LogFactory.getLog(ExecuteAndWaitInterceptor.class);

    public static final String KEY = "__execWait";

    private int threadPriority = Thread.NORM_PRIORITY;

    public void init() {
    }

    protected BackgroundProcess getNewBackgroundProcess(String name, ActionInvocation actionInvocation, int threadPriority) {
        return new BackgroundProcess(name + "BackgroundThread", actionInvocation, threadPriority);
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionProxy proxy = actionInvocation.getProxy();
        String name = proxy.getActionName();
        ActionContext context = actionInvocation.getInvocationContext();
        Map session = context.getSession();

        synchronized (session) {
            BackgroundProcess bp = (BackgroundProcess) session.get(KEY + name);

            if (bp == null) {
                bp = getNewBackgroundProcess(name, actionInvocation, threadPriority);
                session.put(KEY + name, bp);
            }

            if (!bp.isDone()) {
                actionInvocation.getStack().push(bp.getAction());
                Map results = proxy.getConfig().getResults();
                if (!results.containsKey("wait")) {
                    LOG.warn("ExecuteAndWait interceptor detected that no result named 'wait' is available. " +
                            "Defaulting to a plain built-in wait page. It is highly recommend you provided " +
                            "provide an action-specific or global result named 'wait'! This requires FreeMarker " +
                            "support and won't work if you don't have it installed");
                    // no wait result? hmm -- let's try to do dynamically put it in for you!
                    ResultConfig rc = new ResultConfig("wait", "com.opensymphony.webwork.views.freemarker.FreemarkerResult",
                            Collections.singletonMap("location", "com/opensymphony/webwork/interceptor/wait.ftl"));
                    results.put("wait", rc);
                }

                return "wait";
            } else {
                session.remove(KEY + name);
                actionInvocation.getStack().push(bp.getAction());

                // if an exception occured during action execution, throw it here
                if (bp.getException() != null) {
                    throw bp.getException();
                }

                return bp.getResult();
            }
        }
    }


    public void destroy() {
    }

    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }

}

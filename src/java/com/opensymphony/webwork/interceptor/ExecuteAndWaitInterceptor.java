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
 *
 * The ExecuteAndWaitInterceptor is great for running long-lived actions in the background while showing the user a nice
 * progress meter. This also prevents the HTTP request from timing out when the action takes more than 5 or 10 minutes.
 *
 * <p/> Using this interceptor is pretty straight forward. Assuming that you are including webwork-default.xml, this
 * interceptor is already configured but is not part of any of the default stacks. Because of the nature of this
 * interceptor, it must be the <b>last</b> interceptor in the stack.
 *
 * <p/> This interceptor works on a per-session basis. That means that the same action name (myLongRunningAction, in the
 * above example) cannot be run more than once at a time in a given session. On the initial request or any subsequent
 * requests (before the action has completed), the <b>wait</b> result will be returned. <b>The wait result is
 * responsible for issuing a subsequent request back to the action, giving the effect of a self-updating progress
 * meter</b>.
 *
 * <p/> If no "wait" result is found, WebWork will automatically generate a wait result on the fly. This result is
 * written in FreeMarker and cannot run unless FreeMarker is installed. If you don't wish to deploy with FreeMarker, you
 * must provide your own wait result. This is generally a good thing to do anyway, as the default wait page is very
 * plain.
 *
 * <p/>Whenever the wait result is returned, the <b>action that is currently running in the background will be placed on
 * top of the stack</b>. This allows you to display progress data, such as a count, in the wait page. By making the wait
 * page automatically reload the request to the action (which will be short-circuited by the interceptor), you can give
 * the appearance of an automatic progress meter.
 *
 * <p/><b>Important</b>: Because the action will be running in a seperate thread, you can't use ActionContext because it
 * is a ThreadLocal. This means if you need to access, for example, session data, you need to implement SessionAware
 * rather than calling ActionContext.getSesion().
 *
 * <p/>The thread kicked off by this interceptor will be named in the form <b><u>actionName</u>BrackgroundProcess</b>.
 * For example, the <i>search</i> action would run as a thread named <i>searchBackgroundProcess</i>.
 *
 * <!-- END SNIPPET: description -->
 *
 * <p/> <u>Interceptor parameters:</u>
 *
 * <!-- START SNIPPET: parameters -->
 *
 * <ul>
 *
 * <li>threadPriority (optional) - the priority to assign the thread</li>
 *
 * </ul>
 *
 * <!-- END SNIPPET: parameters -->
 *
 * <p/> <u>Extending the interceptor:</u>
 *
 * <p/>
 *
 * <!-- START SNIPPET: extending -->
 *
 * If you wish to make special preparations before and/or after the invocation of the background thread, you can extend
 * the BackgroundProcess class and implement the beforeInvocation() and afterInvocation() methods. This may be useful
 * for obtaining and releasing resources that the background process will need to execute successfully. To use your
 * background process extension, extend ExecuteAndWaitInterceptor and implement the getNewBackgroundProcess() method.
 *
 * <!-- END SNIPPET: extending -->
 *
 * <p/> <u>Example code:</u>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;action name="someAction" class="com.examples.SomeAction"&gt;
 *     &lt;interceptor-ref name="completeStack"/&gt;
 *     &lt;interceptor-ref name="execAndWait"/&gt;
 *     &lt;result name="wait"&gt;longRunningAction-wait.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;longRunningAction-success.jsp&lt;/result&gt;
 * &lt;/action&gt;
 *
 * &lt;%@ taglib prefix="ww" uri="/webwork" %&gt;
 * &lt;html&gt;
 *   &lt;head&gt;
 *     &lt;title&gt;Please wait&lt;/title&gt;
 *     &lt;meta http-equiv="refresh" content="5;url=&lt;ww:url includeParams="all" /&gt;"/&gt;
 *   &lt;/head&gt;
 *   &lt;body&gt;
 *     Please wait while we process your request.
 *     Click &lt;a href="&lt;ww:url includeParams="all" /&gt;">&lt;/a&gt; if this page does not reload automatically.
 *   &lt;/body&gt;
 * &lt;/html&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author <a href="plightbo@gmail.com">Pat Lightbody</a>
 */
public class ExecuteAndWaitInterceptor implements Interceptor {
    private static final Log LOG = LogFactory.getLog(ExecuteAndWaitInterceptor.class);

    public static final String KEY = "__execWait";
    private static final String WAIT = "wait";

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
                if (!results.containsKey(WAIT)) {
                    LOG.warn("ExecuteAndWait interceptor has detected that no result named 'wait' is available. " +
                            "Defaulting to a plain built-in wait page. It is highly recommend you " +
                            "provide an action-specific or global result named '" + WAIT +
                            "'! This requires FreeMarker support and won't work if you don't have it installed");
                    // no wait result? hmm -- let's try to do dynamically put it in for you!
                    ResultConfig rc = new ResultConfig(WAIT, "com.opensymphony.webwork.views.freemarker.FreemarkerResult",
                            Collections.singletonMap("location", "com/opensymphony/webwork/interceptor/wait.ftl"));
                    results.put(WAIT, rc);
                }

                return WAIT;
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

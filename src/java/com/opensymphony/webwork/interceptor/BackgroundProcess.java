package com.opensymphony.webwork.interceptor;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionInvocation;

import java.io.Serializable;

/**
 * Background thread to be executed by the ExecuteAndWaitInterceptor.
 *
 * @author <a href="plightbo@yahoo.com">Pat Lightbody</a>
 */
public class BackgroundProcess implements Serializable {
    protected Action action;
    protected ActionInvocation invocation;
    protected String result;
    protected Exception exception;
    protected boolean done;

    public BackgroundProcess(String threadName, final ActionInvocation invocation, int threadPriority) {
        this.invocation = invocation;
        this.action = invocation.getAction();

        final Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    beforeInvocation();
                    invocation.getResult();
                    result = invocation.invoke();
                    afterInvocation();
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

    /**
     * called before the background thread determines the result code
     * from the ActionInvocation.
     *
     * @throws Exception any exception thrown will be thrown, in turn, by the ExecuteAndWaitInterceptor
     */
    protected void beforeInvocation() throws Exception {
    }

    /**
     * called after the background thread determines the result code
     * from the ActionInvocation, but before the background thread is
     * marked as done.
     *
     * @throws Exception any exception thrown will be thrown, in turn, by the ExecuteAndWaitInterceptor
     */
    protected void afterInvocation() throws Exception {
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

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.PreResultListener;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Class to replace the action at the top of the stack with the one returned from the server.
 * @author Peter Kelley
 */
public class HTTPClientPreResultListener implements PreResultListener {
    //~ Static fields/initializers /////////////////////////////////////////////

    /** logger for this class */
    private static final Log log = LogFactory.getLog(HTTPClientPreResultListener.class);

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Default constructor
     */
    public HTTPClientPreResultListener() {
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Replace the action at the top of the stack with the one returned from the server. This
     * method assumes that the object at the top of the stack is a
     * {@link ClientRequestInvocation ClientRequestInvocation}.
     * This callback method will be called after the Action execution and before the Result execution.
     *
     * @param invocation The action invocation that is being processed
     * @param resultCode The result of the action invocation
     */
    public void beforeResult(ActionInvocation invocation, String resultCode) {
        OgnlValueStack stack = invocation.getStack();

        if (log.isDebugEnabled()) {
            log.debug("Object at top of stack was: " + stack.peek());
        }

        ClientRequest localAction = (ClientRequest) stack.pop();
        ClientRequestInvocation clientRequestInvocation = (localAction).getClientRequestInvocation();
        Object resultAction = clientRequestInvocation.getResultAction();

        if (log.isDebugEnabled()) {
            log.debug("Replacing " + localAction + " with " + resultAction);
        }

        stack.push(resultAction);
    }
}

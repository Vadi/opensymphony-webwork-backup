/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.util.TokenHelper;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ValidationAware;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.interceptor.Interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * TokenInterceptor
 * @author Jason Carreira
 * Created Apr 2, 2003 11:27:57 PM
 */
public class TokenInterceptor implements Interceptor {
    //~ Static fields/initializers /////////////////////////////////////////////

    public static final String INVALID_TOKEN_CODE = "invalid.token";
    private static final Log LOG = LogFactory.getLog(TokenInterceptor.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Called to let an interceptor clean up any resources it has allocated.
     */
    public void destroy() {
    }

    /**
     * Called after an Interceptor is created, but before any requests are processed using the intercept() methodName. This
     * gives the Interceptor a chance to initialize any needed resources.
     */
    public void init() {
    }

    /**
     * Allows the Interceptor to do some processing on the request before and/or after the rest of the processing of the
     * request by the DefaultActionInvocation or to short-circuit the processing and just return a String return code.
     *
     * @param invocation
     * @return
     * @throws Exception
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Intercepting invocation to check for valid transaction token.");
        }

        Map session = ActionContext.getContext().getSession();

        synchronized (session) {
            if (!TokenHelper.validToken(session)) {
                return handleInvalidToken(invocation);
            }

            return handleValidToken(invocation);
        }
    }

    /**
     * Handles the case of an invalid token
     * @todo make this error message look up from a message bundle
     * @param invocation
     * @return
     */
    protected String handleInvalidToken(ActionInvocation invocation) throws Exception {
        Action action = invocation.getAction();
        String errorMessage = "The form has already been processed or no token was supplied, please try again.";

        if (action instanceof ValidationAware) {
            ((ValidationAware) action).addActionError(errorMessage);
        } else {
            LOG.warn(errorMessage);
        }

        return INVALID_TOKEN_CODE;
    }

    /**
     * Handle the case of a valid token
     * @param invocation
     * @return
     * @throws Exception
     */
    protected String handleValidToken(ActionInvocation invocation) throws Exception {
        return invocation.invoke();
    }
}

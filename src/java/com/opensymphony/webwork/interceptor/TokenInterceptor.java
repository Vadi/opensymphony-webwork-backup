/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.util.TokenHelper;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ValidationAware;
import com.opensymphony.xwork.interceptor.Interceptor;
import com.opensymphony.xwork.util.LocalizedTextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;


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
 * @author Jason Carreira
 */
public class TokenInterceptor implements Interceptor {

    public static final String INVALID_TOKEN_CODE = "invalid.token";
    private static final Log LOG = LogFactory.getLog(TokenInterceptor.class);


    /**
     * Called to let an interceptor clean up any resources it has allocated. Does nothing by default -
     * subclass and overwrite to customize.
     */
    public void destroy() {
    }

    /**
     * Called after an Interceptor is created, but before any requests are processed using the
     * {@link #intercept(ActionInvocation)} method.. This gives the Interceptor a chance to
     * initialize any needed resources. Currently does nothing - subclass and overwrite to customize.
     */
    public void init() {
    }

    /**
     * @param invocation an encapsulation of the action execution state.
     * @throws Exception
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Intercepting invocation to check for valid transaction token.");
        }

        HttpServletRequest request = ServletActionContext.getRequest();

        synchronized (request.getSession(true)) {
            if (!TokenHelper.validToken(request)) {
                return handleInvalidToken(invocation);
            }

            return handleValidToken(invocation);
        }
    }

    /**
     * @param invocation
     * @return
     * @throws Exception
     */
    protected String handleInvalidToken(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();
        String errorMessage = LocalizedTextUtil.findText(this.getClass(), "webwork.messages.invalid.token", ActionContext.getContext().getLocale(), "The form has already been processed or no token was supplied, please try again.", new Object[0]);

        if (action instanceof ValidationAware) {
            ((ValidationAware) action).addActionError(errorMessage);
        } else {
            LOG.warn(errorMessage);
        }

        return INVALID_TOKEN_CODE;
    }

    /**
     * @param invocation
     * @throws Exception
     */
    protected String handleValidToken(ActionInvocation invocation) throws Exception {
        return invocation.invoke();
    }
}

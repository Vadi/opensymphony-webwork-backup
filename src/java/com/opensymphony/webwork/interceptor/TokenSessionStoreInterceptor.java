/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.util.InvocationSessionStore;
import com.opensymphony.webwork.util.TokenHelper;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Jason Carreira
 */
public class TokenSessionStoreInterceptor extends TokenInterceptor {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * @param invocation
     * @return
     * @throws Exception
     */
    protected String handleInvalidToken(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String tokenName = TokenHelper.getTokenName(request);
        String token = TokenHelper.getToken(tokenName, request);

        if ((tokenName != null) && (token != null)) {
            ActionInvocation savedInvocation = InvocationSessionStore.loadInvocation(tokenName, token);

            if (savedInvocation != null) {
                // set the valuestack to the request scope
                OgnlValueStack stack = savedInvocation.getStack();
                ServletActionContext.getRequest().setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, stack);

                Result result = savedInvocation.getResult();

                if ((result != null) && (savedInvocation.getProxy().getExecuteResult())) {
                    result.execute(savedInvocation);
                }

                // turn off execution of this invocations result
                invocation.getProxy().setExecuteResult(false);

                return savedInvocation.getResultCode();
            }
        }

        return INVALID_TOKEN_CODE;
    }

    /**
     * @param invocation
     * @return
     * @throws Exception
     */
    protected String handleValidToken(ActionInvocation invocation) throws Exception {
        // we know the token name and token must be there
        HttpServletRequest request = ServletActionContext.getRequest();
        String key = TokenHelper.getTokenName(request);
        String token = TokenHelper.getToken(key, request);
        InvocationSessionStore.storeInvocation(key, token, invocation);

        return invocation.invoke();
    }
}

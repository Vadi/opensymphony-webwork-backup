/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.util.InvocationSessionStore;
import com.opensymphony.webwork.util.TokenHelper;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;


/**
 * TokenSessionStoreInterceptor
 * @author Jason Carreira
 * Created Apr 14, 2003 10:33:19 PM
 */
public class TokenSessionStoreInterceptor extends TokenInterceptor {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
    * Handles the case of an invalid token
    * @param invocation
    * @return
    */
    protected String handleInvalidToken(ActionInvocation invocation) throws Exception {
        String tokenName = TokenHelper.getTokenName();
        String token = TokenHelper.getToken(tokenName);

        if ((tokenName != null) && (token != null)) {
            ActionInvocation savedInvocation = InvocationSessionStore.loadInvocation(tokenName, token);

            if (savedInvocation != null) {
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
    * Handle the case of a valid token
    * @param invocation
    * @return
    * @throws Exception
    */
    protected String handleValidToken(ActionInvocation invocation) throws Exception {
        // we know the token name and token must be there
        String key = TokenHelper.getTokenName();
        String token = TokenHelper.getToken(key);
        InvocationSessionStore.storeInvocation(key, token, invocation);

        return invocation.invoke();
    }
}

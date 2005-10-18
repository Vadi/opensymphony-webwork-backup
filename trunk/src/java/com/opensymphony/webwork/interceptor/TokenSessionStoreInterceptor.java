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
 * @author Jason Carreira
 */
public class TokenSessionStoreInterceptor extends TokenInterceptor {

    /**
     * @param invocation
     * @return
     * @throws Exception
     */
    protected String handleInvalidToken(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String tokenName = TokenHelper.getTokenName(request);
        String token = TokenHelper.getToken(tokenName, request);

        Map params = invocation.getInvocationContext().getParameters();
        params.remove(tokenName);
        params.remove(TokenHelper.TOKEN_NAME_FIELD);

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

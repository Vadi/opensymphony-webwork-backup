package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

/**
 * PrincipalInterceptor gives access to principal and roles information from action, without
 * making action class tied to HttpServlerRequest.
 *
 * @author Remigijus Bauzys
 * @version $Revision$
 */
public class PrincipalInterceptor extends AroundInterceptor {
    protected void before(ActionInvocation invocation) throws Exception {
        Action action = invocation.getAction();

        if (action instanceof PrincipalAware) {
            ((PrincipalAware) action).setPrincipalProxy(new PrincipalProxy(ServletActionContext.getRequest()));
        }
    }

    protected void after(ActionInvocation dispatcher, String result) throws Exception {
    }
}
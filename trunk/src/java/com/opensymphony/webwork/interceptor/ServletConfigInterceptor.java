/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.WebWorkStatics;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class ServletConfigInterceptor extends AroundInterceptor implements WebWorkStatics {
    //~ Methods ////////////////////////////////////////////////////////////////

    protected void after(ActionInvocation dispatcher, String result) throws Exception {
    }

    protected void before(ActionInvocation invocation) throws Exception {
        Action action = invocation.getAction();
        ActionContext context = ActionContext.getContext();

        if (action instanceof ServletRequestAware) {
            HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
            ((ServletRequestAware) action).setServletRequest(request);
        }

        if (action instanceof ServletResponseAware) {
            HttpServletResponse response = (HttpServletResponse) context.get(HTTP_RESPONSE);
            ((ServletResponseAware) action).setServletResponse(response);
        }

        if (action instanceof ParameterAware) {
            ((ParameterAware) action).setParameters(context.getParameters());
        }

        if (action instanceof SessionAware) {
            ((SessionAware) action).setSession(context.getSession());
        }

        if (action instanceof ApplicationAware) {
            ((ApplicationAware) action).setApplication(context.getApplication());
        }
    }
}

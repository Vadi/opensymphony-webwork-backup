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

        if (action instanceof HttpServletRequestAware) {
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(HTTP_REQUEST);
            ((HttpServletRequestAware) action).setHttpServletRequest(request);
        }

        if (action instanceof HttpServletResponseAware) {
            HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(HTTP_RESPONSE);
            ((HttpServletResponseAware) action).setHttpServletResponse(response);
        }
    }
}

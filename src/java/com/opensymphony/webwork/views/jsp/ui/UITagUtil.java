/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.ApplicationMap;
import com.opensymphony.webwork.dispatcher.ServletDispatcher;
import com.opensymphony.webwork.dispatcher.SessionMap;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;


/**
 * Util class to encapsulate UI tag utils
 */
public class UITagUtil {
    //~ Constructors ///////////////////////////////////////////////////////////

    private UITagUtil() {
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public static OgnlValueStack getValueStack(PageContext pageContext) {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        OgnlValueStack stack = (OgnlValueStack) req.getAttribute("webwork.valueStack");

        if (stack == null) {
            stack = new OgnlValueStack();

            HttpServletResponse res = (HttpServletResponse) pageContext.getResponse();
            Map extraContext = ServletDispatcher.createContextMap(req.getParameterMap(), new SessionMap(req.getSession()), new ApplicationMap(pageContext.getServletContext()), req, res, pageContext.getServletConfig());
            extraContext.put(ServletActionContext.PAGE_CONTEXT, pageContext);
            stack.getContext().putAll(extraContext);
            req.setAttribute("webwork.valueStack", stack);
        }

        return stack;
    }
}

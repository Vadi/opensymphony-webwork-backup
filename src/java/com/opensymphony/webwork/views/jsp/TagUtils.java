/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.util.AttributeMap;
import com.opensymphony.webwork.dispatcher.ApplicationMap;
import com.opensymphony.webwork.dispatcher.RequestMap;
import com.opensymphony.webwork.dispatcher.ServletDispatcher;
import com.opensymphony.webwork.dispatcher.SessionMap;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.ActionContext;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;


/**
 * User: plightbo
 * Date: Oct 17, 2003
 * Time: 7:09:59 AM
 */
public class TagUtils {
    //~ Methods ////////////////////////////////////////////////////////////////

    public static OgnlValueStack getStack(PageContext pageContext) {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        OgnlValueStack stack = (OgnlValueStack) req.getAttribute("webwork.valueStack");

        if (stack == null) {
            stack = new OgnlValueStack();

            HttpServletResponse res = (HttpServletResponse) pageContext.getResponse();
            Map extraContext = ServletDispatcher.createContextMap(new RequestMap(req), req.getParameterMap(), new SessionMap(req.getSession()), new ApplicationMap(pageContext.getServletContext()), req, res, pageContext.getServletConfig());
            extraContext.put(ServletActionContext.PAGE_CONTEXT, pageContext);
            stack.getContext().putAll(extraContext);
            req.setAttribute("webwork.valueStack", stack);

            // also tie this stack/context to the ThreadLocal
            ActionContext.setContext(new ActionContext(stack.getContext()));
        } else {
            // let's make sure that the current page context is in the action context
            Map context = stack.getContext();
            context.put(ServletActionContext.PAGE_CONTEXT, pageContext);
            AttributeMap attrMap = new AttributeMap(context);
            context.put("attr", attrMap);
        }

        return stack;
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork;

import com.opensymphony.webwork.dispatcher.ServletDispatcher;

import com.opensymphony.xwork.ActionContext;

import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;


/**
 * @author <a href="mailto:nightfal@etherlands.net">Erik Beeson</a>
 */
public class ServletActionContext extends ActionContext implements WebWorkStatics {
    //~ Constructors ///////////////////////////////////////////////////////////

    private ServletActionContext(Map context) {
        super(context);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public static PageContext getPageContext() {
        return (PageContext) ActionContext.getContext().get(PAGE_CONTEXT);
    }

    public static void setRequest(HttpServletRequest request) {
        ActionContext.getContext().put(HTTP_REQUEST, request);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) ActionContext.getContext().get(HTTP_REQUEST);
    }

    public static void setResponse(HttpServletResponse response) {
        ActionContext.getContext().put(HTTP_RESPONSE, response);
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) ActionContext.getContext().get(HTTP_RESPONSE);
    }

    public static void setServletConfig(ServletConfig config) {
        ActionContext.getContext().put(SERVLET_CONFIG, config);
    }

    public static ServletConfig getServletConfig() {
        return (ServletConfig) ActionContext.getContext().get(SERVLET_CONFIG);
    }

    public static ServletContext getServletContext() {
        return getServletConfig().getServletContext();
    }

    public static ServletDispatcher getServletDispatcher() {
        return (ServletDispatcher) ActionContext.getContext().get(SERLVET_DISPATCHER);
    }
}

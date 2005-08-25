/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.servlet;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * When WebWorkPortlet doService() include the JSP resource, then
 * WebWorkJSPServlet was invoked.
 * <p/>
 * Now we can use WebWorkJSPServlet to render the result JSP with the stackValue
 * stored in ActionContext after WebWork Action was executed.
 * <p/>
 * Because PortletRequestDispatcher include() method in WebWorkPortlet
 * doService() can not filtered by ServletFilter, we must add the SiteMesh
 * function here if want SiteMesh decoration support.
 * <p/>
 *
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn"> Henry Hu </a>
 * @since 2005-7-6
 */
public class WebWorkJSPServlet extends WebWorkAbstractServlet {

    //~ Constructors
    // ///////////////////////////////////////////////////////////

    public WebWorkJSPServlet() {
        super();
    }

    //~ Methods
    // ////////////////////////////////////////////////////////////////

    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String location = (String) ActionContext.getContext().get("template");

        OgnlValueStack stack = ActionContext.getContext().getValueStack();

//      Moved to Parent class doFilter()      
//        request.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY,stack);
//        stack.getContext().put(ServletActionContext.HTTP_REQUEST, request);
//        stack.getContext().put(ServletActionContext.HTTP_RESPONSE, response);
//        stack.getContext().put(ServletActionContext.SERVLET_CONTEXT, getServletContext());

        RequestDispatcher dispatcher = request.getRequestDispatcher(location);

        // if the view doesn't exist, let's do a 404
        if (dispatcher == null) {
            response.sendError(404, "result '" + location + "' not found");
            return;
        }

        dispatcher.include(request, response);

    }

}
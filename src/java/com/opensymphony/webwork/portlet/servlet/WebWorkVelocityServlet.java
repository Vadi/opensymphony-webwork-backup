/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.servlet;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.portlet.velocity.VelocityManager;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.Writer;

/**
 * When WebWorkPortlet doService() include the .vm resource, then
 * WebWorkVelocityServlet was invoked.
 * <p/>
 * Now we can use WebWorkVelocityServlet to render the result velocity VM with
 * the stackValue stored in ActionContext after WebWork Action was executed.
 * <p/>
 * Because PortletRequestDispatcher include() method in WebWorkPortlet
 * doService() can not filtered by ServletFilter, we must add the SiteMesh
 * function here if want SiteMesh decoration support.
 * <p/>
 *
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn"> Henry Hu </a>
 * @since 2005-7-6
 */
public class WebWorkVelocityServlet extends WebWorkAbstractServlet {

    //~ Constructors
    // ///////////////////////////////////////////////////////////

    public WebWorkVelocityServlet() {
        super();
    }

    //~ Methods
    // ////////////////////////////////////////////////////////////////

    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // save the old PageContext
        PageContext oldPageContext = ServletActionContext.getPageContext();

        // create a new PageContext
        JspFactory jspFactory = JspFactory.getDefaultFactory();

        PageContext pageContext = jspFactory.getPageContext(this, request, response, null, true, 8192, true);

        // put the new PageContext into ActionContext
        ActionContext actionContext = ActionContext.getContext();

        actionContext.put(ServletActionContext.PAGE_CONTEXT, pageContext);

        /*
         * Must put the stack into HttpServletRequest, because the WebWork JSP
         * Taglib will use it to judge whether there are some errors in stack.
         */
        OgnlValueStack stack = ActionContext.getContext().getValueStack();

//Moved to Parent class doFilter()       
//        request.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY,stack);
//        stack.getContext().put(ServletActionContext.HTTP_REQUEST, request);
//        stack.getContext().put(ServletActionContext.HTTP_RESPONSE, response);
//        stack.getContext().put(ServletActionContext.SERVLET_CONTEXT, getServletContext());

        //Moved to WebWorkPortlet doService()

        VelocityManager velocityManager = VelocityManager.getInstance();
        Context resultContext = velocityManager.createContext(stack, request, response);

        response.setContentType("text/html");

        try {
            String location = (String) ActionContext.getContext().get("template");
            Template template = velocityManager.getVelocityEngine().getTemplate(location);

            Writer writer = pageContext.getOut();
            template.merge(resultContext, writer);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // perform cleanup
            jspFactory.releasePageContext(pageContext);
            actionContext.put(ServletActionContext.PAGE_CONTEXT, oldPageContext);
        }

    }

}
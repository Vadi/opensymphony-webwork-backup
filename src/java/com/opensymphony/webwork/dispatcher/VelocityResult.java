/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.views.velocity.VelocityManager;
import com.opensymphony.webwork.views.velocity.WebWorkVelocityServlet;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeSingleton;

import java.io.Writer;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;


/**
 * Pulls the HttpServletResponse object from the action context,
 * and calls sendRedirect() using the location specified as the
 * parameter "location". The following params are required:
 * <ul>
 *  <li>location - the URL to use when calling sendRedirect()</li>
 * </ul>
 *
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 */
public class VelocityResult extends WebWorkResultSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(VelocityResult.class);
    private static VelocityEngine velocityEngine = VelocityManager.getVelocityEngine();

    //~ Methods ////////////////////////////////////////////////////////////////

    public void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        OgnlValueStack stack = ActionContext.getContext().getValueStack();

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        JspFactory jspFactory = JspFactory.getDefaultFactory();
        ServletContext servletContext = ServletActionContext.getServletContext();
        Servlet servlet = (Servlet) servletContext.getAttribute("servlet");
        PageContext pageContext = jspFactory.getPageContext(servlet, request, response, null, true, 8192, true);
        ActionContext.getContext().put(ServletActionContext.PAGE_CONTEXT, pageContext);

        try {
            Template t = getTemplate(stack, velocityEngine, invocation, finalLocation);

            Context context = VelocityManager.createContext(stack, request, response);
            Writer writer = pageContext.getOut();

            // @todo can t.getEncoding() ever return a null value?
            String encoding = RuntimeSingleton.getString(RuntimeSingleton.OUTPUT_ENCODING, WebWorkVelocityServlet.DEFAULT_OUTPUT_ENCODING);

            if (encoding != null) {
                response.setContentType("text/html; charset=" + encoding);
            } else {
                response.setContentType("text/html");
            }

            t.merge(context, writer);
        } catch (Exception e) {
            log.error("Unable to render Velocity Template, '" + finalLocation + "'", e);
            throw e;
        } finally {
            if (pageContext != null) {
                jspFactory.releasePageContext(pageContext);
            }
        }

        return;
    }

    /**
     * given a value stack, a velocity engine, and an action invocation, return the appropriate velocity Template to
     * render
     *
     * @param stack the value stack to resolve the location again (when parse == true)
     * @param velocity the velocity engine to process the request against
     * @param invocation the current ActionInvocation
     * @return the Template to render
     * @throws Exception when the requested template could not be found
     */
    protected Template getTemplate(OgnlValueStack stack, VelocityEngine velocity, ActionInvocation invocation, String location) throws Exception {
        if (!location.startsWith("/")) {
            location = invocation.getProxy().getNamespace() + "/" + location;
        }

        Template template = velocity.getTemplate(location);

        return template;
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.velocity.VelocityManager;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import java.io.Writer;


/**
 * Using the Servlet container's {@link JspFactory}, this result mocks a JSP execution environment
 * and then displays a Velocity template that will be streamed directly to the servlet output. <p>
 * <p/>
 * This result follows the same rules from {@link WebWorkResultSupport}.
 *
 * @author <a href="mailto:matt@indigoegg.com">Matt Ho</a>
 */
public class VelocityResult extends WebWorkResultSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(VelocityResult.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Creates a Velocity context from the action, loads a Velocity template and executes the
     * template. Output is written to the servlet output stream.
     *
     * @param finalLocation the location of the Velocity template
     * @param invocation    an encapsulation of the action execution state.
     * @throws Exception if an error occurs when creating the Velocity context, loading or executing
     *                   the template or writing output to the servlet response stream.
     */
    public void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        OgnlValueStack stack = ActionContext.getContext().getValueStack();

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        JspFactory jspFactory = null;
        ServletContext servletContext = ServletActionContext.getServletContext();
        Servlet servlet = (Servlet) servletContext.getAttribute("webwork.servlet");

        VelocityManager.getInstance().init(servletContext);

        boolean usedJspFactory = false;
        PageContext pageContext = (PageContext) ActionContext.getContext().get(ServletActionContext.PAGE_CONTEXT);

        if (pageContext == null) {
            jspFactory = JspFactory.getDefaultFactory();
            pageContext = jspFactory.getPageContext(servlet, request, response, null, true, 8192, true);
            ActionContext.getContext().put(ServletActionContext.PAGE_CONTEXT, pageContext);
            usedJspFactory = true;
        }

        try {
            VelocityManager velocityManager = VelocityManager.getInstance();
            Template t = getTemplate(stack, velocityManager.getVelocityEngine(), invocation, finalLocation);

            Context context = createContext(velocityManager, stack, request, response, finalLocation);
            Writer writer = pageContext.getOut();

            if (usedJspFactory) {
                String encoding = getEncoding(finalLocation);
                String contentType = getContentType(finalLocation);

                if (encoding != null) {
                    contentType = contentType + ";charset=" + encoding;
                }

                response.setContentType(contentType);
            }

            t.merge(context, writer);

            if (usedJspFactory) {
                writer.flush();
            }
        } catch (Exception e) {
            log.error("Unable to render Velocity Template, '" + finalLocation + "'", e);
            throw e;
        } finally {
            if (usedJspFactory) {
                jspFactory.releasePageContext(pageContext);
            }
        }

        return;
    }

    /**
     * Retrieve the content type for this template.
     * <p/>
     * People can override this method if they want to provide specific content types for specific templates (eg text/xml).
     *
     * @return The content type associated with this template (default "text/html")
     */
    protected String getContentType(String templateLocation) {
        return "text/html";
    }

    /**
     * Retrieve the encoding for this template.
     * <p/>
     * People can override this method if they want to provide specific encodings for specific templates.
     *
     * @return The encoding associated with this template (defaults to the value of 'webwork.i18n.encoding' property)
     */
    protected String getEncoding(String templateLocation) {
        return (String) Configuration.get("webwork.i18n.encoding");
    }

    /**
     * Given a value stack, a Velocity engine, and an action invocation, this method returns the appropriate
     * Velocity template to render.
     *
     * @param stack      the value stack to resolve the location again (when parse equals true)
     * @param velocity   the velocity engine to process the request against
     * @param invocation an encapsulation of the action execution state.
     * @param location   the location of the template
     * @return the template to render
     * @throws Exception when the requested template could not be found
     */
    protected Template getTemplate(OgnlValueStack stack, VelocityEngine velocity, ActionInvocation invocation, String location) throws Exception {
        if (!location.startsWith("/")) {
            location = invocation.getProxy().getNamespace() + "/" + location;
        }

        Template template = velocity.getTemplate(location);

        return template;
    }

    /**
     * Creates the VelocityContext that we'll use to render this page.
     *
     * @param velocityManager a reference to the velocityManager to use
     * @param stack           the value stack to resolve the location against (when parse equals true)
     * @param location        the name of the template that is being used
     * @return the a minted Velocity context.
     */
    protected Context createContext(VelocityManager velocityManager, OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response, String location) {
        return velocityManager.createContext(stack, request, response);
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.xwork.ActionContext;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.servlet.VelocityServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Properties;


/**
 * @author $Author$
 * @version $Revision$
 */
public class WebWorkVelocityServlet extends VelocityServlet {
    //~ Instance fields ////////////////////////////////////////////////////////

    private VelocityManager velocityManager;

    //~ Constructors ///////////////////////////////////////////////////////////

    public WebWorkVelocityServlet() {
        velocityManager = VelocityManager.getInstance();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        // initialize our VelocityManager
        velocityManager.init(servletConfig.getServletContext());
        servletConfig.getServletContext().setAttribute("webwork.servlet", this);
    }

    protected Context createContext(HttpServletRequest request, HttpServletResponse response) {
        return velocityManager.createContext(ActionContext.getContext().getValueStack(), request, response);
    }

    protected Template handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Context context) throws Exception {
        String servletPath = (String) httpServletRequest.getAttribute("javax.servlet.include.servlet_path");

        if (servletPath == null) {
            servletPath = httpServletRequest.getServletPath();
        }

        return getTemplate(servletPath, getEncoding());
    }

    /**
     * This method extends the VelocityServlet's loadConfiguration method by performing the following actions:
     * <ul>
     * <li>invokes VelocityServlet.loadConfiguration to create a properties object</li>
     * <li>alters the RESOURCE_LOADER to include a class loader</li>
     * <li>configures the class loader using the WebWorkResourceLoader</li>
     * </ul>
     *
     * @param servletConfig
     * @throws IOException
     * @throws FileNotFoundException
     * @see org.apache.velocity.servlet.VelocityServlet#loadConfiguration
     */
    protected Properties loadConfiguration(ServletConfig servletConfig) throws IOException, FileNotFoundException {
        return velocityManager.loadConfiguration(servletConfig.getServletContext());
    }

    /**
     * create a PageContext and render the template to PageContext.getOut()
     *
     * @see VelocityServlet#mergeTemplate(Template, Context, HttpServletResponse) for additional documentation
     */
    protected void mergeTemplate(Template template, Context context, HttpServletResponse response) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException, UnsupportedEncodingException, Exception {
        // save the old PageContext
        PageContext oldPageContext = ServletActionContext.getPageContext();

        // create a new PageContext
        JspFactory jspFactory = JspFactory.getDefaultFactory();
        HttpServletRequest request = (HttpServletRequest) context.get(VelocityManager.REQUEST);
        PageContext pageContext = jspFactory.getPageContext(this, request, response, null, true, 8192, true);

        // put the new PageContext into ActionContext
        ActionContext actionContext = ActionContext.getContext();
        actionContext.put(ServletActionContext.PAGE_CONTEXT, pageContext);

        try {
            Writer writer = pageContext.getOut();
            template.merge(context, writer);
            writer.flush();
        } finally {
            // perform cleanup
            jspFactory.releasePageContext(pageContext);
            actionContext.put(ServletActionContext.PAGE_CONTEXT, oldPageContext);
        }
    }

    private String getEncoding() {
        // todo look into converting this to using XWork/WebWork2 encoding rules
        try {
            return Configuration.getString("webwork.i18n.encoding");
        } catch (IllegalArgumentException e) {
            return RuntimeSingleton.getString(RuntimeSingleton.OUTPUT_ENCODING, DEFAULT_OUTPUT_ENCODING);
        }
    }
}

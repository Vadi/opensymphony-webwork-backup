/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.views.velocity.VelocityManager;
import com.opensymphony.webwork.views.velocity.WebWorkVelocityServlet;
import com.opensymphony.webwork.views.velocity.ui.JSPTagAdapterFactory;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeSingleton;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;
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
public class VelocityResult implements Result, WebWorkStatics {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(VelocityResult.class);
    private static VelocityEngine velocityEngine = VelocityManager.getVelocityEngine();
    public static final String DEFAULT_PARAM = "location";

    //~ Instance fields ////////////////////////////////////////////////////////

    private JSPTagAdapterFactory adapterFactory = new JSPTagAdapterFactory();
    private String location;
    private boolean parse;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setLocation(String location) {
        this.location = location;
    }

    public void setParse(boolean parse) {
        this.parse = parse;
    }

    public void execute(ActionInvocation invocation) throws Exception {
        OgnlValueStack stack = ActionContext.getContext().getValueStack();

        try {
            Template t = getTemplate(stack, velocityEngine, invocation);

            Context context = VelocityManager.createContext(ServletActionContext.getServletConfig(), ServletActionContext.getRequest(), ServletActionContext.getResponse());
            HttpServletResponse response = ServletActionContext.getResponse();
            PageContext pageContext = ServletActionContext.getPageContext();

            Writer writer;

            if (pageContext != null) {
                writer = pageContext.getOut();
            } else {
                writer = response.getWriter();
            }

            // @todo can t.getEncoding() ever return a null value?
            String encoding = RuntimeSingleton.getString(RuntimeSingleton.OUTPUT_ENCODING, WebWorkVelocityServlet.DEFAULT_OUTPUT_ENCODING);

            if (encoding != null) {
                response.setContentType("text/html; charset=" + encoding);
            } else {
                response.setContentType("text/html");
            }

            t.merge(context, writer);

            // flush the buffer as resin fails to render in some cases without this
            if (pageContext == null) {
                writer.flush();
            }
        } catch (Exception e) {
            log.error("Unable to render Velocity Template, '" + location + "'", e);
            throw e;
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
    protected Template getTemplate(OgnlValueStack stack, VelocityEngine velocity, ActionInvocation invocation) throws Exception {
        if (parse) {
            location = TextParseUtil.translateVariables(location, stack);
        }

        if (!location.startsWith("/")) {
            this.location = invocation.getProxy().getNamespace() + "/" + this.location;
        }

        Template template = velocity.getTemplate(this.location);

        return template;
    }
}

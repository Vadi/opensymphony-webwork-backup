/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.views.freemarker.FreemarkerManager;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * When WebWorkPortlet doService() include the .vm resource, then
 * WebWorkVelocityServlet was invoked.
 * <p/>
 * Now we can use WebWorkPreemarkerServlet to render the result Freemarker with
 * the stackValue stored in ActionContext after WebWork Action was executed.
 * <p/>
 *
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn"> Henry Hu </a>
 * @since 2005-7-6
 */
public class WebWorkFreemarkerServlet extends WebWorkAbstractServlet {

    public WebWorkFreemarkerServlet() {
        super();
    }

    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // save the old PageContext
        PageContext oldPageContext = ServletActionContext.getPageContext();

        // create a new PageContext
        JspFactory jspFactory = JspFactory.getDefaultFactory();

        PageContext pageContext = jspFactory.getPageContext(this, request, response, null, true, 8192, true);

        // put the new PageContext into ActionContext
        ActionContext actionContext = ActionContext.getContext();
        actionContext.put(ServletActionContext.PAGE_CONTEXT, pageContext);

        try {
            String location = (String) ActionContext.getContext().get("template");

            Template template = null;

            try {
                template = configuration.getTemplate(location, deduceLocale(location, request, response));
            } catch (FileNotFoundException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Object attrContentType = template.getCustomAttribute("content_type");

            if (attrContentType != null) {
                response.setContentType(attrContentType.toString());
            } else {
                response.setContentType("text/html; charset=" + template.getEncoding());
            }


//            Template template = velocityManager.getVelocityEngine().getTemplate(location);

            Writer writer = pageContext.getOut();

            ServletContext servletContext = getServletContext();
            TemplateModel model = createModel(getObjectWrapper(), servletContext, request, response);

            // Give subclasses a chance to hook into preprocessing
            if (preTemplateProcess(request, response, template, model)) {
                try {
                    // Process the template
                    template.process(model, writer);
                } finally {
                    // Give subclasses a chance to hook into postprocessing
                    postTemplateProcess(request, response, template, model);
                }
            }

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // perform cleanup
            jspFactory.releasePageContext(pageContext);
            actionContext.put(ServletActionContext.PAGE_CONTEXT, oldPageContext);
        }

    }


    /**
     * Returns the locale used for the
     * {@link Configuration#getTemplate(String, Locale)} call.
     * The base implementation simply returns the locale setting of the
     * configuration. Override this method to provide different behaviour, i.e.
     * to use the locale indicated in the request.
     */
    protected Locale deduceLocale(String templatePath, HttpServletRequest request, HttpServletResponse response) {
        return configuration.getLocale();
    }

    /**
     * This method is called from {@link #process(HttpServletRequest, HttpServletResponse)} to obtain the
     * FreeMarker object wrapper object that this result will use
     * for adapting objects into
     * template models.. This is a hook that allows you
     * to custom-configure the wrapper object in a subclass.
     * <p/>
     * <b>
     * The default implementation returns @see Configuration#getObjectWrapper()
     * </b>
     */
    protected ObjectWrapper getObjectWrapper() {
        return configuration.getObjectWrapper();
    }


    protected TemplateModel createModel(ObjectWrapper wrapper, ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws TemplateModelException {

        OgnlValueStack stack = ActionContext.getContext().getValueStack();
        Object action = null;
        if (ServletActionContext.getContext().getActionInvocation() != null) {
            action = ActionContext.getContext().getActionInvocation().getAction();
        }
        TemplateModel model = FreemarkerManager.getInstance().buildTemplateModel(stack, action, servletContext, request, response, wrapper);
        return model;
    }


    protected void postTemplateProcess(HttpServletRequest request, HttpServletResponse response, Template template, TemplateModel data) throws ServletException, IOException {
    }

    protected boolean preTemplateProcess(HttpServletRequest request, HttpServletResponse response, Template template, TemplateModel data) throws ServletException, IOException {
        return true;
    }

}
/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 15/04/2004
 */
package com.opensymphony.webwork.views.freemarker;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;

import com.opensymphony.xwork.ActionInvocation;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author CameronBraid
 */
public class FreemarkerResult extends WebWorkResultSupport {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected ActionInvocation invocation;
    protected Configuration configuration;
    protected ObjectWrapper wrapper;

    /*
     * webwork results are constructed for each result execeution
     *
     * the current context is availible to subclasses via these protected fields
     */
    protected String location;
    private String pContentType = "text/html";

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setContentType(String aContentType) {
        pContentType = aContentType;
    }

    /**
     * allow parameterization of the contentType
     * the default being text/html
     */
    public String getContentType() {
        return pContentType;
    }

    /**
     * execute this result, using the specified template location.
     *
     * The template location has already been interoplated for any variable substitutions
     *
     * this method obtains the freemarker configuration and the object wrapper from the provided hooks.
     * It them implements the template processing workflow by calling the hooks for
     * preTemplateProcess and postTemplateProcess
     */
    public void doExecute(String location, ActionInvocation invocation) throws IOException, TemplateException {
        this.location = location;
        this.invocation = invocation;
        this.configuration = getConfiguration();
        this.wrapper = getObjectWrapper();

        Template template = configuration.getTemplate(location, deduceLocale());
        TemplateModel model = createModel();

        // Give subclasses a chance to hook into preprocessing
        if (preTemplateProcess(template, model)) {
            try {
                // Process the template
                template.process(model, getWriter());
            } finally {
                // Give subclasses a chance to hook into postprocessing
                postTemplateProcess(template, model);
            }
        }
    }

    /**
     * This method is called from {@link #doExecute()} to obtain the
     * FreeMarker configuration object that this result will use
     * for template loading. This is a hook that allows you
     * to custom-configure the configuration object in a subclass,
     * or to fetch it from an IoC container.
     *
     * <b>
     *         The default implementation obtains the configuration from the ConfigurationManager instance.</b>
     * </b>
     */
    protected Configuration getConfiguration() throws TemplateException {
        return FreemarkerManager.getInstance().getConfigruation(ServletActionContext.getServletContext());
    }

    /**
     * This method is called from {@link #doExecute()} to obtain the
     * FreeMarker object wrapper object that this result will use
     * for adapting objects into
     * template models.. This is a hook that allows you
     * to custom-configure the wrapper object in a subclass.
     *
     * <b>
     * The default implementation returns @see Configuration#getObjectWrapper()
     * </b>
     */
    protected ObjectWrapper getObjectWrapper() {
        return configuration.getObjectWrapper();
    }

    /**
     * the default writer writes directly to the response output stream
     */
    protected Writer getWriter() throws IOException {
        return new OutputStreamWriter(ServletActionContext.getResponse().getOutputStream());
    }

    /**
     * Build the instance of the ScopesHashModel, including JspTagLib support
     *
     * Objects added to the model are
     *
     * <ul>
     *  <li>Application - servlet context attributes hash model
     *  <li>JspTaglibs - jsp tag lib factory model
     *  <li>Request - request attributes hash model
     *  <li>Session - session attributes hash model
     *  <li>req - the HttpServletRequst object for direct access
     *  <li>res - the HttpServletResponse object for direct access
     *  <li>stack - the OgnLValueStack instance for direct access
     *  <li>ognl - the instance of the OgnlTool
     *  <li>action - the action itself
     *  <li>exception - optional : the JSP or Servlet exception as per the servlet spec (for JSP Exception pages)
     *  <li>webwork - instance of the WebWorkUtil class
     *</ul>
     */
    protected TemplateModel createModel() throws TemplateModelException {
        ServletContext servletContext = ServletActionContext.getServletContext();
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpSession session = ServletActionContext.getRequest().getSession(false);

        ScopesHashModel model = FreemarkerManager.getInstance().buildScopesHashModel(servletContext, request, response, wrapper);

        FreemarkerManager.getInstance().populateContext(model, invocation.getStack(), invocation.getAction(), request, response);

        return new ValueStackModel(ServletActionContext.getContext().getValueStack(), model, wrapper);
    }

    /**
     * Returns the locale used for the
     * {@link Configuration#getTemplate(String, Locale)} call.
     * The base implementation simply returns the locale setting of the
     * configuration. Override this method to provide different behaviour,
     */
    protected Locale deduceLocale() {
        return configuration.getLocale();
    }

    /**
     * the default implementation of postTemplateProcess applies the contentType parameter
     */
    protected void postTemplateProcess(Template template, TemplateModel data) throws IOException {
    }

    /**
     * Called before the execution is passed to template.process().
     * This is a generic hook you might use in subclasses to perform a specific
     * action before the template is processed. By default does nothing.
     * A typical action to perform here is to inject application-specific
     * objects into the model root
     * @return true to process the template, false to suppress template processing.
     */
    protected boolean preTemplateProcess(Template template, TemplateModel model) throws IOException {
        Object attrContentType = template.getCustomAttribute("content_type");

        if (attrContentType != null) {
            ServletActionContext.getResponse().setContentType(attrContentType.toString());
        } else {
            String contentType = getContentType();

            if (contentType == null) {
                contentType = "text/html";
            }

            String encoding = template.getEncoding();

            if (encoding != null) {
                contentType = contentType + "; charset=" + encoding;
            }

            ServletActionContext.getResponse().setContentType(contentType);
        }

        return true;
    }
}

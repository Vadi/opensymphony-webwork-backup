/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 27/08/2003
 *
 */
package com.opensymphony.webwork.views.freemarker;

import com.opensymphony.xwork.ActionContext;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author CameronBraid
 *
 */
public class FreemarkerServlet extends freemarker.ext.servlet.FreemarkerServlet {
    //~ Constructors ///////////////////////////////////////////////////////////

    /**
    *
    */
    public FreemarkerServlet() {
        super();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
    * @see freemarker.ext.servlet.FreemarkerServlet#createModel(freemarker.template.ObjectWrapper, javax.servlet.ServletContext, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
    protected TemplateModel createModel(ObjectWrapper wrapper, ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws TemplateModelException {
        // get the superclasses model and wrap it in a ValueStackModelWrapper
        TemplateHashModel model = (TemplateHashModel) super.createModel(wrapper, servletContext, request, response);

        ValueStackModel valueStackModel = new ValueStackModel(model);
        valueStackModel.setObjectWrapper(getObjectWrapper());

        return valueStackModel;
    }

    /*
    *
    * @see freemarker.ext.servlet.FreemarkerServlet#preTemplateProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, freemarker.template.Template, freemarker.template.TemplateModel)
    */
    protected boolean preTemplateProcess(HttpServletRequest request, HttpServletResponse response, Template template, TemplateModel templateModel) throws ServletException, IOException {
        super.preTemplateProcess(request, response, template, templateModel);

        SimpleHash hash = (SimpleHash) templateModel;

        // support for access to the value stack using the key 'stack' 
        hash.put("stack", ActionContext.getContext().getValueStack());

        // the select list helper is a utility class to assist in building a list of key/value
        // when given a list and a keyName and valueName, as in ww:select input
        hash.put("wwUtil", new FreemarkerUtil());

        // support for JSP exception pages, exposing the servlet or JSP exception
        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (exception == null) {
            exception = (Throwable) request.getAttribute("javax.servlet.error.JspException");
        }

        if (exception != null) {
            hash.put("exception", exception);
        }

        return true;
    }
}

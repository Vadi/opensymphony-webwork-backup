/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.freemarker;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Simple Hash model that also searches other scopes.
 *
 * If the key doesn't exist in this hash, this template model tries to
 * resolve the key within the attributes of the following scopes,
 * in the order stated: Request, Session, Servlet Context
 *
 */
public class ScopesHashModel extends SimpleHash {
    //~ Instance fields ////////////////////////////////////////////////////////

    private HttpServletRequest request;
    private ObjectWrapper objectWraper;
    private ServletContext servletContext;

    //~ Constructors ///////////////////////////////////////////////////////////

    public ScopesHashModel(ObjectWrapper objectWrapper, ServletContext context, HttpServletRequest request) {
        super();
        this.objectWraper = objectWrapper;
        this.servletContext = context;
        this.request = request;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public TemplateModel get(String key) throws TemplateModelException {
        // Lookup in default scope
        TemplateModel model = super.get(key);

        if (model != null) {
            return model;
        }

        if (request != null) {
            // Lookup in request scope
            Object obj = request.getAttribute(key);

            if (obj != null) {
                return objectWraper.wrap(obj);
            }

            // Lookup in session scope
            HttpSession session = request.getSession(false);

            if (session != null) {
                obj = session.getAttribute(key);

                if (obj != null) {
                    return objectWraper.wrap(obj);
                }
            }
        }

        if (servletContext != null) {
            // Lookup in application scope
            Object obj = servletContext.getAttribute(key);

            if (obj != null) {
                return objectWraper.wrap(obj);
            }
        }

        return null;
    }
}

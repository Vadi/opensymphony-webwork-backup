/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */

package com.opensymphony.webwork.sitemesh;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.filter.PageFilter;
import com.opensymphony.webwork.views.freemarker.FreemarkerManager;
import com.opensymphony.xwork.*;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 *  Applies FreeMarker-based sitemesh decorators.
 *
 * @author patrick
 */
public class FreeMarkerPageFilter extends TemplatePageFilter {
    private static final Log LOG = LogFactory.getLog(FreeMarkerPageFilter.class);

    /**
     *  Applies the decorator, using the relevent contexts
     * 
     * @param page The page
     * @param decorator The decorator
     * @param req The servlet request
     * @param res The servlet response
     * @param servletContext The servlet context
     * @param ctx The action context for this request, populated with the server state
     */
    protected void applyDecorator(Page page, Decorator decorator,
                                  HttpServletRequest req, HttpServletResponse res,
                                  ServletContext servletContext, ActionContext ctx)
            throws ServletException, IOException {
        try {
            FreemarkerManager fmm = FreemarkerManager.getInstance();

            // get the configuration and template
            Configuration config = fmm.getConfiguration(servletContext);
            Template template = config.getTemplate(decorator.getPage(), getLocale(ctx.getActionInvocation(), config)); // WW-1181

            // get the main hash
            SimpleHash model = fmm.buildTemplateModel(ctx.getValueStack(), null, servletContext, req, res, config.getObjectWrapper());

            // populate the hash with the page
            model.put("page", page);
            if (page instanceof HTMLPage) {
                HTMLPage htmlPage = ((HTMLPage) page);
                model.put("head", htmlPage.getHead());
            }
            model.put("title",page.getTitle());
            model.put("body",page.getBody());
            model.put("page.properties", new SimpleHash(page.getProperties()));

            // finally, render it
            template.process(model, res.getWriter());
        } catch (Exception e) {
            String msg = "Error applying decorator: " + e.getMessage();
            LOG.error(msg, e);
            throw new ServletException(msg, e);
        }
    }
    
    /**
     * Returns the locale used for the {@link Configuration#getTemplate(String, Locale)} call. The base implementation
     * simply returns the locale setting of the action (assuming the action implements {@link LocaleProvider}) or, if
     * the action does not the configuration's locale is returned. Override this method to provide different behaviour,
     */
    protected Locale getLocale(ActionInvocation invocation, Configuration configuration) {
        if (invocation.getAction() instanceof LocaleProvider) {
            return ((LocaleProvider) invocation.getAction()).getLocale();
        } else {
            return configuration.getLocale();
        }
    }
 
}

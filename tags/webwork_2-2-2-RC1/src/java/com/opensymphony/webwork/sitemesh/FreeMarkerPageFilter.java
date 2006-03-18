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
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.DispatcherUtils;
import com.opensymphony.xwork.*;
import com.opensymphony.xwork.interceptor.PreResultListener;
import com.opensymphony.xwork.util.OgnlValueStack;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author patrick
 */
public class FreeMarkerPageFilter extends PageFilter {
    private static final Log LOG = LogFactory.getLog(FreeMarkerPageFilter.class);

    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) {
        super.init(filterConfig);
        this.filterConfig = filterConfig;
    }

    protected void applyDecorator(Page page, Decorator decorator,
                                  HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            FreemarkerManager fmm = FreemarkerManager.getInstance();
            ServletContext servletContext = filterConfig.getServletContext();
            ActionContext ctx = ServletActionContext.getActionContext(req);
            if (ctx == null) {
                // ok, one isn't associated with the request, so let's get a ThreadLocal one (which will create one if needed)
                OgnlValueStack vs = new OgnlValueStack();
                vs.getContext().putAll(DispatcherUtils.getInstance().createContextMap(req, res, null, servletContext));
                ctx = new ActionContext(vs.getContext());
                if (ctx.getActionInvocation() == null) {
                    // put in a dummy ActionSupport so basic functionality still works
                    ActionSupport action = new ActionSupport();
                    vs.push(action);
                    ctx.setActionInvocation(new DummyActionInvocation(action));
                }
            }

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
    
    static class DummyActionInvocation implements ActionInvocation {
        ActionSupport action;

        public DummyActionInvocation(ActionSupport action) {
            this.action = action;
        }

        public Object getAction() {
            return action;
        }

        public boolean isExecuted() {
            return false;
        }

        public ActionContext getInvocationContext() {
            return null;
        }

        public ActionProxy getProxy() {
            return null;
        }

        public Result getResult() throws Exception {
            return null;
        }

        public String getResultCode() {
            return null;
        }

        public void setResultCode(String resultCode) {
        }

        public OgnlValueStack getStack() {
            return null;
        }

        public void addPreResultListener(PreResultListener listener) {
        }

        public String invoke() throws Exception {
            return null;
        }

        public String invokeActionOnly() throws Exception {
            return null;
        }
    }
}

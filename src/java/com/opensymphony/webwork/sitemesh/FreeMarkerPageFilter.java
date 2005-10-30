package com.opensymphony.webwork.sitemesh;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.filter.PageFilter;
import com.opensymphony.webwork.views.freemarker.FreemarkerManager;
import com.opensymphony.xwork.ActionContext;
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

/**
 * User: patrick
 * Date: Aug 31, 2005
 * Time: 2:52:33 PM
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
            ActionContext ctx = ActionContext.getContext();

            // get the configuration and template
            Configuration config = fmm.getConfigruation(servletContext);
            Template template = config.getTemplate(decorator.getPage());

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

            // finally, render it
            template.process(model, res.getWriter());
        } catch (Exception e) {
            String msg = "Error applying decorator: " + e.getMessage();
            LOG.error(msg, e);
            throw new ServletException(msg, e);
        }
    }
}

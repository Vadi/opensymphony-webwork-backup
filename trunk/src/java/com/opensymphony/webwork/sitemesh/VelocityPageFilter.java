package com.opensymphony.webwork.sitemesh;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.filter.PageFilter;
import com.opensymphony.webwork.views.velocity.VelocityManager;
import com.opensymphony.xwork.ActionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * User: plightbo
 * Date: Aug 31, 2005
 * Time: 10:49:51 PM
 */
public class VelocityPageFilter extends TemplatePageFilter {
    private static final Log LOG = LogFactory.getLog(VelocityPageFilter.class);


    protected void applyDecorator(Page page, Decorator decorator,
                                  HttpServletRequest req, HttpServletResponse res,
                                  ServletContext servletContext, ActionContext ctx)
            throws ServletException, IOException {
        try {
            VelocityManager vm = VelocityManager.getInstance();

            // init (if needed)
            vm.init(servletContext);

            // get encoding
            String encoding = getEncoding();

            // get the template and context
            Template template = vm.getVelocityEngine().getTemplate(decorator.getPage(), encoding);
            Context context = vm.createContext(ctx.getValueStack(), req, res);

            // put the page in the context
            context.put("page", page);
            if (page instanceof HTMLPage) {
                HTMLPage htmlPage = ((HTMLPage) page);
                context.put("head", htmlPage.getHead());
            }
            context.put("title",page.getTitle());
            context.put("body",page.getBody());

            // finally, render it
            PrintWriter writer = res.getWriter();
            template.merge(context, writer);
            writer.flush();
        } catch (Exception e) {
            String msg = "Error applying decorator: " + e.getMessage();
            LOG.error(msg, e);
            throw new ServletException(msg, e);
        }
    }
}

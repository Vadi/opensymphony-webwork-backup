package com.opensymphony.webwork.sitemesh;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.filter.PageFilter;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.velocity.VelocityManager;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.xwork.ActionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import javax.servlet.FilterConfig;
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
public class VelocityPageFilter extends PageFilter {
    private static final Log LOG = LogFactory.getLog(VelocityPageFilter.class);

    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) {
        super.init(filterConfig);
        this.filterConfig = filterConfig;
    }

    protected void applyDecorator(Page page, Decorator decorator,
                                  HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            VelocityManager vm = VelocityManager.getInstance();
            ServletContext servletContext = filterConfig.getServletContext();
            ActionContext ctx = ActionContext.getContext();

            // init (if needed)
            vm.init(servletContext);

            // get encoding
            String encoding = getEncoding();

            // get the template and context
            Template template = vm.getVelocityEngine().getTemplate(decorator.getPage(), encoding);
            Context context = vm.createContext(ctx.getValueStack(), req, res);

            // put the page in the context
            context.put("page", page);

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

    protected String getEncoding() {
        String encoding = (String) Configuration.get(WebWorkConstants.WEBWORK_I18N_ENCODING);
        if (encoding == null) {
            encoding = System.getProperty("file.encoding");
        }
        if (encoding == null) {
            encoding = "UTF-8";
        }
        return encoding;
    }

}

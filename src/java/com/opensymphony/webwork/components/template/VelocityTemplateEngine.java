package com.opensymphony.webwork.components.template;

import com.opensymphony.webwork.views.velocity.AbstractTagDirective;
import com.opensymphony.webwork.views.velocity.VelocityManager;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.servlet.ServletContext;
import java.io.Writer;
import java.util.Map;

/**
 * VelocityTemplateEngine renders Velocity templates
 * Date: Sep 28, 2004 12:01:09 PM
 *
 * @author jcarreira
 */
public class VelocityTemplateEngine extends BaseTemplateEngine {
    private static final Log LOG = LogFactory.getLog(VelocityTemplateEngine.class);

    public void renderTemplate(TemplateRenderingContext templateContext) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rendering template '" + templateContext.getTemplateName() + "'");
        }

        Map actionContext = templateContext.getStack().getContext();
        ServletContext servletContext = (ServletContext) actionContext.get(ServletActionContext.SERVLET_CONTEXT);
        HttpServletRequest req = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
        HttpServletResponse res = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);

        // initialize the VelocityEngine
        // this may happen more than once, but it's not a big deal
        VelocityManager velocityManager = VelocityManager.getInstance();
        velocityManager.init(servletContext);
        VelocityEngine velocityEngine = velocityManager.getVelocityEngine();
        Template t = velocityEngine.getTemplate(templateContext.getTemplateName());
        Context context = velocityManager.createContext(templateContext.getStack(), req, res);

        Writer outputWriter = (Writer) ActionContext.getContext().get(AbstractTagDirective.VELOCITY_WRITER);
        if (outputWriter == null) {
            outputWriter = templateContext.getWriter();
        }

        context.put("tag", templateContext.getTag());
        context.put("parameters", templateContext.getParameters());

        t.merge(context, outputWriter);
    }

    protected String getSuffix() {
        return "vm";
    }
}

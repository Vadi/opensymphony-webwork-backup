package com.opensymphony.webwork.views.jsp.ui.template;

import com.opensymphony.webwork.views.velocity.AbstractTagDirective;
import com.opensymphony.webwork.views.velocity.VelocityManager;
import com.opensymphony.xwork.ActionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.io.Writer;

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
        PageContext pageContext = templateContext.getPageContext();

        // initialize the VelocityEngine
        // this may happen more than once, but it's not a big deal
        VelocityManager velocityManager = VelocityManager.getInstance();
        velocityManager.init(pageContext.getServletContext());
        VelocityEngine velocityEngine = velocityManager.getVelocityEngine();

        Template t = velocityEngine.getTemplate(templateContext.getTemplateName());

        Context context = velocityManager.createContext(templateContext.getStack(),
                (HttpServletRequest) pageContext.getRequest(),
                (HttpServletResponse) pageContext.getResponse());

        Writer outputWriter = (Writer) ActionContext.getContext().get(AbstractTagDirective.VELOCITY_WRITER);
        if (outputWriter == null) {
            outputWriter = pageContext.getOut();
        }

        context.put("tag", templateContext.getTag());
        context.put("parameters", templateContext.getParameters());

        t.merge(context, outputWriter);
    }

    protected String getSuffix() {
        return "vm";
    }
}

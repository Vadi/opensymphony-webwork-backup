package com.opensymphony.webwork.views.jsp.ui.template;

import com.opensymphony.webwork.views.velocity.VelocityManager;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    protected static VelocityManager velocityManager = VelocityManager.getInstance();
    protected static VelocityEngine velocityEngine = velocityManager.getVelocityEngine();
    private static boolean velocityInitialized = false;

    public void renderTemplate(TemplateRenderingContext templateContext) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rendering template '" + templateContext.getTemplateName() + "'");
        }
        PageContext pageContext = templateContext.getPageContext();
        if (!velocityInitialized) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Initializing Velocity...");
            }
            // initialize the VelocityEngine
            // this may happen more than once, but it's not a big deal
            VelocityManager.getInstance().init(pageContext.getServletContext());
            velocityInitialized = true;
        }

        Template t = velocityEngine.getTemplate(templateContext.getTemplateName());

        Context context = velocityManager.createContext(templateContext.getStack(),
                (HttpServletRequest) pageContext.getRequest(),
                (HttpServletResponse) pageContext.getResponse());

        Writer outputWriter = pageContext.getOut();

        // Make the OGNL stack available to the velocityEngine templates.
        // todo Consider putting all the VelocityServlet Context values in
        // after all, if we're already sending the request, it might also
        // make sense for consistency to send the page and res and any others.
        context.put("tag", templateContext.getTag());
        context.put("parameters", templateContext.getParameters());

        t.merge(context, outputWriter);
    }

    protected String getSuffix() {
        return "vm";
    }
}

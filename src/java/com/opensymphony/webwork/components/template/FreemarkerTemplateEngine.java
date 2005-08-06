/*
 * Created on 3/03/2005
 *
 */
package com.opensymphony.webwork.components.template;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.views.freemarker.FreemarkerManager;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author CameronBraid
 */
public class FreemarkerTemplateEngine extends BaseTemplateEngine {

    private static final Log LOG = LogFactory.getLog(FreemarkerTemplateEngine.class);

    public void renderTemplate(TemplateRenderingContext templateContext) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rendering template '" + templateContext.getTemplateName() + "'");
        }

        OgnlValueStack stack = templateContext.getStack();
        Map context = stack.getContext();
        ServletContext servletContext = (ServletContext) context.get(ServletActionContext.SERVLET_CONTEXT);
        HttpServletRequest req = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);
        HttpServletResponse res = (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE);

        FreemarkerManager freemarkerManager = FreemarkerManager.getInstance();
        Configuration config = freemarkerManager.getConfigruation(servletContext);

        freemarker.template.Template template = config.getTemplate(templateContext.getTemplateName());

        ActionInvocation ai = ActionContext.getContext().getActionInvocation();

        Object action = (ai == null) ? null : ai.getAction();
        SimpleHash model = freemarkerManager.buildTemplateModel(stack, action, servletContext, req, res, config.getObjectWrapper());

        model.put("tag", templateContext.getTag());
        model.put("parameters", templateContext.getParameters());

        template.process(model, templateContext.getWriter());
    }

    protected String getSuffix() {
        return "ftl";
    }
}

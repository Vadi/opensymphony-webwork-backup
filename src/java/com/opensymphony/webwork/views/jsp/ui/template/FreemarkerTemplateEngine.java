/*
 * Created on 3/03/2005
 *
 */
package com.opensymphony.webwork.views.jsp.ui.template;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.views.freemarker.FreemarkerManager;
import com.opensymphony.webwork.views.freemarker.ScopesHashModel;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModel;

/**
 * @author CameronBraid
 *
 */
public class FreemarkerTemplateEngine extends BaseTemplateEngine {
    
	private static final Log LOG = LogFactory.getLog(FreemarkerTemplateEngine.class);

    public void renderTemplate(TemplateRenderingContext templateContext) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rendering template '" + templateContext.getTemplateName() + "'");
        }
        PageContext pageContext = templateContext.getPageContext();

        FreemarkerManager freemarkerManager = FreemarkerManager.getInstance();
        Configuration config = freemarkerManager.getConfigruation(pageContext.getServletContext());

        freemarker.template.Template template = config.getTemplate(templateContext.getTemplateName());
        
        ActionInvocation ai = ActionContext.getContext().getActionInvocation();
        
        SimpleHash model = freemarkerManager.buildTemplateModel(templateContext.getStack(), (ai==null)?null:ai.getAction(), pageContext.getServletContext(), (HttpServletRequest)pageContext.getRequest(), (HttpServletResponse)pageContext.getResponse(), config.getObjectWrapper());
        
        Writer outputWriter = null;
        
        // if working within a freemarker environment, write directly to its writer
        Environment environment = Environment.getCurrentEnvironment();
        if (environment != null) {
        	outputWriter = environment.getOut();
        }
        else {
            outputWriter = pageContext.getOut();
        }

        model.put("tag", templateContext.getTag());
        model.put("parameters", templateContext.getParameters());

        template.process(model, outputWriter);
    }

    protected String getSuffix() {
        return "ftl";
    }
}

package com.opensymphony.webwork.views.jsp.ui.template;


/**
 * Any template language which wants to support UI tag templating needs to provide an implementation of this interface
 * to handle rendering the template
 * Date: Sep 28, 2004 11:56:34 AM
 *
 * @author jcarreira
 */
public interface TemplateEngine {
    void renderTemplate(TemplateRenderingContext templateContext) throws Exception;

    String getFinalTemplateName(String templateName);
}

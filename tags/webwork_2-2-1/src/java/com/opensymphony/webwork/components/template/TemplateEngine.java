package com.opensymphony.webwork.components.template;

import java.util.Map;


/**
 * Any template language which wants to support UI tag templating needs to provide an implementation of this interface
 * to handle rendering the template
 *
 * @author jcarreira
 */
public interface TemplateEngine {
    void renderTemplate(TemplateRenderingContext templateContext) throws Exception;

    Map getThemeProps(Template template);
}

package com.opensymphony.webwork.components.template;

/**
 * BaseTemplateEngine
 * Date: Sep 28, 2004 3:47:22 PM
 *
 * @author jcarreira
 */
public abstract class BaseTemplateEngine implements TemplateEngine {
    public String getFinalTemplateName(String templateName) {
        if (templateName.indexOf(".") <= 0) {
            return templateName + "." + getSuffix();
        }
        return templateName;
    }

    protected abstract String getSuffix();
}

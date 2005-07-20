package com.opensymphony.webwork.components.template;

import com.opensymphony.webwork.config.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * The TemplateEngineManager will return a template engine for the template
 * Date: Sep 28, 2004 11:58:29 AM
 *
 * @author jcarreira
 */
public class TemplateEngineManager {
    public static final String DEFAULT_TEMPLATE_TYPE_CONFIG_KEY = "webwork.ui.templateSuffix";

    private static final TemplateEngineManager MANAGER = new TemplateEngineManager();
    public static final String DEFAULT_TEMPLATE_TYPE = "ftl";

    Map templateEngines = new HashMap();

    private TemplateEngineManager() {
        templateEngines.put("ftl", new FreemarkerTemplateEngine());
        templateEngines.put("vm", new VelocityTemplateEngine());
        templateEngines.put("jsp", new JspTemplateEngine());
    }

    public static void registerTemplateEngine(String templateExtension, TemplateEngine templateEngine) {
        MANAGER.templateEngines.put(templateExtension,templateEngine);
    }

    /**
     * Gets the TemplateEngine for the template name. If the template name has an extension (for instance foo.jsp), then
     * this extension will be used to look up the appropriate TemplateEngine. If it does not have an extension, it will
     * look for a Configuration setting "webwork.ui.templateSuffix" for the extension, and if that is not set, it
     * will fall back to "vm" as the default.
     *
     * @param templateName
     * @return
     */
    public static TemplateEngine getTemplateEngine(String templateName) {
        String templateType = DEFAULT_TEMPLATE_TYPE;
        if (templateName.indexOf(".") > 0) {
            templateType = templateName.substring(templateName.indexOf(".") + 1);
        } else {
            if (Configuration.isSet(DEFAULT_TEMPLATE_TYPE_CONFIG_KEY)) {
                templateType = (String) Configuration.get(DEFAULT_TEMPLATE_TYPE_CONFIG_KEY);
            }
        }
        return (TemplateEngine) MANAGER.templateEngines.get(templateType);
    }


}

package com.opensymphony.webwork.views;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.jsp.ui.template.JspTemplateEngine;
import com.opensymphony.webwork.views.jsp.ui.template.TemplateEngine;
import com.opensymphony.webwork.views.jsp.ui.template.TemplateEngineManager;
import com.opensymphony.webwork.views.jsp.ui.template.VelocityTemplateEngine;
import junit.framework.TestCase;

/**
 * TemplateEngineManagerTest
 * Date: Sep 28, 2004 12:22:05 PM
 *
 * @author jcarreira
 */
public class TemplateEngineManagerTest extends TestCase {
    public void testTemplateTypeFromTemplateNameAndDefaults() {
        Configuration.setConfiguration(new Configuration() {
            public boolean isSetImpl(String name) {
                return name.equals(TemplateEngineManager.DEFAULT_TEMPLATE_TYPE_CONFIG_KEY);
            }

            public Object getImpl(String aName) throws IllegalArgumentException {
                if (aName.equals(TemplateEngineManager.DEFAULT_TEMPLATE_TYPE_CONFIG_KEY)) {
                    return "jsp";
                }
                return null;
            }
        });
        TemplateEngine engine = TemplateEngineManager.getTemplateEngine("foo");
        assertTrue(engine instanceof JspTemplateEngine);
        engine = TemplateEngineManager.getTemplateEngine("foo.vm");
        assertTrue(engine instanceof VelocityTemplateEngine);
    }

    public void testTemplateTypeUsesDefaultWhenNotSetInConfiguration() {
        TemplateEngine engine = TemplateEngineManager.getTemplateEngine("foo");
        TemplateEngine defaultTemplateEngine = TemplateEngineManager.getTemplateEngine(TemplateEngineManager.DEFAULT_TEMPLATE_TYPE);
        assertTrue(engine.getClass().equals(defaultTemplateEngine.getClass()));
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        Configuration.setConfiguration(null);
    }
}

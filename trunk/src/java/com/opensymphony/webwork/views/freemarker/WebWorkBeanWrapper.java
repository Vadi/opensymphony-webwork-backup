package com.opensymphony.webwork.views.freemarker;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateBooleanModel;

/**
 * @author Patrick Lightbody (plightbo at gmail dot com)
 */
public class WebWorkBeanWrapper extends BeansWrapper {
    public TemplateModel wrap(Object object) throws TemplateModelException {
        if (object instanceof TemplateBooleanModel) {
            TemplateModel tm = super.wrap(object);
            return tm;
        }

        return super.wrap(object);
    }

    class BooleanTemplateModel implements TemplateBooleanModel {
        public boolean getAsBoolean() throws TemplateModelException {
            return false;
        }
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.validators.JavaScriptVisitorFieldValidator;
import com.opensymphony.webwork.validators.ScriptValidationAware;
import com.opensymphony.webwork.views.jsp.ParameterizedTagSupport;
import com.opensymphony.webwork.views.jsp.ui.template.TemplateEngine;
import com.opensymphony.webwork.views.jsp.ui.template.TemplateEngineManager;
import com.opensymphony.webwork.views.jsp.ui.template.TemplateRenderingContext;
import com.opensymphony.xwork.ModelDriven;
import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.validator.*;
import ognl.OgnlRuntime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * Abstract base class for all UI tags.
 *
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public abstract class AbstractUITag extends ParameterizedTagSupport {

    private static final Log LOG = LogFactory.getLog(AbstractUITag.class);

    protected String cssClassAttr;
    protected String cssStyleAttr;
    protected String disabledAttr;
    protected String labelAttr;
    protected String labelPositionAttr;
    protected String nameAttr;
    protected String requiredAttr;
    protected String tabindexAttr;
    protected String templateAttr;
    protected String templateDir;
    protected String theme;
    protected String themeAttr;
    protected String valueAttr;

    // HTML scripting events attributes
    protected String onclickAttr;
    protected String ondblclickAttr;
    protected String onmousedownAttr;
    protected String onmouseupAttr;
    protected String onmouseoverAttr;
    protected String onmousemoveAttr;
    protected String onmouseoutAttr;
    protected String onfocusAttr;
    protected String onblurAttr;
    protected String onkeypressAttr;
    protected String onkeydownAttr;
    protected String onkeyupAttr;
    protected String onselectAttr;
    protected String onchangeAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setCssClass(String aCssClass) {
        cssClassAttr = aCssClass;
    }

    public void setCssStyle(String aCssStyle) {
        this.cssStyleAttr = aCssStyle;
    }

    public void setDisabled(String disabled) {
        this.disabledAttr = disabled;
    }

    public void setLabel(String aLabel) {
        labelAttr = aLabel;
    }

    public void setLabelposition(String aLabelPosition) {
        labelPositionAttr = aLabelPosition;
    }

    public void setName(String aName) {
        nameAttr = aName;
    }

    public void setRequired(String required) {
        this.requiredAttr = required;
    }

    public void setTabindex(String tabindex) {
        this.tabindexAttr = tabindex;
    }

    public void setTemplate(String aName) {
        templateAttr = aName;
    }

    public String getTemplateDir() {
        // If templateDir is not explicitly given,
        // try to find attribute which states the dir set to use
        if ((templateDir == null) || (templateDir == "")) {
            templateDir = setupPath((String) pageContext.findAttribute("templateDir"), true);
        }

        // Default template set
        if ((templateDir == null) || (templateDir == "")) {
            templateDir = setupPath(Configuration.getString("webwork.ui.templateDir"), true);
        }

        if ((templateDir == null) || (templateDir == "")) {
            templateDir = "/template/";
        }

        return templateDir;
    }

    public void setTheme(String aName) {
        themeAttr = aName;
    }

    public String getTheme() {
        if (themeAttr != null) {
            theme = setupPath(findString(themeAttr), false);
        }

        // If theme set is not explicitly given,
        // try to find attribute which states the theme set to use
        if ((theme == null) || (theme == "")) {
            theme = setupPath((String) pageContext.findAttribute("theme"), false);
        }

        // Default template set
        if ((theme == null) || (theme == "")) {
            theme = setupPath(Configuration.getString("webwork.ui.theme"), false);
        }

        return theme;
    }

    public void setValue(String aValue) {
        valueAttr = aValue;
    }

    // HTML scripting attribute setters

    public void setOnclick(String onclick) {
        this.onclickAttr = onclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclickAttr = ondblclick;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedownAttr = onmousedown;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseupAttr = onmouseup;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseoverAttr = onmouseover;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemoveAttr = onmousemove;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseoutAttr = onmouseout;
    }

    public void setOnfocus(String onfocus) {
        this.onfocusAttr = onfocus;
    }

    public void setOnblur(String onblur) {
        this.onblurAttr = onblur;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypressAttr = onkeypress;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydownAttr = onkeydown;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyupAttr = onkeyup;
    }

    public void setOnselect(String onselect) {
        this.onselectAttr = onselect;
    }

    public void setOnchange(String onchange) {
        this.onchangeAttr = onchange;
    }

    public int doEndTag() throws JspException {
        try {
            OgnlValueStack stack = getStack();
            evaluateParams(stack);

            try {
                mergeTemplate(this.getTemplateName());

                return EVAL_PAGE;
            } catch (Exception e) {
                throw new JspException("Fatal exception caught in " + this.getClass().getName() + " tag class, doEndTag: " + e.getMessage(), e);
            }
        } finally {
            // clean up after ourselves to allow this tag to be reused
            this.reset();
        }
    }

    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    /**
     * A contract that requires each concrete UI Tag to specify which template should be used as a default.  For
     * example, the CheckboxTab might return "checkbox.vm" while the RadioTag might return "radio.vm".  This value
     * <strong>not</strong> begin with a '/' unless you intend to make the path absolute rather than relative to the
     * current theme.
     *
     * @return The name of the template to be used as the default.
     */
    protected abstract String getDefaultTemplate();

    /**
     * Find the name of the Velocity template that we should use.
     *
     * @return The name of the Velocity template that we should use. This value should begin with a '/'
     */
    protected String getTemplateName() {
        return buildTemplateName(templateAttr, getDefaultTemplate());
    }

    protected Class getValueClassType() {
        return String.class;
    }

    /**
     * @param myTemplate
     * @param myDefaultTemplate
     */
    protected String buildTemplateName(String myTemplate, String myDefaultTemplate) {
        String template = myDefaultTemplate;

        if (myTemplate != null) {
            template = findString(myTemplate);

            if (template == null) {
                LOG.warn("template attribute evaluated to null; using value as-is for backwards compatibility");
                template = myTemplate;
            }
        }

        return getTemplateDir() + getTheme() + template;
    }

    protected void evaluateExtraParams(OgnlValueStack stack) {
    }

    protected boolean evaluateNameValue() {
        return true;
    }

    protected void evaluateParams(OgnlValueStack stack) {
        Object name = null;

        if (nameAttr != null) {
            name = findString(nameAttr);
            addParameter("name", name);
        }

        if (labelAttr != null) {
            addParameter("label", findString(labelAttr));
        }

        if (labelPositionAttr != null) {
            addParameter("labelposition", findString(labelPositionAttr));
        }

        if (requiredAttr != null) {
            addParameter("required", findValue(requiredAttr, Boolean.class));
        }

        if (disabledAttr != null) {
            addParameter("disabled", findValue(disabledAttr, Boolean.class));
        }

        if (tabindexAttr != null) {
            addParameter("tabindex", findString(tabindexAttr));
        }

        if (onclickAttr != null) {
            addParameter("onclick", findString(onclickAttr));
        }

        if (ondblclickAttr != null) {
            addParameter("ondblclick", findString(ondblclickAttr));
        }

        if (onmousedownAttr != null) {
            addParameter("onmousedown", findString(onmousedownAttr));
        }

        if (onmouseupAttr != null) {
            addParameter("onmouseup", findString(onmouseupAttr));
        }

        if (onmouseoverAttr != null) {
            addParameter("onmouseover", findString(onmouseoverAttr));
        }

        if (onmousemoveAttr != null) {
            addParameter("onmousemove", findString(onmousemoveAttr));
        }

        if (onmouseoutAttr != null) {
            addParameter("onmouseout", findString(onmouseoutAttr));
        }

        if (onfocusAttr != null) {
            addParameter("onfocus", findString(onfocusAttr));
        }

        if (onblurAttr != null) {
            addParameter("onblur", findString(onblurAttr));
        }

        if (onkeypressAttr != null) {
            addParameter("onkeypress", findString(onkeypressAttr));
        }

        if (onkeydownAttr != null) {
            addParameter("onkeydown", findString(onkeydownAttr));
        }

        if (onkeyupAttr != null) {
            addParameter("onkeyup", findString(onkeyupAttr));
        }

        if (onselectAttr != null) {
            addParameter("onselect", findString(onselectAttr));
        }

        if (onchangeAttr != null) {
            addParameter("onchange", findString(onchangeAttr));
        }

        if (cssClassAttr != null) {
            addParameter("cssClass", findString(cssClassAttr));
        }

        if (cssStyleAttr != null) {
            addParameter("cssStyle", findString(cssStyleAttr));
        }

        if (evaluateNameValue()) {
            Class valueClazz = getValueClassType();

            if (valueClazz != null) {
                if (valueAttr != null) {
                    addParameter("nameValue", findValue(valueAttr, valueClazz));
                } else if (name != null) {
                    String expr = name.toString();
                    if (ALT_SYNTAX) {
                        expr = "%{" + expr + "}";
                    }

                    addParameter("nameValue", findValue(expr, valueClazz));
                }
            } else {
                if (valueAttr != null) {
                    addParameter("nameValue", findValue(valueAttr));
                } else if (name != null) {
                    addParameter("nameValue", findValue(name.toString()));
                }
            }
        }

        if (id != null) {
            addParameter("id", getId());
        }

        FormTag formTag = (FormTag) findAncestorWithClass(this, FormTag.class);

        if (formTag != null) {
            addParameter("form", formTag.getParameters());

            // register ScriptValiationAware validators for this UI tag with the form
            Boolean validate = (Boolean) formTag.getParameters().get("validate");

            if ((validate != null) && validate.booleanValue() && (formTag.getActionClass() != null) && (formTag.getActionName() != null) && name != null) {
                findScriptingValidators(formTag, (String) name, formTag.getActionClass(), null);
            }
        }

        evaluateExtraParams(stack);
    }

    protected void mergeTemplate(String templateName) throws Exception {
        TemplateEngine engine = TemplateEngineManager.getTemplateEngine(templateName);
        if (engine == null) {
            throw new ConfigurationException("Unable to find a TemplateEngine for template " + templateName);
        }
        String finalTemplateName = engine.getFinalTemplateName(templateName);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Got template engine " + engine.getClass().getName() + " for template '" + templateName + "'" +
                    ((templateName.equals(finalTemplateName)) ? null : " final template name '" + finalTemplateName + "'"));
        }
        TemplateRenderingContext context = new TemplateRenderingContext(finalTemplateName, pageContext, getStack(), getParameters(), this);
        engine.renderTemplate(context);
    }

    /**
     * Finds all ScriptValidationAware validators that apply to the field covered by this tag.
     *
     * @param formTag      the parent form tag this tag is in
     * @param fieldName    the name of the field to validate (used for error message key)
     * @param fieldClass   the Class of the object the field is for
     * @param propertyName the actual property name to get validator for; if null, fieldName is used
     */
    private void findScriptingValidators(FormTag formTag, String fieldName, Class fieldClass, String propertyName) {
        List validators = ActionValidatorManager.getValidators(fieldClass, formTag.getActionName());
        String name = fieldName;

        if (propertyName != null) {
            name = propertyName;
        }

        for (Iterator iterator = validators.iterator(); iterator.hasNext();) {
            Validator validator = (Validator) iterator.next();

            if (!(validator instanceof ScriptValidationAware)) {
                continue;
            }

            ValidatorContext validatorContext = new DelegatingValidatorContext(fieldClass);

            if (validator instanceof FieldValidator) {
                FieldValidator fieldValidator = (FieldValidator) validator;

                // JavaScriptVisitorFieldValidators must validate model, not action
                if (validator instanceof JavaScriptVisitorFieldValidator) {
                    JavaScriptVisitorFieldValidator visitorValidator = (JavaScriptVisitorFieldValidator) validator;
                    String propName = null;
                    boolean visit;

                    if (visitorValidator.getFieldName().equals("model") && ModelDriven.class.isAssignableFrom(fieldClass)) {
                        visit = true;
                    } else {
                        String baseName = name;
                        int idx = name.indexOf(".");

                        if (idx != -1) {
                            baseName = name.substring(0, idx);
                            propName = name.substring(idx + 1);
                        }

                        visit = baseName.equals(visitorValidator.getFieldName());
                    }

                    if (visit) {
                        Class realFieldClass = visitorValidator.getValidatedClass();

                        if (realFieldClass == null) {
                            for (Iterator iterator1 = getStack().getRoot().iterator(); iterator1.hasNext();) {
                                Object o = iterator1.next();
                                try {
                                    PropertyDescriptor pd =
                                            OgnlRuntime.getPropertyDescriptor(o.getClass(), visitorValidator.getFieldName());
                                    realFieldClass = pd.getPropertyType();
                                    break;
                                } catch (Throwable t) {
                                    // just keep trying
                                }
                            }
                        }

                        if (realFieldClass != null) {
                            if (visitorValidator.isAppendPrefix()) {
                                findScriptingValidators(formTag, visitorValidator.getFieldName() + "." + name, realFieldClass, propName);
                            } else {
                                findScriptingValidators(formTag, name, realFieldClass, propName);
                            }
                        } else {
                            LOG.warn("Cannot figure out class of visited object");
                        }
                    }
                } else if (fieldValidator.getFieldName().equals(name)) {
                    validator.setValidatorContext(validatorContext);
                    formTag.registerValidator((ScriptValidationAware) fieldValidator, new HashMap(getParameters()));
                }
            } else {
                validator.setValidatorContext(validatorContext);
                formTag.registerValidator((ScriptValidationAware) validator, new HashMap(getParameters()));
            }
        }
    }

    private String setupPath(String path, boolean prefix) {
        if ((path != null) && (path != "")) {
            if (prefix) {
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }

            if (!path.endsWith("/")) {
                path += "/";
            }
        }

        return path;
    }
}

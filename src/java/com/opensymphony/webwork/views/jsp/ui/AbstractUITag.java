/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.jsp.ParameterizedTagSupport;
import com.opensymphony.webwork.views.velocity.VelocityManager;
import com.opensymphony.webwork.validators.ScriptValidationAware;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.validator.ActionValidatorManager;
import com.opensymphony.xwork.validator.FieldValidator;
import com.opensymphony.xwork.validator.Validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import javax.servlet.jsp.JspException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public abstract class AbstractUITag extends ParameterizedTagSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log LOG = LogFactory.getLog(AbstractUITag.class);

    /**
     * The name of the default theme used by WW2.
     */
    public static String THEME;
    protected static VelocityManager velocityManager = VelocityManager.getInstance();
    protected static VelocityEngine velocityEngine = velocityManager.getVelocityEngine();

    static {
        try {
            THEME = Configuration.getString("webwork.ui.theme");
            if (!THEME.endsWith("/")) {
                THEME += "/";
            }
        } catch (IllegalArgumentException e) {
            LOG.warn("Unable to find 'webwork.ui.theme' property setting. Defaulting to xhtml", e);
            THEME = "xhtml";
        }
    }

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String disabledAttr;
    protected String labelAttr;
    protected String labelPositionAttr;
    protected String nameAttr;
    protected String onchangeAttr;
    protected String requiredAttr;
    protected String tabindexAttr;
    protected String templateAttr;
    protected String themeAttr;
    protected String theme;
    protected String templateDir;
    protected String valueAttr;
    protected String cssClassAttr;
    protected String cssStyleAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

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

    public void setOnchange(String onchange) {
        this.onchangeAttr = onchange;
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

    public void setTheme(String aName) {
        themeAttr = aName;
    }

    public void setValue(String aValue) {
        valueAttr = aValue;
    }

    public void setCssClass(String aCssClass) {
        cssClassAttr = aCssClass;
    }

    public void setCssStyle(String aCssStyle) {
        this.cssStyleAttr = aCssStyle;
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

    public String getTheme() {
       // If theme set is not explicitly given,
       // try to find attribute which states the theme set to use
       if ((theme == null) || (theme == "")) {
          theme = (String) pageContext.findAttribute("theme");
       }

       // Default template set
       if ((theme == null) || (theme == "")) {
          theme = Configuration.getString("webwork.ui.theme");
       }

       return theme;
    }

    public String getTemplateDir() {
       // If templateDir is not explicitly given,
       // try to find attribute which states the dir set to use
       if ((templateDir == null) || (templateDir == "")) {
         templateDir = (String) pageContext.findAttribute("templateDir");
       }

       // Default template set
       if ((templateDir == null) || (templateDir == "")) {
          templateDir = Configuration.getString("webwork.ui.templateDir");
       }

       if ((templateDir == null) || (templateDir == "")) {
          templateDir = "template";
       }

       return templateDir;
    }

    /**
     * @param myTemplate
     * @param myDefaultTemplate
     */
    protected String buildTemplateName(String myTemplate, String myDefaultTemplate) {
        if (themeAttr != null)
        {
            theme = findString(themeAttr);
        }

        String template = null;
        if (myTemplate == null) {
            template = myDefaultTemplate;
        } else {
            template = findString(myTemplate);
        }
        return "/" + getTemplateDir() + "/" + getTheme() + "/" + template;
    }

    protected void evaluateExtraParams(OgnlValueStack stack) {
    }

    protected void evaluateParams(OgnlValueStack stack) {
        Object name = null;

        if (nameAttr != null) {
            name = findValue(nameAttr, String.class);
            addParameter("name", name);
        }

        if (labelAttr != null) {
            addParameter("label", findValue(labelAttr, String.class));
        }

        if (labelPositionAttr != null) {
            addParameter("labelPosition", findValue(labelPositionAttr, String.class));
        }

        if (requiredAttr != null) {
            addParameter("required", findValue(requiredAttr, Boolean.class));
        }

        if (disabledAttr != null) {
            addParameter("disabled", findValue(disabledAttr, Boolean.class));
        }

        if (tabindexAttr != null) {
            addParameter("tabindex", findValue(tabindexAttr, String.class));
        }

        if (onchangeAttr != null) {
            addParameter("onchange", findValue(onchangeAttr, String.class));
        }

        if (cssClassAttr != null) {
            addParameter("cssClass", findValue(cssClassAttr, String.class));
        }

        if (cssStyleAttr != null) {
            addParameter("cssStyle", findValue(cssStyleAttr, String.class));
        }

        FormTag tag = (FormTag) findAncestorWithClass(this, FormTag.class);
        if (tag != null) {
            addParameter("form", tag.getParameters());
        }

        if (evaluateNameValue()) {
            Class valueClazz = getValueClassType();

            if (valueClazz != null) {
                if (valueAttr != null) {
                    addParameter("nameValue", findValue(valueAttr, valueClazz));
                } else if (name != null) {
                    addParameter("nameValue", findValue(name.toString(), valueClazz));
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

        // now let's do some JavaScript stuff. or something
        if (tag != null && tag.getActionClass() != null && tag.getActionName() != null) {
            List validators = ActionValidatorManager.getValidators(tag.getActionClass(), tag.getActionName());
            for (Iterator iterator = validators.iterator(); iterator.hasNext();) {
                Validator validator = (Validator) iterator.next();
                if (validator instanceof ScriptValidationAware) {
                    ScriptValidationAware fieldValidator = (ScriptValidationAware) validator;
                    if (fieldValidator.getFieldName().equals(name)) {
                        tag.registerValidator(name, fieldValidator, new HashMap(getParameters()));
                    }
                }
            }
        }

        evaluateExtraParams(stack);
    }

    protected boolean evaluateNameValue() {
        return true;
    }

    protected void mergeTemplate(String templateName) throws Exception {
        Template t = velocityEngine.getTemplate(templateName);
        Context context = velocityManager.createContext(getStack(), pageContext.getRequest(), pageContext.getResponse());

        Writer outputWriter = pageContext.getOut();

        /**
         * Make the OGNL stack available to the velocityEngine templates.
         * todo Consider putting all the VelocityServlet Context values in - after all, if we're already sending
         * the request, it might also make sense for consistency to send the page and res and any others.
         */
        context.put("tag", this);
        context.put("parameters", getParameters());

        t.merge(context, outputWriter);
    }
}

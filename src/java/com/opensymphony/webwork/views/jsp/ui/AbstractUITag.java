/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.validators.ScriptValidationAware;
import com.opensymphony.webwork.views.jsp.ParameterizedTagSupport;
import com.opensymphony.webwork.views.velocity.VelocityManager;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.validator.ActionValidatorManager;
import com.opensymphony.xwork.validator.Validator;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Writer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public abstract class AbstractUITag extends ParameterizedTagSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected static VelocityManager velocityManager = VelocityManager.getInstance();
    protected static VelocityEngine velocityEngine = velocityManager.getVelocityEngine();
    private static final Log LOG = LogFactory.getLog(AbstractClosingUITag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String cssClassAttr;
    protected String cssStyleAttr;
    protected String disabledAttr;
    protected String labelAttr;
    protected String labelPositionAttr;
    protected String nameAttr;
    protected String onchangeAttr;
    protected String requiredAttr;
    protected String tabindexAttr;
    protected String templateAttr;
    protected String templateDir;
    protected String theme;
    protected String themeAttr;
    protected String valueAttr;

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
        if (themeAttr != null) {
            theme = findString(themeAttr);
        }

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
        if ((tag != null) && (tag.getActionClass() != null) && (tag.getActionName() != null)) {
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

    protected void mergeTemplate(String templateName) throws Exception {
        Template t = velocityEngine.getTemplate(templateName);
        Context context = velocityManager.createContext(getStack(), (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());

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

    private String setupPath(String path, boolean prefix) {
        if (path != null) {
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

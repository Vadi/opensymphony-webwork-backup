/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.jsp.ParameterizedTagSupport;
import com.opensymphony.webwork.views.velocity.VelocityManager;

import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.Writer;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public abstract class AbstractUITag extends ParameterizedTagSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log LOG = LogFactory.getLog(AbstractUITag.class);

    /**
     * The name of the default theme used by WW2.
     */
    public static String THEME;
    protected static VelocityEngine velocityEngine = VelocityManager.getVelocityEngine();

    static {
        try {
            THEME = Configuration.getString("webwork.ui.theme");
        } catch (IllegalArgumentException e) {
            LOG.warn("Unable to find 'webwork.ui.theme' property setting. Defaulting to /template/xhtml/", e);
            THEME = "/template/xhtml/";
        }
    }

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String labelAttr;
    protected String labelPositionAttr;
    protected String nameAttr;
    protected String requiredAttr;
    protected String templateAttr;
    protected String themeAttr;
    protected String valueAttr;
    protected String disabledAttr;
    protected String tabindexAttr;
    protected String onchangeAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

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

    public void setTemplate(String aName) {
        templateAttr = aName;
    }

    public void setTheme(String aName) {
        themeAttr = aName;
    }

    public void setValue(String aValue) {
        valueAttr = aValue;
    }

    public void setDisabled(String disabled) {
        this.disabledAttr = disabled;
    }

    public void setTabindex(String tabindex) {
        this.tabindexAttr = tabindex;
    }

    public void setOnchange(String onchange) {
        this.onchangeAttr = onchange;
    }

    public int doEndTag() throws JspException {
        OgnlValueStack stack = getValueStack();
        evaluateParams(stack);

        try {
            mergeTemplate(this.getTemplateName());

            return EVAL_BODY_INCLUDE;
        } catch (Exception e) {
            LOG.error("Could not generate UI template", e);

            return SKIP_BODY;
        }
    }

    public int doStartTag() throws JspException {
        /**
         * Migrated instantiation of the params HashMap to here from the constructor to facilitate implementation of the
         * release() method.
         */
        getParams().clear();

        return EVAL_BODY_INCLUDE;
    }

    /**
     * A contract that requires each concrete UI Tag to specify which template should be used as a default.  For
     * example, the CheckboxTab might return "checkbox.vm" while the RadioTag might return "radio.vm".  This value
     * <strong>not</strong> begin with a '/' unless you intend to make the path absolute rather than relative to the
     * current theme.
     * @return The name of the template to be used as the default.
     */
    protected abstract String getDefaultTemplate();

    /**
     * Find the name of the Velocity template that we should use.
     * @return The name of the Velocity template that we should use. This value should begin with a '/'
     */
    protected String getTemplateName() {
        return buildTemplateName(templateAttr, getDefaultTemplate());
    }

    /**
     *
     * @param myTemplate
     * @param myDefaultTemplate
     * @return
     */
    protected String buildTemplateName(String myTemplate, String myDefaultTemplate) {
        /**
         * If no used defined template has been speccified, apply the appropriate theme to the default template
         */
        if (myTemplate == null) {
            if (this.themeAttr == null) {
                return THEME + myDefaultTemplate;
            } else if (this.themeAttr.endsWith("/")) {
                return this.themeAttr + myDefaultTemplate;
            } else {
                return this.themeAttr + "/" + myDefaultTemplate;
            }

            /**
             * If a theme has been specified and it begins with a '/', allow this to override any theme value provided.
             */
        } else if (myTemplate.startsWith("/")) {
            return myTemplate;

            /**
             * Otherwise, apply the appropriate theme to the user specified template
             */
        } else {
            if (this.themeAttr == null) {
                return THEME + myTemplate;
            } else if (this.themeAttr.endsWith("/")) {
                return this.themeAttr + myTemplate;
            } else {
                return this.themeAttr + "/" + myTemplate;
            }
        }
    }

    protected void evaluateExtraParams(OgnlValueStack stack) {
    }

    protected void evaluateParams(OgnlValueStack stack) {
        Object name = null;

        if (nameAttr != null) {
            name = stack.findValue(nameAttr, String.class);
            addParam("name", name);
        }

        if (labelAttr != null) {
            addParam("label", stack.findValue(labelAttr, String.class));
        }

        if (labelPositionAttr != null) {
            addParam("labelPosition", stack.findValue(labelPositionAttr, String.class));
        }

        if (requiredAttr != null) {
            addParam("required", stack.findValue(requiredAttr, Boolean.class));
        }

        if (disabledAttr != null) {
            addParam("disabled", stack.findValue(disabledAttr, Boolean.class));
        }

        if (tabindexAttr != null) {
            addParam("tabindex", stack.findValue(tabindexAttr, String.class));
        }

        if (onchangeAttr != null) {
            addParam("onchange", stack.findValue(onchangeAttr, String.class));
        }

        Class valueClazz = getValueClassType();
        if (valueClazz != null) {
            if (valueAttr != null) {
                addParam("nameValue", stack.findValue(valueAttr, valueClazz));
            } else if (name != null) {
                addParam("nameValue", stack.findValue(name.toString(), valueClazz));
            }
        } else {
            if (valueAttr != null) {
                addParam("nameValue", stack.findValue(valueAttr));
            } else if (name != null) {
                addParam("nameValue", stack.findValue(name.toString()));
            }
        }

        if (id != null) {
            addParam("id", getId());
        }

        evaluateExtraParams(stack);
    }

    protected Class getValueClassType() {
        return String.class;
    }

    protected void mergeTemplate(String templateName) throws Exception {
        Template t = velocityEngine.getTemplate(templateName);
        Context context = VelocityManager.createContext(getValueStack(), pageContext.getRequest(), pageContext.getResponse());

        Writer outputWriter = pageContext.getOut();

        /**
         * Make the OGNL stack available to the velocityEngine templates.
         * todo Consider putting all the VelocityServlet Context values in - after all, if we're already sending
         * the request, it might also make sense for consistency to send the page and res and any others.
         */
        context.put("tag", this);
        context.put("parameters", getParams());

        t.merge(context, outputWriter);
    }

    /**
     * initialize the
     * @throws JspException
     */
    protected VelocityEngine newVelocityEngine() throws Exception {
        Properties p = new Properties();

        ServletConfig config = pageContext.getServletConfig();

        if (config != null) {
            Enumeration enum = config.getInitParameterNames();

            while (enum.hasMoreElements()) {
                String key = (String) enum.nextElement();
                p.setProperty(key, config.getInitParameter(key));
            }
        }

        if (p.getProperty(Velocity.RESOURCE_LOADER) == null) {
            p.setProperty(Velocity.RESOURCE_LOADER, "file, class");

            ServletContext context = pageContext.getServletContext();

            if (context != null) {
                String path = context.getRealPath("");

                if (path != null) {
                    p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
                }
            }

            /**
             * Refactored the Velocity templates for the WebWork taglib into the classpath from the web path.  This will
             * enable WebWork projects to have access to the templates by simply including the WebWork jar file.
             * Unfortunately, there does not appear to be a macro for the class loader keywords
             * Matt Ho - Mon Mar 17 00:21:46 PST 2003
             */
            p.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
            p.setProperty("class.resource.loader.class", "com.opensymphony.webwork.views.velocity.WebWorkResourceLoader");
        }

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init(p);

        return velocityEngine;
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.jsp.ParameterizedTag;
import com.opensymphony.webwork.views.velocity.Renderer;
import com.opensymphony.webwork.views.velocity.VelocityManager;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.Writer;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public abstract class AbstractUITag extends TagSupport implements ParameterizedTag, Renderer {
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
            LOG.warn("Unable to find 'webwork.ui.theme' property setting. Defaulting to /templates/xhtml/", e);
            THEME = "/templates/xhtml/";
        }
    }

    //~ Instance fields ////////////////////////////////////////////////////////

    Map params;
    Object actualValue;
    Object name;
    Object value;
    String id;
    String label;
    String onchange;
    String template;
    String theme;
    Writer writer;
    boolean required;

    //~ Methods ////////////////////////////////////////////////////////////////

    public Object getActualValue() {
        return actualValue;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getName() {
        return name;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public String getOnchange() {
        return onchange;
    }

    public void setOut(Writer writer) {
        this.writer = writer;
    }

    /**
    * com.opensymphony.webwork.views.jsp.ParameterizedTag implementation
    * @return a Map of user specified Map parameters
    * @see ParameterizedTag
    */
    public Map getParams() {
        if (params == null) {
            params = new HashMap();
        }

        return params;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean getRequired() {
        return required;
    }

    /**
    * for Velocity
    * @return
    * @throws JspException
    */
    public String getShow() throws JspException {
        int result = doStartTag();
        doEndTag();

        return "";
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    /**
    * simple setter method for Velocity
    * @param id
    */
    public Object Id(String id) {
        setId(id);

        return this;
    }

    /**
    * simple setter method for Velocity
    * @param label
    */
    public Object Label(String label) {
        setLabel(label);

        return this;
    }

    /**
    * simple setter method for Velocity
    * @param name
    */
    public Object Name(Object name) {
        setName(name);

        return this;
    }

    /**
    * simple setter method for Velocity
    * @param onchange
    */
    public Object Onchange(String onchange) {
        setOnchange(onchange);

        return this;
    }

    public Object Param(String key, Object value) {
        this.addParam(key, value);

        return this;
    }

    /**
    * simple setter method for Velocity
    * @param label
    */
    public Object Required(boolean required) {
        setRequired(required);

        return this;
    }

    /**
    * simple setter method for Velocity
    * @param template
    */
    public Object Template(String template) {
        setTemplate(template);

        return this;
    }

    /**
    * simple setter method for Velocity
    * @param theme
    */
    public Object Theme(String theme) {
        setTheme(theme);

        return this;
    }

    /**
    * simple setter method for Velocity
    * @param value
    */
    public Object Value(Object value) {
        setValue(value);

        return this;
    }

    /**
    * com.opensymphony.webwork.views.jsp.ParameterizedTag implementation
    * @param key the String name of a parameter to add
    * @param value the value associated with that parameter
    * @see ParameterizedTag
    */
    public void addParam(String key, Object value) {
        this.getParams().put(key, value);
    }

    public int doEndTag() throws JspException {
        OgnlValueStack stack = ActionContext.getContext().getValueStack();

        if (stack != null) {
            if (value != null) {
                actualValue = stack.findValue(value.toString());
            } else if (name != null) {
                actualValue = stack.findValue(name.toString());
            }
        }

        try {
            mergeTemplate(this.getTemplateName());

            return EVAL_BODY_INCLUDE;
        } catch (Exception e) {
            e.printStackTrace();

            return SKIP_BODY;
        } finally {
            this.params = null;
            this.id = null;
            this.label = null;
            this.name = null;
            this.onchange = null;
            this.theme = null;
            this.template = null;
            this.value = null;
        }
    }

    public int doStartTag() throws JspException {
        /**
        * Migrated instantiation of the params HashMap to here from the constructor to facilitate implementation of the
        * release() method.
        */
        this.params = new HashMap();

        return EVAL_BODY_INCLUDE;
    }

    /**
    * Clears all the instance variables to allow this instance to be reused.
    */
    public void release() {
        super.release();
        this.params = null;
        this.id = null;
        this.label = null;
        this.required = false;
        this.name = null;
        this.onchange = null;
        this.theme = null;
        this.template = null;
        this.value = null;
    }

    public void render(Context context, Writer writer) throws Exception {
        OgnlValueStack stack = ActionContext.getContext().getValueStack();

        if (stack != null) {
            if (value != null) {
                actualValue = stack.findValue(value.toString());
            } else if (name != null) {
                actualValue = stack.findValue(name.toString());
            }
        }

        Template template = velocityEngine.getTemplate(this.getTemplateName());
        template.merge(context, writer);
    }

    /**
    * For Velocity
    * @param label
    * @return
    * @throws JspException
    */
    public Object set(String label) throws JspException {
        return this.set(label, null, null, null, null, null, null);
    }

    /**
    * For Velocity
    * @param label
    * @param name
    * @return
    * @throws JspException
    */
    public Object set(String label, Object name) throws JspException {
        return this.set(label, name, null, null, null, null, null);
    }

    /**
    * For Velocity
    * @param label
    * @param name
    * @param value
    * @return
    * @throws JspException
    */
    public Object set(String label, Object name, Object value) throws JspException {
        return this.set(label, name, value, null, null, null, null);
    }

    /**
    * For Velocity
    * @param label
    * @param name
    * @param value
    * @param id
    * @return
    * @throws JspException
    */
    public Object set(String label, Object name, Object value, String id) throws JspException {
        return this.set(label, name, value, id, null, null, null);
    }

    /**
    * For Velocity
    * @param label
    * @param name
    * @param value
    * @param id
    * @param onchange
    * @return
    * @throws JspException
    */
    public Object set(String label, Object name, Object value, String id, String onchange) throws JspException {
        return this.set(label, name, value, id, onchange, null, null);
    }

    /**
    * For Velocity
    * @param label
    * @param name
    * @param value
    * @param id
    * @param onchange
    * @param template
    * @return
    * @throws JspException
    */
    public Object set(String label, Object name, Object value, String id, String onchange, String template) throws JspException {
        return this.set(label, name, value, id, onchange, template, null);
    }

    /**
    * For Velocity
    * @param label
    * @param name
    * @param value
    * @param id
    * @param onchange
    * @param template
    * @param theme
    * @return
    * @throws JspException
    */
    public Object set(String label, Object name, Object value, String id, String onchange, String template, String theme) throws JspException {
        this.setLabel(label);
        this.setName(name);
        this.setValue(value);
        this.setId(id);
        this.setOnchange(onchange);
        this.setTemplate(template);
        this.setTheme(theme);

        return this;
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
        return buildTemplateName(getTemplate(), getDefaultTemplate());
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
            if (this.theme == null) {
                return THEME + myDefaultTemplate;
            } else if (this.theme.endsWith("/")) {
                return this.theme + myDefaultTemplate;
            } else {
                return this.theme + "/" + myDefaultTemplate;
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
            if (this.theme == null) {
                return THEME + myTemplate;
            } else if (this.theme.endsWith("/")) {
                return this.theme + myTemplate;
            } else {
                return this.theme + "/" + myTemplate;
            }
        }
    }

    protected void mergeTemplate(String templateName) throws Exception {
        Template t = velocityEngine.getTemplate(templateName);
        Context context = VelocityManager.createContext(pageContext.getServletConfig(), pageContext.getRequest(), pageContext.getResponse());

        if (writer == null) {
            writer = pageContext.getOut();
        }

        /**
        * Make the OGNL stack available to the velocityEngine templates.
        * @todo Consider putting all the VelocityServlet Context values in - after all, if we're already sending
        * the request, it might also make sense for consistency to send the page and res and any others.
        */
        context.put("tag", this);

        t.merge(context, writer);
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

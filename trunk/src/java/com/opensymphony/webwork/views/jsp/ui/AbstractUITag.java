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

    Map params = new HashMap();
    String id;
    String template;
    String theme;
    Writer writer;
    boolean required;

    //~ Methods ////////////////////////////////////////////////////////////////


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLabel(String label) {
        params.put("label", label);
    }

    public void setName(Object name) {
        params.put("name", name);
    }

    public void setOnchange(String onchange) {
        params.put("onchange", onchange);
    }


    /**
     * com.opensymphony.webwork.views.jsp.ParameterizedTag implementation
     * @return a Map of user specified Map parameters
     * @see ParameterizedTag
     */
    public Map getParams() {
        return params;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean getRequired() {
        return required;
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
        params.put("value", value);
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
        evaluateActualValue();

        try {
            mergeTemplate(this.getTemplateName());

            return EVAL_BODY_INCLUDE;
        } catch (Exception e) {
            LOG.error("Could npt generate UI template", e);

            return SKIP_BODY;
        } finally {
            nullifyVars();
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
        nullifyVars();
    }

    private void nullifyVars() {
        this.params = null;
        this.id = null;
        this.required = false;
        this.theme = null;
        this.template = null;
    }

    public void render(Context context, Writer writer) throws Exception {
        evaluateActualValue();

        Template template = velocityEngine.getTemplate(this.getTemplateName());
        template.merge(context, writer);
    }

    public Object set(String label) throws JspException {
        return this.set(label, null, null, null, null, null, null);
    }

    public Object set(String label, Object name) throws JspException {
        return this.set(label, name, null, null, null, null, null);
    }

    public Object set(String label, Object name, Object value) throws JspException {
        return this.set(label, name, value, null, null, null, null);
    }

    public Object set(String label, Object name, Object value, String id) throws JspException {
        return this.set(label, name, value, id, null, null, null);
    }

    public Object set(String label, Object name, Object value, String id, String onchange) throws JspException {
        return this.set(label, name, value, id, onchange, null, null);
    }

    public Object set(String label, Object name, Object value, String id, String onchange, String template) throws JspException {
        return this.set(label, name, value, id, onchange, template, null);
    }

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

    /**
     * this method derives the value of this.actualValue from the value stack if this.actualValue has not
     * already been set
     */
    protected void evaluateActualValue() {
    }

    protected void mergeTemplate(String templateName) throws Exception {
        Template t = velocityEngine.getTemplate(templateName);
        Context context = VelocityManager.createContext(pageContext.getServletConfig(), pageContext.getRequest(), pageContext.getResponse());

        Writer outputWriter = writer;
        if (outputWriter == null) {
            outputWriter = pageContext.getOut();
        }

        /**
         * Make the OGNL stack available to the velocityEngine templates.
         * todo Consider putting all the VelocityServlet Context values in - after all, if we're already sending
         * the request, it might also make sense for consistency to send the page and res and any others.
         */
        context.put("tag", this);

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

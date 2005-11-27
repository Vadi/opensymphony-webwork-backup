package com.opensymphony.webwork.components;

import com.opensymphony.webwork.components.template.Template;
import com.opensymphony.webwork.components.template.TemplateEngine;
import com.opensymphony.webwork.components.template.TemplateEngineManager;
import com.opensymphony.webwork.components.template.TemplateRenderingContext;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * UIBean is the standard superclass of all webwork UI componentns.
 * It defines common webwork and html properties all UI components should present for usage.
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 */
public abstract class UIBean extends Component {
    private static final Log LOG = LogFactory.getLog(UIBean.class);

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public UIBean(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack);
        this.request = request;
        this.response = response;
    }

    // The template to use, overrides the default one.
    protected String template;

    // templateDir and theme attributes
    protected String templateDir;
    protected String theme;

    protected String cssClass;
    protected String cssStyle;
    protected String disabled;
    protected String label;
    protected String labelPosition;
    protected String name;
    protected String required;
    protected String tabindex;
    protected String value;

    // HTML scripting events attributes
    protected String onclick;
    protected String ondblclick;
    protected String onmousedown;
    protected String onmouseup;
    protected String onmouseover;
    protected String onmousemove;
    protected String onmouseout;
    protected String onfocus;
    protected String onblur;
    protected String onkeypress;
    protected String onkeydown;
    protected String onkeyup;
    protected String onselect;
    protected String onchange;

    public void end(Writer writer, String body) {
        evaluateParams();
        try {
            super.end(writer, body);
            mergeTemplate(writer, buildTemplateName(template, getDefaultTemplate()));
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    protected Template buildTemplateName(String myTemplate, String myDefaultTemplate) {
        String template = myDefaultTemplate;

        if (myTemplate != null) {
            template = findString(myTemplate);
        }

        String templateDir = getTemplateDir();
        String theme = getTheme();

        return new Template(templateDir, theme, template);

    }

    protected void mergeTemplate(Writer writer, Template template) throws Exception {
        final TemplateEngine engine = TemplateEngineManager.getTemplateEngine(template);
        if (engine == null) {
            throw new ConfigurationException("Unable to find a TemplateEngine for template " + template);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Rendering template " + template);
        }

        final TemplateRenderingContext context = new TemplateRenderingContext(template, writer, getStack(), getParameters(), this);
        engine.renderTemplate(context);
    }

    public String getTemplateDir() {
        String templateDir = null;

        if (this.templateDir != null) {
            templateDir = findString(this.templateDir);
        }

        // If templateDir is not explicitly given,
        // try to find attribute which states the dir set to use
        if ((templateDir == null) || (templateDir.equals(""))) {
            templateDir = (String) stack.findValue("#attr.templateDir");
        }

        // Default template set
        if ((templateDir == null) || (templateDir.equals(""))) {
            templateDir = Configuration.getString("webwork.ui.templateDir");
        }

        // Defaults to 'template'
        if ((templateDir == null) || (templateDir.equals(""))) {
            templateDir = "template";
        }

        return templateDir;
    }

    public String getTheme() {
        String theme = null;

        if (this.theme != null) {
            theme = findString(this.theme);
        }

        if (theme == null || theme.equals("")) {
            Form form = (Form) findAncestor(Form.class);
            if (form != null) {
                theme = form.getTheme();
            }
        }

        // If theme set is not explicitly given,
        // try to find attribute which states the theme set to use
        if ((theme == null) || (theme.equals(""))) {
            theme = (String) stack.findValue("#attr.theme");
        }

        // Default theme set
        if ((theme == null) || (theme.equals(""))) {
            theme = Configuration.getString("webwork.ui.theme");
        }

        return theme;
    }

    public void evaluateParams() {
        addParameter("templateDir", getTemplateDir());
        addParameter("theme", getTheme());

        String name = null;

        if (this.name != null) {
            name = findString(this.name);
            addParameter("name", name);
        }

        if (label != null) {
            addParameter("label", findString(label));
        }

        if (labelPosition != null) {
            addParameter("labelposition", findString(labelPosition));
        }

        if (required != null) {
            addParameter("required", findValue(required, Boolean.class));
        }

        if (disabled != null) {
            addParameter("disabled", findValue(disabled, Boolean.class));
        }

        if (tabindex != null) {
            addParameter("tabindex", findString(tabindex));
        }

        if (onclick != null) {
            addParameter("onclick", findString(onclick));
        }

        if (ondblclick != null) {
            addParameter("ondblclick", findString(ondblclick));
        }

        if (onmousedown != null) {
            addParameter("onmousedown", findString(onmousedown));
        }

        if (onmouseup != null) {
            addParameter("onmouseup", findString(onmouseup));
        }

        if (onmouseover != null) {
            addParameter("onmouseover", findString(onmouseover));
        }

        if (onmousemove != null) {
            addParameter("onmousemove", findString(onmousemove));
        }

        if (onmouseout != null) {
            addParameter("onmouseout", findString(onmouseout));
        }

        if (onfocus != null) {
            addParameter("onfocus", findString(onfocus));
        }

        if (onblur != null) {
            addParameter("onblur", findString(onblur));
        }

        if (onkeypress != null) {
            addParameter("onkeypress", findString(onkeypress));
        }

        if (onkeydown != null) {
            addParameter("onkeydown", findString(onkeydown));
        }

        if (onkeyup != null) {
            addParameter("onkeyup", findString(onkeyup));
        }

        if (onselect != null) {
            addParameter("onselect", findString(onselect));
        }

        if (onchange != null) {
            addParameter("onchange", findString(onchange));
        }

        if (cssClass != null) {
            addParameter("cssClass", findString(cssClass));
        }

        if (cssStyle != null) {
            addParameter("cssStyle", findString(cssStyle));
        }

        // see if the value was specified as a parameter already
        if (parameters.containsKey("value")) {
            parameters.put("nameValue", parameters.get("value"));
        } else {
            if (evaluateNameValue()) {
                final Class valueClazz = getValueClassType();

                if (valueClazz != null) {
                    if (value != null) {
                        addParameter("nameValue", findValue(value, valueClazz));
                    } else if (name != null) {
                        String expr = name.toString();
                        if (altSyntax()) {
                            expr = "%{" + expr + "}";
                        }

                        addParameter("nameValue", findValue(expr, valueClazz));
                    }
                } else {
                    if (value != null) {
                        addParameter("nameValue", findValue(value));
                    } else if (name != null) {
                        addParameter("nameValue", findValue(name.toString()));
                    }
                }
            }
        }

        final Form form = (Form) findAncestor(Form.class);

        if (id != null) {
            // this check is needed for backwards compatibility with 2.1.x
            if (altSyntax()) {
                addParameter("id", findString(id));
            } else {
                addParameter("id", id);
            }
        } else if (form != null) {
            addParameter("id", form.getParameters().get("id") + "_" + escape(name));
        }

        if (form != null) {
            addParameter("form", form.getParameters());
        }

        evaluateExtraParams();
    }

    protected String escape(String name) {
        // escape any possible values that can make the ID painful to work with in JavaScript
        if (name != null) {
            return name.replaceAll("[\\.\\[\\]]", "_");
        } else {
            return "";
        }
    }

    protected void evaluateExtraParams() {
    }

    protected boolean evaluateNameValue() {
        return true;
    }

    protected Class getValueClassType() {
        return String.class;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="The theme (other than default) to use for renedring the element"
      */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTemplate() {
        return template;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="The template (other than default) to use for renedring the element"
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="The css class to use for element"
     */
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="The css style definitions for element ro use"
     */
    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html disabled attribute on rendered html element"
     */
    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    /**
     * @jsp.attribute required="false"
     * description="Label expression used for rendering a element specific label"
     * rtexprvalue="true"
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * @deprecated please use {@link #setLabelPosition} instead
     */
    public void setLabelposition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="The name to set for element"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @jsp.attribute  required="false"  rtexprvalue="true"
     * description="If set to true, the rendered element will inidicate that input is required"
     */
    public void setRequired(String required) {
        this.required = required;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html tabindex attribute on rendered html element"
     */
    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Preset the value of input element."
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onclick attribute on rendered html element"
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html ondblclick attribute on rendered html element"
     */
    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onmousedown attribute on rendered html element"
     */
    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onmouseup attribute on rendered html element"
     */
    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onmouseover attribute on rendered html element"
     */
    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onmousemove attribute on rendered html element"
     */
    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onmouseout attribute on rendered html element"
     */
    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onfocus attribute on rendered html element"
     */
    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onblur attribute on rendered html element"
     */
    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onkeypress attribute on rendered html element"
     */
    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onkeydown attribute on rendered html element"
     */
    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onkeyup attribute on rendered html element"
     */
    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onselect attribute on rendered html element"
     */
    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set the html onchange attribute on rendered html element"
     */
    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }
}
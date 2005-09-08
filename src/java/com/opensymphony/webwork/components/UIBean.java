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
 * User: plightbo
 * Date: Jul 1, 2005
 * Time: 11:14:38 PM
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

        Object name = null;

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

        if (evaluateNameValue()) {
            final Class valueClazz = getValueClassType();

            if (valueClazz != null) {
                if (value != null) {
                    addParameter("nameValue", findValue(value, valueClazz));
                } else if (name != null) {
                    String expr = name.toString();
                    if (ALT_SYNTAX) {
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

        final Form form = (Form) findAncestor(Form.class);

        if (id != null) {
            addParameter("id", id);
        } else if (form != null) {
            addParameter("id", form.getParameters().get("id") + "_" + name);
        }

        if (form != null) {
            addParameter("form", form.getParameters());
        }

        evaluateExtraParams();
    }

    protected void evaluateExtraParams() {
    }

    protected boolean evaluateNameValue() {
        return true;
    }

    protected Class getValueClassType() {
        return String.class;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }
}

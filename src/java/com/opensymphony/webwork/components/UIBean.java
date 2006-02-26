/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import com.opensymphony.webwork.components.template.Template;
import com.opensymphony.webwork.components.template.TemplateEngine;
import com.opensymphony.webwork.components.template.TemplateEngineManager;
import com.opensymphony.webwork.components.template.TemplateRenderingContext;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.views.util.ContextUtil;
import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.List;

/**
 * UIBean is the standard superclass of all webwork UI componentns.
 * It defines common webwork and html properties all UI components should present for usage.
 * 
 * <!-- START SNIPPET: templateRelatedAttributes -->
 * 
 * <table border="1">
 *    <thead>
 *       <tr>
 *          <td>Attribute</td>
 *          <td>Theme</td>
 *          <td>Data Types</td>
 *          <td>Description</td>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>templateDir</td>
 *          <td>n/a</td>
 *          <td>String</td>
 *          <td>define the template directory</td>
 *       </td>
 *       <tr>
 *          <td>theme</td>
 *          <td>n/a</td>
 *          <td>String</td>
 *          <td>define the theme name</td>
 *       </td>
 *       <tr>
 *          <td>template</td>
 *          <td>n/a</td>
 *          <td>String</td>
 *          <td>define the template name</td>
 *       </td>
 *    </tbody>
 * </table>
 * 
 * <!-- END SNIPPET: templateRelatedAttributes -->
 * 
 * <p/>
 * 
 * <!-- START SNIPPET: generalAttributes -->
 * 
 * <table border="1">
 *    <thead>
 *       <tr>
 *          <td>Attribute</td>
 *          <td>Theme</td>
 *          <td>Data Types</td>
 *          <td>Description</td>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>cssClass</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>define html class attribute</td>
 *       </tr>
 *       <tr>
 *          <td>cssStyle</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>define html style attribute</td>
 *       </tr>
 *       <tr>
 *          <td>title</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>define html title attribute</td>
 *       </tr>
 *       <tr>
 *          <td>disabled</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>define html disabled attribute</td>
 *       </tr>
 *       <tr>
 *          <td>label</td>
 *          <td>xhtml</td>
 *          <td>String</td>
 *          <td>define label of form element</td>
 *       </tr>
 *       <tr>
 *          <td>labelPosition</td>
 *          <td>xhtml</td>
 *          <td>String</td>
 *          <td>define label position of form element (top/left), default to left</td>
 *       </tr>
 *       <tr>
 *          <td>name</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>Form Element's field name mapping</td>
 *       </tr>
 *       <tr>
 *          <td>required</td>
 *          <td>xhtml</td>
 *          <td>Boolean</td>
 *          <td>add * to label (true to add false otherwise)</td>
 *       </tr>
 *       <tr>
 *          <td>tabIndex</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>define html tabindex attribute</td>
 *       </tr>
 *       <tr>
 *          <td>value</td>
 *          <td>simple</td>
 *          <td>Object</td>
 *          <td>define value of form element</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <!-- END SNIPPET: generalAttributes -->
 * 
 * <p/>
 * 
 * <!-- START SNIPPET: javascriptRelatedAttributes -->
 * 
 * <table border="1">
 *    <thead>
 *       <tr>
 *          <td>Attribute</td>
 *          <td>Theme</td>
 *          <td>Data Types</td>
 *          <td>Description</td>
 *       </tr>
 *    </thead>
 *    <tbody>
 *       <tr>
 *          <td>onclick</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onclick attribute</td>
 *       </tr>
 *       <tr>
 *          <td>ondbclick</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript ondbclick attribute</td>
 *       </tr>
 *       <tr>
 *          <td>onmousedown</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onmousedown attribute</td>
 *       </tr>
 *       <tr>
 *          <td>onmouseup</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onmouseup attribute</td>
 *       </tr>
 *       <tr>
 *          <td>onmouseover</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onmouseover attribute</td>
 *       </tr>
 *       <tr>
 *          <td>onmouseout</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onmouseout attribute</td>
 *       </tr>
 *       <tr>
 *          <td>onfocus</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onfocus attribute</td>
 *       </tr>
 *       <tr>
 *          <td>onblur</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onblur attribute</td>
 *       </tr>
 *       <tr>
 *          <td>onkeypress</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onkeypress attribute</td>
 *       </tr>
 *       <tr>
 *          <td>onkeyup</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onkeyup attribute</td>
 *       </tr>
 *       <tr>
 *          <td>onkeydown</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onkeydown attribute</td>
 *       </tr>
 *       <tr>
 *          <td>onselect</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onselect attribute</td>
 *       </tr>
 *       <tr>
 *          <td>onchange</td>
 *          <td>simple</td>
 *          <td>String</td>
 *          <td>html javascript onchange attribute</td>
 *       </tr>
 *    </tbody>
 * </table>
 * 
 * <!-- END SNIPPET: javascriptRelatedAttributes -->
 * 
 * <p/>
 * 
 * <!-- START SNIPPET: tooltipattributes -->
 * 
 * <table border="1">
 *  <tr>
 *     <td>Attribute</td>
 *     <td>Data Type</td>
 *     <td>Default</td>
 *     <td>Description</td>
 *  </tr>
 *  <tr>
 *  	<td>tooltip</td>
 *  	<td>String</td>
 *  	<td>none</td>
 *  	<td>Set the tooltip of this particular component</td>
 *  </tr>
 *    <tr>
 *   	<td>tooltipIcon</td>
 *   	<td>String</td>
 *   	<td>/webwork/static/tooltip/tooltip.gif</td>
 *   	<td>The url to the tooltip icon</td>
 *   </tr>
 *   	<td>tooltipAboveMousePointer</td>
 *   	<td>Boolean</td>
 *   	<td>false</td>
 *   	<td>Places the tooltip above the mousepointer. Additionally applied the tooltipOffseY allows to set the vertical distance from the mousepointer.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipBgColor</td>
 *   	<td>String</td>
 *   	<td>#e6ecff</td>
 *   	<td>Background color of the tooltip.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipBgImg</td>
 *   	<td>String</td>
 *   	<td>none</td>
 *   	<td>Background image.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipBorderWidth</td>
 *   	<td>String</td>
 *   	<td>1</td>
 *   	<td>Width of tooltip border.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipBorderColor</td>
 *   	<td>String</td>
 *   	<td>#003399</td>
 *   	<td>Background color of the tooltip</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipDelay</td>
 *   	<td>String</td>
 *   	<td>500</td>
 *   	<td>Tooltip shows up after the specified timeout (miliseconds). A behavior similar to that of OS based tooltips.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipFixCoordinate</td>
 *   	<td>String</td>
 *   	<td>not specified</td>
 *   	<td>Fixes the tooltip to the co-ordinates specified within the square brackets. Useful for example if combined with tooltipSticky attribute, eg. [200, 2400]</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipFontColor</td>
 *   	<td>String</td>
 *   	<td>#000066</td>
 *   	<td>Font color.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipFontFace</td>
 *   	<td>String</td>
 *   	<td>arial,helvetica,sans-serif</td>
 *   	<td>Font face/family eg. verdana,geneva,sans-serif</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipFontSize</td>
 *   	<td>String</td>
 *   	<td>11px</td>
 *   	<td>Font size + unit eg. 30px</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipFontWeight</td>
 *   	<td>String</td>
 *   	<td>normal</td>
 *   	<td>Font weight. either normal or bold</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipLeftOfMousePointer</td>
 *   	<td>Boolean</td>
 *   	<td>false</td>
 *   	<td>Tooltip positioned on the left side of the mousepointer</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipOffsetX</td>
 *   	<td>String</td>
 *   	<td>12</td>
 *   	<td>Horizontal offset from mouse-pointer.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipOffsetY</td>
 *   	<td>String</td>
 *   	<td>15</td>
 *   	<td>Vertical offset from mouse-pointer.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipOpacity</td>
 *   	<td>String</td>
 *   	<td>100</td>
 *   	<td>Transparency of tooltip. Opacity is the opposite of transparency. Value must be a number between 0 (fully transparent) and 100 (opaque, no transparency). Not (yet) supported by Opera.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipPadding</td>
 *   	<td>String</td>
 *   	<td>3</td>
 *   	<td>Inner spacing, ie. the spacing between border and content, for instance text or image(s)</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipShadowColor</td>
 *   	<td>String</td>
 *   	<td>#cccccc</td>
 *   	<td>Creates shadow with the specified color.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipShadowWidth</td>
 *   	<td>String</td>
 *   	<td>5</td>
 *   	<td>Creates shodow with the specified width (offset).</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipStatic</td>
 *   	<td>Boolean</td>
 *   	<td>false</td>
 *   	<td>Like OS-based tooltips, the tooltip doesn't follow the movements of the mouse pointer.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipSticky</td>
 *   	<td>Boolean</td>
 *   	<td>false</td>
 *   	<td>The tooltip stays fixed on its inital position until anohter tooltip is activated, or the user clicks on the document.</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipStayAppearTime</td>
 *   	<td>String</td>
 *   	<td>0</td>
 *   	<td>Specifies a time span in miliseconds after which the tooltip disappears, even if the mousepointer is still on the concerned HTML element, with value <=0 it acts as if no time span is defined</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipTextAlign</td>
 *   	<td>String</td>
 *   	<td>left</td>
 *   	<td>Aligns the text of both the title and the body of the tooltip. Either right, left or justify</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipTitle</td>
 *   	<td>String</td>
 *   	<td>none</td>
 *   	<td>title</td>
 *   </tr>
 *   <tr>
 *   	<td>tooltipTitleColor</td>
 *   	<td>String</td>
 *   	<td>#ffffff</td>
 *   	<td>Color of the title text</td>
 *   </tr>   
 *   <tr>
 *    	<td>tooltipWidth</td>
 *    	<td>String</td>
 *    	<td>300</td>
 *    	<td>Width of tooltip</td>
 *    </tr>
 * </table>
 * 
 * <!-- END SNIPPET: tooltipattributes -->
 * 
 * 
 * <!-- START SNIPPET: tooltipdescription -->
 * 
 * Every Form UI component (in xhtml / css_xhtml or any others that extends of them) could
 * have tooltip assigned to a them. The Form component's tooltip related attribute once 
 * defined will be applicable to all form UI component that is created under it unless
 * explicitly overriden by having the Form UI component itself defined that tooltip attribute. 
 * 
 * <p/>
 * 
 * In Example 1, the textfield will inherit the tooltipAboveMousePointer attribte from 
 * its containing form. In other words, although it doesn't defined a tooltipAboveMousePointer
 * attribute, it will have that attribute defined as true inherited from its containing form.
 * 
 * <p/>
 * 
 * In Example 2, the the textfield will inherite both the tooltipAboveMousePointer and 
 * tooltipLeftOfMousePointer attribute from its containing form but tooltipLeftOfMousePointer
 * attribute is overriden at the textfield itself. Hence, the textfield actually will 
 * have tooltipAboveMousePointer defined as true, inherited from its containing form and 
 * tooltipLeftOfMousePointer defined as false, due to overriden at the textfield itself.
 * 
 * <!-- END SNIPPET: tooltipdescription -->
 * 
 * 
 * <pre>
 * <!-- START SNIPPET: tooltipexample -->
 * 
 * &lt;!-- Example 1: --&gt;
 * &lt;ww:form tooltipAboveMousePointer="true" .... &gt;
 *   ....
 *     &lt;ww:textfield label="Customer Name" tooltip="Enter the customer name" .... /&gt;
 *   ....
 * &lt;/ww:form&gt;
 * 
 * &lt;!-- Example 2: --&gt;
 * &lt;ww:form tooltipAboveMousePointer="true" tooltipLeftOfMousePointer="true" ...&gt;
 *   ....
 *     &lt;ww:textfield label="Address" tooltip="Enter your address" tooltipLeftOfMousePointer="false" /&gt;
 *   ....
 * &lt;/ww:form&gt;
 * 
 * 
 * 
 * <!-- END SNIPPET: tooltipexample -->
 * </pre>
 * 
 * 
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @author Rainer Hermanns
 * @author tm_jee
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
        this.templateSuffix = ContextUtil.getTemplateSuffix(stack.getContext());
    }

    // The templateSuffic to use, overrides the default one if not null.
    protected String templateSuffix;

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
    protected String title;

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
    
    // javascript tooltip attribute
    protected String tooltip;
    protected String tooltipIcon;
    protected String tooltipAboveMousePointer;
    protected String tooltipBgColor;
    protected String tooltipBgImg;
    protected String tooltipBorderWidth;
    protected String tooltipBorderColor;
    protected String tooltipDelay;
    protected String tooltipFixCoordinate;
    protected String tooltipFontColor;
    protected String tooltipFontFace;
    protected String tooltipFontSize;
    protected String tooltipFontWeight;
    protected String tooltipLeftOfMousePointer;
    protected String tooltipOffsetX;
    protected String tooltipOffsetY;
    protected String tooltipOpacity;
    protected String tooltipPadding;
    protected String tooltipShadowColor;
    protected String tooltipShadowWidth;
    protected String tooltipStatic;
    protected String tooltipSticky;
    protected String tooltipStayAppearTime;
    protected String tooltipTextAlign;
    protected String tooltipTitle;
    protected String tooltipTitleColor;
    protected String tooltipWidth;
    

    public boolean end(Writer writer, String body) {
        evaluateParams();
        try {
            super.end(writer, body, false);
            mergeTemplate(writer, buildTemplateName(template, getDefaultTemplate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
        	popComponentStack();
        }

        return false;
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
        final TemplateEngine engine = TemplateEngineManager.getTemplateEngine(template, templateSuffix);
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
            templateDir = Configuration.getString(WebWorkConstants.WEBWORK_UI_TEMPLATEDIR);
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

        if ( theme == null || theme.equals("") ) {
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
            theme = Configuration.getString(WebWorkConstants.WEBWORK_UI_THEME);
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

        if (title != null) {
            addParameter("title", findString(title));
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
                        String expr = name;
                        if (altSyntax()) {
                            expr = "%{" + expr + "}";
                        }

                        addParameter("nameValue", findValue(expr, valueClazz));
                    }
                } else {
                    if (value != null) {
                        addParameter("nameValue", findValue(value));
                    } else if (name != null) {
                        addParameter("nameValue", findValue(name));
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
            addParameter("id", form.getParameters().get("id") + "_" +escape(name));
        }

        if (form != null ) {
            addParameter("form", form.getParameters());

            if ( name != null ) {
                List tags = (List) form.getParameters().get("tagNames");
                tags.add(name);
            }
        }
        
        
        // tooltips
        if (tooltip != null) {
        	addParameter("tooltip", findString(tooltip));
        	
        	// it only makes sense to have tooltipIcon if a tooltip attribute is 
        	// actually specified
        	if (tooltipIcon != null) {
        		addParameter("tooltipIcon", findString(tooltipIcon));
        	}
        	
        	if (form != null) { // inform the containing form that we need tooltip javascript included
        		form.addParameter("hasTooltip", Boolean.TRUE);
        		
        		// javascript tooltip functionality only works when we have an ancestor form and can 
        		// include the appropriate javascript, so it makes sense to have the logic here, only if 
        		// an ancestor form exists
        		if (tooltipIcon != null) {
        			addParameter("tooltipIcon", findString(tooltipIcon));
        		}
        		else if (form.tooltipIcon != null) {
        			addParameter("tooltipIcon", findString(form.tooltipIcon));
        		}
        		
        	    if (tooltipAboveMousePointer != null) {
        	    	addParameter("tooltipAboveMousePointer", ((Boolean)findValue(tooltipAboveMousePointer, Boolean.class)).booleanValue() ? "true" : "false");
        	    }
        	    else if (form.tooltipAboveMousePointer != null) {
        	    	addParameter("tooltipAboveMousePointer", ((Boolean)findValue(form.tooltipAboveMousePointer, Boolean.class)).booleanValue() ? "true" : "false");
        	    }
        	    
        	    if (tooltipBgColor != null) {
        	    	addParameter("tooltipBgColor", findString(tooltipBgColor));
        	    }
        	    else if (form.tooltipBgColor != null) {
        	    	addParameter("tooltipBgColor", findString(form.tooltipBgColor));
        	    }
        	    
        	    if (tooltipBgImg != null) {
        	    	addParameter("tooltipBgImg", findString(tooltipBgImg));
        	    }
        	    else if (form.tooltipBgImg != null) {
        	    	addParameter("tooltipBgImg", findString(form.tooltipBgImg));
        	    }
        	    
        	    if (tooltipBorderWidth != null) {
        	    	addParameter("tooltipBorderWidth", findString(tooltipBorderWidth));
        	    }
        	    else if (form.tooltipBorderWidth != null) {
        	    	addParameter("tooltipBorderWidth", findString(form.tooltipBorderWidth));
        	    }
        	    
        	    if (tooltipBorderColor != null) {
        	    	addParameter("tooltipBorderColor", findString(tooltipBorderColor));
        	    }
        	    else if (form.tooltipBorderColor != null) {
        	    	addParameter("tooltipBorderColor", findString(form.tooltipBorderColor));
        	    }
        	    
        	    if (tooltipDelay != null) {
        	    	addParameter("tooltipDelay", findString(tooltipDelay));
        	    }
        	    else if (form.tooltipDelay != null) {
        	    	addParameter("tooltipDelay", findString(form.tooltipDelay));
        	    }
        	    
        	    if (tooltipFixCoordinate != null) {
        	    	addParameter("tooltipFixCoordinate", findString(tooltipFixCoordinate));
        	    }
        	    else if (form.tooltipFixCoordinate != null) {
        	    	addParameter("tooltipFixCoordinate", findString(form.tooltipFixCoordinate));
        	    }
        	    
        	    if (tooltipFontColor != null) {
        	    	addParameter("tooltipFontColor", findString(tooltipFontColor));
        	    }
        	    else if (form.tooltipFontColor != null) {
        	    	addParameter("tooltipFontColor", findString(form.tooltipFontColor));
        	    }
        	    
        	    if (tooltipFontFace != null) {
        	    	addParameter("tooltipFontFace", findString(tooltipFontFace));
        	    }
        	    else if (form.tooltipFontFace != null) {
        	    	addParameter("tooltipFontFace", findString(form.tooltipFontFace));
        	    }
        	    
        	    if (tooltipFontSize != null) {
        	    	addParameter("tooltipFontSize", findString(tooltipFontSize));
        	    }
        	    else if (form.tooltipFontSize != null) {
        	    	addParameter("tooltipFontSize", findString(form.tooltipFontSize));
        	    }
        	    
        	    if (tooltipFontWeight != null) {
        	    	addParameter("tooltipFontWeight", findString(tooltipFontWeight));
        	    }
        	    else if (form.tooltipFontWeight != null) {
        	    	addParameter("tooltipFontWeight", findString(form.tooltipFontWeight));
        	    }
        	    
        	    if (tooltipLeftOfMousePointer != null) {
        	    	addParameter("tooltipLeftOfMousePointer", ((Boolean)findValue(tooltipLeftOfMousePointer, Boolean.class)).booleanValue() ? "true" : "false");
        	    }
        	    else if (form.tooltipLeftOfMousePointer != null) {
        	    	addParameter("tooltipLeftOfMousePointer", ((Boolean)findValue(form.tooltipLeftOfMousePointer, Boolean.class)).booleanValue() ? "true" : "false");
        	    }
        	    
        	    if (tooltipOffsetX != null) {
        	    	addParameter("tooltipOffsetX", findString(tooltipOffsetX));
        	    }
        	    else if (form.tooltipOffsetX != null) {
        	    	addParameter("tooltipOffsetX", findString(form.tooltipOffsetX));
        	    }
        	    
        	    if (tooltipOffsetY != null) {
        	    	addParameter("tooltipOffsetY", findString(tooltipOffsetY));
        	    }
        	    else if (form.tooltipOffsetY != null) {
        	    	addParameter("tooltipOffsetY", findString(form.tooltipOffsetY));
        	    }
        	    
        	    if (tooltipOpacity != null) {
        	    	addParameter("tooltipOpacity", findString(tooltipOpacity));
        	    }
        	    else if (form.tooltipOpacity != null) {
        	    	addParameter("tooltipOpacity", findString(form.tooltipOpacity));
        	    }
        	    
        	    if (tooltipPadding != null) {
        	    	addParameter("tooltipPadding", findString(tooltipPadding));
        	    }
        	    else if (form.tooltipPadding != null) {
        	    	addParameter("tooltipPadding", findString(form.tooltipPadding));
        	    }
        	    
        	    if (tooltipShadowColor != null) {
        	    	addParameter("tooltipShadowColor", findString(tooltipShadowColor));
        	    }
        	    else if (form.tooltipShadowColor != null) {
        	    	addParameter("tooltipShadowColor", findString(form.tooltipShadowColor));
        	    }
        	    
        	    if (tooltipShadowWidth != null) {
        	    	addParameter("tooltipShadowWidth", findString(tooltipShadowWidth));
        	    }
        	    else if (form.tooltipShadowWidth != null) {
        	    	addParameter("tooltipShadowWidth", findString(form.tooltipShadowWidth));
        	    }
        	    
        	    if (tooltipStatic != null) {
        	    	addParameter("tooltipStatic", ((Boolean)findValue(tooltipStatic, Boolean.class)).booleanValue() ? "true" : "false");
        	    }
        	    else if (form.tooltipStatic != null) {
        	    	addParameter("tooltipStatic", ((Boolean)findValue(form.tooltipStatic, Boolean.class)).booleanValue() ? "true" : "false");
        	    }
        	    
        	    if (tooltipSticky != null) {
        	    	addParameter("tooltipSticky", ((Boolean)findValue(tooltipSticky, Boolean.class)).booleanValue() ? "true" : "false");
        	    }
        	    else if (form.tooltipSticky != null) {
        	    	addParameter("tooltipSticky", ((Boolean)findValue(form.tooltipSticky, Boolean.class)).booleanValue() ? "true" : "false");
        	    }
        	    
        	    if (tooltipStayAppearTime != null) {
        	    	addParameter("tooltipStayAppearTime", findString(tooltipStayAppearTime));
        	    }
        	    else if (form.tooltipStayAppearTime != null) {
        	    	addParameter("tooltipStayAppearTime", findString(form.tooltipStayAppearTime));
        	    }
        	    
        	    if (tooltipTextAlign != null) {
        	    	addParameter("tooltipTextAlign", findString(tooltipTextAlign));
        	    }
        	    else if (form.tooltipTextAlign != null) {
        	    	addParameter("tooltipTextAlign", findString(form.tooltipTextAlign));
        	    }
        	    
        	    if (tooltipTitle != null) {
        	    	addParameter("tooltipTitle", findString(tooltipTitle));
        	    }
        	    else if (form.tooltipTitle != null) {
        	    	addParameter("tooltipTitle", findString(form.tooltipTitle));
        	    }
        	    
        	    if (tooltipTitleColor != null) {
        	    	addParameter("tooltipTitleColor", findString(tooltipTitleColor));
        	    }
        	    else if (form.tooltipTitleColor != null) {
        	    	addParameter("tooltipTitleColor", findString(form.tooltipTitleColor));
        	    }
        	    
        	    if (tooltipWidth != null) {
        	    	addParameter("tooltipWidth", findString(tooltipWidth));
        	    }
        	    else if (form.tooltipWidth != null) {
        	    	addParameter("tooltipWidth", findString(form.tooltipWidth));
        	    }
        		
        	}
        	else {
        		LOG.warn("No ancestor Form not found, javascript based tooltip will not work, however standard HTML tooltip using alt and title attribute will still work ");
        	}
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

    public void addFormParameter(String key, Object value) {
        Form form = (Form) findAncestor(Form.class);
        if (form != null) {
            form.addParameter(key, value);
        }
    }

    /**
     * @ww.tagattribute required="false"
     * description="The theme (other than default) to use for renedring the element"
      */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTemplate() {
        return template;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The template (other than default) to use for renedring the element"
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The css class to use for element"
     */
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The css style definitions for element ro use"
     */
    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html title attribute on rendered html element"
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html disabled attribute on rendered html element"
     */
    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Label expression used for rendering a element specific label"
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @ww.tagattribute required="false" default="left"
     * description="deprecated."
     * @deprecated please use {@link #setLabelposition(String)} instead
     */
    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    /**
     * @ww.tagattribute required="false"
     * description="define label position of form element (top/left)"
     */
    public void setLabelposition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The name to set for element"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @ww.tagattribute  required="false" type="Boolean" default="false"
     * description="If set to true, the rendered element will indicate that input is required"
     */
    public void setRequired(String required) {
        this.required = required;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html tabindex attribute on rendered html element"
     */
    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Preset the value of input element."
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onclick attribute on rendered html element"
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html ondblclick attribute on rendered html element"
     */
    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onmousedown attribute on rendered html element"
     */
    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onmouseup attribute on rendered html element"
     */
    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onmouseover attribute on rendered html element"
     */
    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onmousemove attribute on rendered html element"
     */
    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onmouseout attribute on rendered html element"
     */
    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onfocus attribute on rendered html element"
     */
    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onblur attribute on rendered html element"
     */
    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onkeypress attribute on rendered html element"
     */
    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onkeydown attribute on rendered html element"
     */
    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onkeyup attribute on rendered html element"
     */
    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onselect attribute on rendered html element"
     */
    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the html onchange attribute on rendered html element"
     */
    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }
    
    /**
     * @ww.tagattribute required="false" type="String" default=""
     * description="Set the tooltip of this particular component"
     */
    public void setTooltip(String tooltip) {
    	this.tooltip = tooltip;
    }

    /**
     * @ww.tagattribute required="false" type="String" default="/webwork/static/tooltip/tooltip.gif"
     * description="The url to the tooltip icon"
     */
    public void setTooltipIcon(String icon) {
    	this.tooltipIcon = icon;
    }
    
    
    /**
     * @ww.tagattribute required="false" type="boolean" default="false"
     * description="Places the tooltip above the mousepointer. Additionally applied the tooltipOffseY allows to set the vertical distance from the mousepointer."
     */
	public void setTooltipAboveMousePointer(String tooltipAboveMousePointer) {
		this.tooltipAboveMousePointer = tooltipAboveMousePointer;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="#e6ecff"
     * description="Background color of the tooltip."
	 */
	public void setTooltipBgColor(String tooltipBgColor) {
		this.tooltipBgColor = tooltipBgColor;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="none"
     * description="Background image."
     */
	public void setTooltipBgImg(String tooltipBgImg) {
		this.tooltipBgImg = tooltipBgImg;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="#003399"
     * description="Background color of the tooltip"
	 */
	public void setTooltipBorderColor(String tooltipBorderColor) {
		this.tooltipBorderColor = tooltipBorderColor;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="1"
     * description="Width of tooltip border."
	 */
	public void setTooltipBorderWidth(String tooltipBorderWidth) {
		this.tooltipBorderWidth = tooltipBorderWidth;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="500"
     * description="Tooltip shows up after the specified timeout (miliseconds). A behavior similar to that of OS based tooltips."
	 */
	public void setTooltipDelay(String tooltipDelay) {
		this.tooltipDelay = tooltipDelay;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="none"
     * description="Fixes the tooltip to the co-ordinates specified within the square brackets. Useful for example if combined with tooltipSticky attribute, eg. [200, 2400]"
	 */
	public void setTooltipFixCoordinate(String tooltipFixCoordinate) {
		this.tooltipFixCoordinate = tooltipFixCoordinate;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="#000066"
     * description="Font color."
	 */
	public void setTooltipFontColor(String tooltipFontColor) {
		this.tooltipFontColor = tooltipFontColor;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="arial,helvetica,sans-serif"
     * description="Font face/family eg. verdana,geneva,sans-serif"
	 */
	public void setTooltipFontFace(String tooltipFontFace) {
		this.tooltipFontFace = tooltipFontFace;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="11px"
     * description="Font size + unit eg. 30px"
	 */
	public void setTooltipFontSize(String tooltipFontSize) {
		this.tooltipFontSize = tooltipFontSize;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="normal"
     * description="Font weight. either normal or bold"
	 */
	public void setTooltipFontWeight(String tooltipFontWeight) {
		this.tooltipFontWeight = tooltipFontWeight;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="false"
     * description="Tooltip positioned on the left side of the mousepointer"
	 */
	public void setTooltipLeftOfMousePointer(String tooltipLeftOfMousePointer) {
		this.tooltipLeftOfMousePointer = tooltipLeftOfMousePointer;
	}

	/**12
	 * @ww.tagattribute required="false" type="String" default="12"
     * description="Horizontal offset from mouse-pointer."
	 */
	public void setTooltipOffsetX(String tooltipOffsetX) {
		this.tooltipOffsetX = tooltipOffsetX;
	}

	/**
	 * @ww.tagattribute required="false" default="15"
     * description="Vertical offset from mouse-pointer." 
	 */
	public void setTooltipOffsetY(String tooltipOffsetY) {
		this.tooltipOffsetY = tooltipOffsetY;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="100"
     * description="Transparency of tooltip. Opacity is the opposite of transparency. Value must be a number between 0 (fully transparent) and 100 (opaque, no transparency). Not (yet) supported by Opera."
	 */
	public void setTooltipOpacity(String tooltipOpacity) {
		this.tooltipOpacity = tooltipOpacity;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="3"
     * description="Inner spacing, ie. the spacing between border and content, for instance text or image(s)"
	 */
	public void setTooltipPadding(String tooltipPadding) {
		this.tooltipPadding = tooltipPadding;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="#cccccc"
     * description="Creates shadow with the specified color."
	 */
	public void setTooltipShadowColor(String tooltipShadowColor) {
		this.tooltipShadowColor = tooltipShadowColor;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="5"
     * description="Creates shodow with the specified width (offset)."
	 */
	public void setTooltipShadowWidth(String tooltipShadowWidth) {
		this.tooltipShadowWidth = tooltipShadowWidth;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="false"
     * description="Like OS-based tooltips, the tooltip doesn't follow the movements of the mouse pointer."
	 */
	public void setTooltipStatic(String tooltipStatic) {
		this.tooltipStatic = tooltipStatic;
	}
	
	/**
	 * @ww.tagattribute required="false" type="String" default="0"
     * description="Specifies a time span in miliseconds after which the tooltip disappears, even if the mousepointer is still on the concerned HTML element, with value <=0 it acts as if no time span is defined"
	 */
	public void setTooltipStayAppearTime(String tooltipStayAppearTime) {
		this.tooltipStayAppearTime = tooltipStayAppearTime;
	}

	/**
	 * @ww.tagattribute required="false" type="boolean" default="false"
     * description="The tooltip stays fixed on its inital position until anohter tooltip is activated, or the user clicks on the document."
	 */
	public void setTooltipSticky(String tooltipSticky) {
		this.tooltipSticky = tooltipSticky;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="left"
     * description="Aligns the text of both the title and the body of the tooltip. Either right, left or justify"
	 */
	public void setTooltipTextAlign(String tooltipTextAlign) {
		this.tooltipTextAlign = tooltipTextAlign;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="none"
     * description="Title"
	 */
	public void setTooltipTitle(String tooltipTitle) {
		this.tooltipTitle = tooltipTitle;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="#ffffff"
     * description="Color of the title text"
	 */
	public void setTooltipTitleColor(String tooltipTitleColor) {
		this.tooltipTitleColor = tooltipTitleColor;
	}

	/**
	 * @ww.tagattribute required="false" type="String" default="300"
     * description="Width of tooltip"
	 */
	public void setTooltipWidth(String tooltipWidth) {
		this.tooltipWidth = tooltipWidth;
	}
}
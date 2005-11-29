package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * Renders an HTML input element of type checkbox, populated by the specified property from the OgnlValueStack.
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * JSP:
 * &lt;ww:checkbox label="'checkbox test'" name="'checkboxField1'" value="aBoolean" fieldValue="'true'"/&gt;
 *
 * Velocity:
 * #tag( Checkbox "label='checkbox test'" "name='checkboxField1'" "value=aBoolean" "fieldValue='true'" )
 *
 * Resulting HTML (simple template, aBoolean == true):
 * &lt;input type="checkbox" name="checkboxField1" value="true" checked="checked" /&gt;
 *
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @ww.tag name="checkbox" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.CheckboxTag"
 * description="Render a checkbox input field"
  */
public class Checkbox extends UIBean {
    final public static String TEMPLATE = "checkbox";

    protected String fieldValue;

    public Checkbox(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    protected void evaluateExtraParams() {
        if (fieldValue != null) {
            addParameter("fieldValue", findString(fieldValue));
        }
    }

    protected Class getValueClassType() {
        return Boolean.class; // for checkboxes, everything needs to end up as a Boolean
    }

    /**
     * @ww.tagattribute required="true"
     * description="The actual HTML value attribute of the checkbox"
      */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

}

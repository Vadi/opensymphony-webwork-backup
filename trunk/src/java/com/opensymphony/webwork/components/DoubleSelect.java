package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * Renders two HTML select elements to represent eg. "available|selected" type input.
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:doubleselect label="'doubleselect test'" ... /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @ww.tag name="doubleselect" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.DoubleSelectTag"
 * description="Render a double select element"
 */
public class DoubleSelect extends DoubleListUIBean {
    final public static String TEMPLATE = "doubleselect";

    protected String emptyOption;
    protected String headerKey;
    protected String headerValue;
    protected String multiple;
    protected String size;

    public DoubleSelect(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (emptyOption != null) {
            addParameter("emptyOption", findValue(emptyOption, Boolean.class));
        }

        if (multiple != null) {
            addParameter("multiple", findValue(multiple, Boolean.class));
        }

        if (size != null) {
            addParameter("size", findString(size));
        }

        if ((headerKey != null) && (headerValue != null)) {
            addParameter("headerKey", findString(headerKey));
            addParameter("headerValue", findString(headerValue));
        }

        // force the onchange parameter
        addParameter("onchange", getParameters().get("name") + "Redirect(this.options.selectedIndex)");
    }

    /**
     * @ww.tagattribute required="false"
     * description="Whether or not to add an empty (--) option after the header option"
     */
    public void setEmptyOption(String emptyOption) {
        this.emptyOption = emptyOption;
    }

    /**
     * Cannot be empty! "'-1'" and "''" is correct, "" is bad.
     * @ww.tagattribute required="false"
     * description="Key for first item in list"
     */
    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Value expression for first item in list"
     */
    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Creates a multiple select. The tag will pre-select multiple values if the values are passed as an Array (of appropriate types) via the value attribute. Passing a Collection may work too? Haven't tested this."
     */
    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    /**
     * @ww.tagattribute required="false"
     * description=" Size of the element box (# of elements to show)"
     */
    public void setSize(String size) {
        this.size = size;
    }
}

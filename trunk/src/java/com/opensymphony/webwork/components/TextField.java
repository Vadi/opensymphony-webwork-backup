package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * Render an HTML input field of type text</p>
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 * <p/>
 * <!-- START SNIPPET: exampledescription -->
 * In this example, a text control is rendered. The label is retrieved from a ResourceBundle by calling
 * ActionSupport's getText() method.<p/>
 * <!-- END SNIPPET: exampledescription -->
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:textfield label="text('user_name')" name="'user'" /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @jsp.tag name="textfield" body-content="JSP" tag-class="com.opensymphony.webwork.views.jsp.ui.TextFieldTag"
 * description="Render an HTML input field of type text"
 */
public class TextField extends UIBean {
    /**
     * The name of the default template for the TextFieldTag
     */
    final public static String TEMPLATE = "text";


    protected String maxLength;
    protected String readonly;
    protected String size;

    public TextField(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (size != null) {
            addParameter("size", findString(size));
        }

        if (maxLength != null) {
            addParameter("maxlength", findString(maxLength));
        }

        if (readonly != null) {
            addParameter("readonly", findValue(readonly, Boolean.class));
        }
    }

    /**
     * @jsp.attribute required="false" rtexprvalue="true"
     * description="HTML maxLength attribute"
     */
    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="HTML readonly attribute"
     */
    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="HTML size attribute"
     */
    public void setSize(String size) {
        this.size = size;
    }
}

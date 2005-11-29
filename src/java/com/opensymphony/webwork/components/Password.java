package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * Render an HTML input tag of type password.</p>
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 * <p/>
 * <!-- START SNIPPET: exampledescription -->
 * In this example, a password control is displayed. For the label, we are calling ActionSupport's getText() to
 * retrieve password label from a resource bundle.<p/>
 * <!-- END SNIPPET: exampledescription -->
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:password label="text('password')" name="'password'" size="10" maxlength="15" /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @ww.tag name="password" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.PasswordTag"
 * description="Render an HTML input tag of type password"
 */
public class Password extends TextField {
    final public static String TEMPLATE = "password";

    protected String showPassword;

    public Password(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (showPassword != null) {
            addParameter("showPassword", findValue(showPassword, Boolean.class));
        }
    }

    /**
     * @ww.tagattribute required="false"
     * description="Whether to show input"
     */
    public void setShowPassword(String showPassword) {
        this.showPassword = showPassword;
    }

    /**
     * @deprecated use {@link #setShowPassword(String)}
     * @ww.tagattribute required="false" rtexprvalue="true"
     * description="Deprecated. Use showPassword instead."
     */
    public void setShow(String showPassword) {
        this.showPassword = showPassword;
    }
}

package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * Renders an HTML LABEL that will allow you to output label:name combination that has the same format treatment as
 * the rest of your UI controls.</p>
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 * <p/>
 * <!-- START SNIPPET: exampledescription -->
 * In this example, a label is rendered. The label is retrieved from a ResourceBundle by calling ActionSupport's
 * getText() method giving you an output of User name: a label.<p/>
 * <!-- END SNIPPET: exampledescription -->
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:label label="text('user_name')" name="'a label'" /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @jsp.tag name="label" body-content="JSP"
 * description="Render a label that displays read-only information"
  */
public class Label extends UIBean {
    final public static String TEMPLATE = "label";

    protected String forAttr;

    public Label(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (forAttr != null) {
            addParameter("for", findString(forAttr));
        }

        // try value first, then name (this overrides the default behavior in the superclass)
        if (value != null) {
            addParameter("nameValue", findString(value));
        } else if (name != null) {
            String expr = name;
            if (altSyntax()) {
                expr = "%{" + expr + "}";
            }

            addParameter("nameValue", findString(expr));
        }
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="HTML for attribute"
     */
    public void setFor(String forAttr) {
        this.forAttr = forAttr;
    }
}

package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Creates a series of checkboxes from a list. Setup is like &lt;ww:select /&gt; or &lt;ww:radio /&gt;, but creates checkbox tags.
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:checkboxlist name="foo" list="bar"/&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @ww.tag name="checkboxlist" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.CheckboxListTag"
 * description="Render a list of checkboxes"
  */
public class CheckboxList extends ListUIBean {
    final public static String TEMPLATE = "checkboxlist";

    public CheckboxList(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

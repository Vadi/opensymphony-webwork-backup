package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * Renders datepicker element.
 *
 * Note the this element only works within &lt;ww:form&gt; tags, not plain HTML form.
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:datepicker name="order.date" /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @jsp.tag name="datepicker" body-content="JSP" tag-class="com.opensymphony.webwork.views.jsp.ui.DatePickerTag"
 * description="Render datepicker"
  */
public class DatePicker extends TextField {
    final public static String TEMPLATE = "datepicker";

    public DatePicker(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

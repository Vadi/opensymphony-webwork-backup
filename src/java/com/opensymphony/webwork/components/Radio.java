package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * Render a radio button input field.</p>
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 * <p/>
 * <!-- START SNIPPET: exampledescription -->
 * In this example, a radio control is displayed with a list of genders. The gender list is built from attribute
 * id=genders. WW calls getGenders() which will return a Map. For examples using listKey and listValue attributes,
 * see the section select tag.<p/>
 * <!-- END SNIPPET: exampledescription -->
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:action name="'GenderMap'" id="genders"/&gt;
 * &lt;ww:radio label="'Gender'" name="'male'" list="#genders.genders"/&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @jsp.tag name="radio" body-content="JSP"
 * description="Renders a radio button input field"
 */
public class Radio extends ListUIBean {
    final public static String TEMPLATE = "radiomap";

    public Radio(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

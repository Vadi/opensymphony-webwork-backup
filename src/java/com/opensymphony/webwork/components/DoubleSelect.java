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
 * &lt;ww:doubleselect label="doubleselect test1" name="menu" list="{'fruit','other'}" doubleName="dishes" doubleList="menu == 'fruit' ? {'apple', 'orange'} : {'monkey', 'chicken'}" /&gt;
 * &lt;ww:doubleselect label="doubleselect test2" name="menu" list="#{'fruit':'Nice Fruits', 'other':'Other Dishes'}" doubleName="dishes" doubleList="menu == 'fruit' ? {'apple', 'orange'} : {'monkey', 'chicken'}" /&gt;
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


    public DoubleSelect(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        // force the onchange parameter
        addParameter("onchange", getParameters().get("name") + "Redirect(this.options.selectedIndex)");
    }
}

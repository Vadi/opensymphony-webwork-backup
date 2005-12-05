/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.util.TextUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import java.io.IOException;


/**
 * <!-- START SNIPPET: javadoc -->
 * <b>NOTE: JSP-TAG</b>
 * 
 * <p>Used to get the property of a <i>value</i>, which will default to the top of
 * the stack if none is specified.</p>
 * <!-- END SNIPPET: javadoc -->
 *
 * <!-- START SNIPPET: params -->
 * <ul>
 *      <li>default (String) - The default value to be used if <u>value</u> attribute is null</li>
 *      <li>escape (Boolean) - Escape HTML. Default to true</li>
 *      <li>value (Object) - value to be displayed</li>
 * </ul>
 * <!-- END SNIPPET: params -->
 *
 * <!-- START SNIPPET: example -->
 * <ww:push value="myBean">
 *     <!-- Example 1: -->
 *     <ww:property value="myBeanProperty" />
 *
 *     <!-- Example 2: -->
 *     <ww:property value="myBeanProperty" default="a default value" />
 * </ww:push>
 * <!-- END SNIPPET: example -->
 *
 * <pre>
 * <!-- START SNIPPET: exampledescription -->
 * Example 1 prints the result of myBean's getMyBeanProperty() method.
 * Example 2 prints the result of myBean's getMyBeanProperty() method and if it is null, print 'a default value' instead.
 * <!-- END SNIPPET: exampledescription -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Cameron Braid
 * @author Mathias Bogaert
 * @author tm_jee
 * @author Rene Gielen
 * @version $Revision$
 *
 * @ww.tag name="property" tld-body-content="empty"
 * description="Print out expression which evaluates against the stack"
 */
public class PropertyTag extends WebWorkBodyTagSupport {
	
	private static final long serialVersionUID = -505263309324809212L;

	private static final Log log = LogFactory.getLog(PropertyTag.class);

    private String defaultValue;
    private String value;
    private boolean escape = true;

    /**
     * @ww.tagattribute required="false" type="String"
     * description="The default value to be used if <u>value</u> attribute is null"
     */
    public void setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @ww.tagattribute required="false" type="Boolean" default="true"
     * description="Whether to escape HTML"
     */
    public void setEscape(boolean escape) {
        this.escape = escape;
    }

    /**
     * @ww.tagattribute required="false" type="Object" default="&lt;top of stack&gt;"
     * description="value to be displayed"
     */
    public void setValue(String value) {
        this.value = value;
    }

    public int doStartTag() throws JspException {
        try {
            Object actualValue = null;

            if (value == null) {
                value = "top";
            }
            else if (altSyntax()) {
                // the same logic as with findValue(String)
                // if value start with %{ and end with }, just cut it off!
                if (value.startsWith("%{") && value.endsWith("}")) {
                    value = value.substring(2, value.length() - 1);
                }
            }

            // exception: don't call findString(), since we don't want the
            //            expression parsed in this one case. it really
            //            doesn't make sense, in fact.
            actualValue = getStack().findValue(value, String.class);

            if (actualValue != null) {
                pageContext.getOut().print(prepare(actualValue));
            } else if (defaultValue != null) {
                pageContext.getOut().print(prepare(defaultValue));
            }
        } catch (IOException e) {
            log.info("Could not print out value '" + value + "': " + e.getMessage());
        }

        return SKIP_BODY;
    }

    private Object prepare(Object value) {
        if (escape) {
            return TextUtils.htmlEncode(value.toString());
        } else {
            return value;
        }
    }
}

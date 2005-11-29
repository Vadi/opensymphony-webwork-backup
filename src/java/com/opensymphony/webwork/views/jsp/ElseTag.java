/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import javax.servlet.jsp.JspException;


/**
 * <!-- START SNIPPET: javadoc -->
 * Perform basic condition flow. 'If' tag could be used by itself or
 * with 'Else If' Tag and/or single/multiple 'Else' Tag.
 * <!-- END SNIPPET: javadoc -->
 *
 *
 * <!-- START SNIPPET: params -->
 * no params
 * <!-- END SNIPPET: params -->
 *
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 *  &lt;ww:if test="%{false}"&gt;
 *	    &lt;div&gt;Will Not Be Executed&lt;/div&gt;
 *  &lt;/ww:if&gt;
 * 	&lt;ww:elseif test="%{true}"&gt;
 *	    &lt;div&gt;Will Be Executed&lt;/div&gt;
 *  &lt;/ww:elseif&gt;
 *  &lt;ww:else&gt;
 *	    &lt;div&gt;Will Not Be Executed&lt;/div&gt;
 *  &lt;/ww:else&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Rick Salsa (rsal@mb.sympatico.ca)
 * @author tmjee
 *
 * @ww.tag name="else" bodycontent="JSP"
 * description="Else tag"
 */
public class ElseTag extends WebWorkBodyTagSupport {
    public int doStartTag() throws JspException {
        Boolean ifResult = (Boolean) pageContext.getAttribute(IfTag.ANSWER);

        pageContext.removeAttribute(IfTag.ANSWER);

        if ((ifResult == null) || (ifResult.booleanValue() == true)) {
            return SKIP_BODY;
        } else {
            return EVAL_BODY_INCLUDE;
        }
    }
}

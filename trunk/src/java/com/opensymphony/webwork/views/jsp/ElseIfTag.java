/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import javax.servlet.jsp.JspException;


/**
 * <!-- START SNIPPET: javadoc -->
 * <b>NOTE: JSP-TAG</b>
 * 
 * <p>Perform basic condition flow. 'If' tag could be used by itself or
 * with 'Else If' Tag and/or single/multiple 'Else' Tag.</p>
 * <!-- END SNIPPET: javadoc -->
 *
 *
 * <!-- START SNIPPET: params -->
 * <ul>
 * 		<li>test* (Boolean) - Logic to determined if body of tag is to be displayed</li>
 * </ul>
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
 * @ww.tag name="elseif" tld-body-content="JSP"
 * description="Elseif tag"
 */
public class ElseIfTag extends WebWorkBodyTagSupport {

    protected Boolean answer;
    protected String test;

    /**
     * @ww.tagattribute required="true" type="Boolean"
     * description="Expression to determine if body of tag is to be displayed"
     */
    public void setTest(String test) {
        this.test = test;
    }

    public int doEndTag() throws JspException {
        if (answer == null) {
            answer = Boolean.FALSE;
        }

        if (answer.booleanValue()) {
            pageContext.setAttribute(IfTag.ANSWER, answer);
        }

        return SKIP_BODY;
    }

    public int doStartTag() throws JspException {
        Boolean ifResult = (Boolean) pageContext.getAttribute(IfTag.ANSWER);

        if ((ifResult == null) || (ifResult.booleanValue())) {
            return SKIP_BODY;
        }

        //make the comparision
        answer = (Boolean) findValue(test, Boolean.class);

        if (answer != null && answer.booleanValue()) {
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }
}

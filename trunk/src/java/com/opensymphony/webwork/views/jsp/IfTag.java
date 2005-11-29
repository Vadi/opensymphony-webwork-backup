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
 * @author $Author$
 * @author tmjee
 *
 * @see com.opensymphony.webwork.views.jsp.ElseTag
 * @see com.opensymphony.webwork.views.jsp.ElseIfTag
 *
 * @ww.tag name="if" tld-body-content="JSP"
 * description="If tag"
 */
public class IfTag extends WebWorkBodyTagSupport {
    public static final String ANSWER = "webwork.if.answer";

    Boolean answer;
    String test;

    /**
     * @ww.tagattribute required="true"
     * description="Logic to determined if body of tag is to be displayed"
     */
    public void setTest(String test) {
        this.test = test;
    }

    public int doEndTag() throws JspException {
        pageContext.setAttribute(ANSWER, answer);

        return SKIP_BODY;
    }

    public int doStartTag() throws JspException {
        answer = (Boolean) findValue(test, Boolean.class);

        if (answer == null) {
            answer = Boolean.FALSE;
        }

        if (answer.booleanValue()) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}

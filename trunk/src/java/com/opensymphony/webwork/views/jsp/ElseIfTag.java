/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import javax.servlet.jsp.JspException;


/**
 * @jsp.tag name="elseif" bodycontent="JSP"
 * @author Rick Salsa (rsal@mb.sympatico.ca)
 */
public class ElseIfTag extends WebWorkBodyTagSupport {

    protected Boolean answer;
    protected String test;

    /**
     * @jsp.attribute required="true"  rtexprvalue="true"
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

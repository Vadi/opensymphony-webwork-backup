/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import javax.servlet.jsp.JspException;


/**
 * @jsp.tag name="if" bodycontent="JSP"
 * @author $Author$
 */
public class IfTag extends WebWorkBodyTagSupport {
    public static final String ANSWER = "webwork.if.answer";

    Boolean answer;
    String test;

    /**
     * @jsp.attribute required="true"  rtexprvalue="true"
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

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class IfTag extends WebWorkTagSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    public static final String ANSWER = "webwork.if.answer";

    //~ Instance fields ////////////////////////////////////////////////////////

    Boolean answer;
    String test;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setTest(String test) {
        this.test = test;
    }

    public int doEndTag() throws JspException {
        if (answer != null) {
            pageContext.setAttribute(ANSWER, answer);
        }

        return SKIP_BODY;
    }

    public int doStartTag() throws JspException {
        Object o = findValue(test);

        if ((o != null) && o instanceof Boolean) {
            answer = (Boolean) o;

            if (answer.booleanValue()) {
                return EVAL_BODY_INCLUDE;
            }
        }

        return SKIP_BODY;
    }
}

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
 * @author Rick Salsa (rsal@mb.sympatico.ca)
 * @version $Revision$
 */
public class ElseIfTag extends TagSupport {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected Boolean answer;
    protected OgnlValueStack stack;
    protected String test;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setTest(String test) {
        this.test = test;
    }

    public int doEndTag() throws JspException {
        if (answer == null) {
            answer = new Boolean(false);
        }

        if (answer.booleanValue()) {
            pageContext.setAttribute(IfTag.ANSWER, answer);
        }

        return EVAL_PAGE;
    }

    public int doStartTag() throws JspException {
        stack = ActionContext.getContext().getValueStack();

        Boolean ifResult = (Boolean) pageContext.getAttribute(IfTag.ANSWER);

        if ((ifResult == null) || (ifResult.booleanValue() == true)) {
            return SKIP_BODY;
        }

        //make the comparision
        Object o = null;

        if (stack != null) {
            o = stack.findValue(test);
        }

        if ((o != null) && o instanceof Boolean) {
            answer = (Boolean) o;

            if (answer.booleanValue()) {
                return EVAL_BODY_INCLUDE;
            }
        }

        return SKIP_BODY;
    }

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        this.answer = null;
        this.stack = null;
        this.test = null;
    }
}

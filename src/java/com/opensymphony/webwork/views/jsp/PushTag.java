/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class PushTag extends BodyTagSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(PushTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    private OgnlValueStack stack;
    private String value;
    private boolean pushed = false;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setValue(String value) {
        this.value = value;
    }

    public int doEndTag() throws JspException {
        OgnlValueStack stack = getStack();

        if (pushed && (stack != null)) {
            stack.pop();
        }

        return SKIP_BODY;
    }

    public int doStartTag() throws JspException {
        OgnlValueStack stack = getStack();

        if (stack != null) {
            stack.push(stack.findValue(value));
            pushed = true;
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
    * Clears all the instance variables to allow this instance to be reused.
    */
    public void release() {
        super.release();
        this.stack = null;
        this.value = null;
        this.pushed = false;
    }

    protected OgnlValueStack getStack() {
        if (stack == null) {
            stack = (OgnlValueStack) ActionContext.getContext().getValueStack();
        }

        return stack;
    }
}

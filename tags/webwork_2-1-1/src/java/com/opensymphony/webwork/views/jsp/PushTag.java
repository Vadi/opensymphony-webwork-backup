/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;


/**
 * @author $Author$
 * @version $Revision$
 */
public class PushTag extends WebWorkBodyTagSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(PushTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

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
            stack.push(findValue(value));
            pushed = true;
        } else {
            pushed = false; // need to ensure push is assigned, otherwise we may have a leftover value
        }

        return EVAL_BODY_INCLUDE;
    }
}

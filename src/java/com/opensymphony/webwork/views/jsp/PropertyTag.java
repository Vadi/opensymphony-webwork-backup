/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class PropertyTag extends TagSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(PropertyTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    private String value;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setValue(String value) {
        this.value = value;
    }

    public int doStartTag() throws JspException {
        OgnlValueStack stack = ActionContext.getContext().getValueStack();

        if (stack != null) {
            try {
                if (value == null) {
                    pageContext.getOut().print(stack.getRoot().peek());
                } else {
                    pageContext.getOut().print(stack.findValue(value, String.class));
                }
            } catch (IOException e) {
                log.info("Could not print out value '" + value + "': " + e.getMessage());
            }
        }

        return EVAL_PAGE;
    }

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        this.value = null;
    }
}

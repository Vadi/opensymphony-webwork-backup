/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.util.TextUtils;

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

    private String defaultValue;
    private String value;
    private boolean escape = false;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setEscape(boolean escape) {
        this.escape = escape;
    }

    public int doStartTag() throws JspException {
        OgnlValueStack stack = ActionContext.getContext().getValueStack();

        try {
            if ((stack != null) && (stack.size() > 0)) {
                Object actualValue = null;

                if (value == null) {
                    value = "that";
                }

                actualValue = stack.findValue(value, String.class);

                if (actualValue != null) {
                    pageContext.getOut().print(prepare(actualValue));
                } else if (defaultValue != null) {
                    pageContext.getOut().print(prepare(defaultValue));
                }
            } else if (defaultValue != null) {
                pageContext.getOut().print(prepare(defaultValue));
            }
        } catch (IOException e) {
            log.info("Could not print out value '" + value + "': " + e.getMessage());
        }

        return SKIP_BODY;
    }

    private Object prepare(Object value) {
        if (escape) {
            return TextUtils.htmlEncode(value.toString());
        } else {
            return value;
        }
    }

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        this.value = null;
        this.defaultValue = null;
        this.escape = false;
    }
}

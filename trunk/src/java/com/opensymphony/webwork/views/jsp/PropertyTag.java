/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.util.TextUtils;

import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

import javax.servlet.jsp.JspException;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class PropertyTag extends WebWorkTagSupport {
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

    public void setEscape(boolean escape) {
        this.escape = escape;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int doStartTag() throws JspException {
        try {
            OgnlValueStack stack = getValueStack();

            Object actualValue = null;

            if (value == null) {
                value = "top";
            }

            actualValue = stack.findValue(value, String.class);

            if (actualValue != null) {
                pageContext.getOut().print(prepare(actualValue));
            } else if (defaultValue != null) {
                pageContext.getOut().print(prepare(defaultValue));
            }
        } catch (IOException e) {
            log.info("Could not print out value '" + value + "': " + e.getMessage());
        }

        return SKIP_BODY;
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

    private Object prepare(Object value) {
        if (escape) {
            return TextUtils.htmlEncode(value.toString());
        } else {
            return value;
        }
    }
}

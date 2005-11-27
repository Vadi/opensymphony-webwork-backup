/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.util.TextUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import java.io.IOException;


/**
 * @jsp.tag name="property" body-content="empty"
 * @author $Author$
 * @version $Revision$
 */
public class PropertyTag extends WebWorkBodyTagSupport {
    private static final Log log = LogFactory.getLog(PropertyTag.class);

    private String defaultValue;
    private String value;
    private boolean escape = true;

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setEscape(boolean escape) {
        this.escape = escape;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setValue(String value) {
        this.value = value;
    }

    public int doStartTag() throws JspException {
        try {
            Object actualValue = null;

            if (value == null) {
                value = "top";
            }

            // exception: don't call findString(), since we don't want the
            //            expression parsed in this one case. it really
            //            doesn't make sense, in fact.
            actualValue = getStack().findValue(value, String.class);

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

    private Object prepare(Object value) {
        if (escape) {
            return TextUtils.htmlEncode(value.toString());
        } else {
            return value;
        }
    }
}

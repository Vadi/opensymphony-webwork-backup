/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.velocity.context.Context;

import java.io.Writer;

import javax.servlet.jsp.JspException;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class ParamTag extends WebWorkTagSupport {
    //~ Instance fields ////////////////////////////////////////////////////////

    String name;
    String value;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int doStartTag() throws JspException {
        ParameterizedTag parent = (ParameterizedTag) findAncestorWithClass(this, ParameterizedTag.class);

        if (parent != null) {
            Object o = null;

            if (value == null) {
                value = "top";
            }

            o = findValue(value);

            parent.addParam(name, o);
        }

        return SKIP_BODY;
    }
}

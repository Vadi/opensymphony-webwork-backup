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
public class SetTag extends WebWorkTagSupport {
    //~ Instance fields ////////////////////////////////////////////////////////

    String name;
    String scope;
    String value;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setName(String name) {
        this.name = name;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int doStartTag() throws JspException {
        Object o;

        OgnlValueStack stack = getStack();

        if (value == null) {
            value = "top";
        }

        o = findValue(value);

        if ("application".equals(scope)) {
            super.pageContext.getServletContext().setAttribute(name, o);
        } else if ("session".equals(scope)) {
            pageContext.getSession().setAttribute(name, o);
        } else if ("request".equals(scope)) {
            pageContext.getRequest().setAttribute(name, o);
        } else if ("page".equals(scope)) {
            pageContext.setAttribute(name, o);
        } else {
            stack.getContext().put(name, o);
        }

        return SKIP_BODY;
    }

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        this.name = null;
        this.scope = null;
        this.value = null;
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.util.FastByteArrayOutputStream;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.io.PrintWriter;

import javax.servlet.jsp.tagext.TagSupport;


/**
 * WebWork base class for defining new tag handlers. 
 */
public abstract class WebWorkTagSupport extends TagSupport {
    //~ Methods ////////////////////////////////////////////////////////////////

    protected OgnlValueStack getStack() {
        return TagUtils.getStack(pageContext);
    }

    protected String findString(String expr) {
        expr = CompatUtil.compat(expr);

        return (String) findValue(expr, String.class);
    }

    protected Object findValue(String expr) {
        expr = CompatUtil.compat(expr);

        return getStack().findValue(expr);
    }

    protected Object findValue(String expr, Class toType) {
        expr = CompatUtil.compat(expr);

        return getStack().findValue(expr, toType);
    }

    protected String toString(Throwable t) {
        FastByteArrayOutputStream bout = new FastByteArrayOutputStream();
        PrintWriter wrt = new PrintWriter(bout);
        t.printStackTrace(wrt);
        wrt.close();

        return bout.toString();
    }
}

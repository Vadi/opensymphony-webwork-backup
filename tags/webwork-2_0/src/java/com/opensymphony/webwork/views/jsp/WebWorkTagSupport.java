/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.ApplicationMap;
import com.opensymphony.webwork.dispatcher.ServletDispatcher;
import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.webwork.util.FastByteArrayOutputStream;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.io.PrintWriter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * User: plightbo
 * Date: Oct 17, 2003
 * Time: 7:06:19 AM
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

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.util.FastByteArrayOutputStream;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.PrintWriter;


/**
 * User: plightbo
 * Date: Oct 17, 2003
 * Time: 7:09:15 AM
 */
public class WebWorkBodyTagSupport extends BodyTagSupport {
    //~ Methods ////////////////////////////////////////////////////////////////

    protected OgnlValueStack getStack() {
        return TagUtils.getStack(pageContext);
    }

    protected String findString(String expr) {
        return (String) findValue(expr, String.class);
    }

    protected Object findValue(String expr) {
        if (WebWorkTagSupport.ALT_SYNTAX) {
            // does the expression start with %{ and end with }? if so, just cut it off!
            if (expr.startsWith("%{") && expr.endsWith("}")) {
                expr = expr.substring(2, expr.length() - 1);
            }
        }

        return getStack().findValue(expr);
    }

    protected Object findValue(String expr, Class toType) {
        if (WebWorkTagSupport.ALT_SYNTAX && toType == String.class) {
            return WebWorkTagSupport.translateVariables(expr, getStack());
        } else {
            if (WebWorkTagSupport.ALT_SYNTAX) {
                // does the expression start with %{ and end with }? if so, just cut it off!
                if (expr.startsWith("%{") && expr.endsWith("}")) {
                    expr = expr.substring(2, expr.length() - 1);
                }
            }

            return getStack().findValue(expr, toType);
        }
    }

    protected String toString(Throwable t) {
        FastByteArrayOutputStream bout = new FastByteArrayOutputStream();
        PrintWriter wrt = new PrintWriter(bout);
        t.printStackTrace(wrt);
        wrt.close();

        return bout.toString();
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.util.FastByteArrayOutputStream;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.PrintWriter;


/**
 * WebWork base class for defining new tag handlers.
 */
public abstract class WebWorkTagSupport extends TagSupport {
    public static final boolean ALT_SYNTAX = "true".equals(Configuration.getString("webwork.tag.altSyntax"));

    public static String translateVariables(String expression, OgnlValueStack stack) {
        while (true) {
            int x = expression.indexOf("%{");
            int y = expression.indexOf("}", x);

            if ((x != -1) && (y != -1)) {
                String var = expression.substring(x + 2, y);

                Object o = stack.findValue(var, String.class);

                if (o != null) {
                    expression = expression.substring(0, x) + o + expression.substring(y + 1);
                } else {
                    // the variable doesn't exist, so don't display anything
                    expression = expression.substring(0, x) + expression.substring(y + 1);
                }
            } else {
                break;
            }
        }

        return expression;
    }

    protected OgnlValueStack getStack() {
        return TagUtils.getStack(pageContext);
    }

    protected String findString(String expr) {
        return (String) findValue(expr, String.class);
    }

    protected Object findValue(String expr) {
        expr = CompatUtil.compat(expr);

        return getStack().findValue(expr);
    }

    protected Object findValue(String expr, Class toType) {
        if (ALT_SYNTAX && toType == String.class) {
            return translateVariables(expr, getStack());
        } else {
            expr = CompatUtil.compat(expr);

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

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.config.Configuration;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * User: plightbo
 * Date: Nov 4, 2003
 * Time: 7:32:57 AM
 */
public class CompatUtil {
    //~ Static fields/initializers /////////////////////////////////////////////

    public static boolean compatMode = false;

    static {
        if (Configuration.isSet("compatibility.mode")) {
            compatMode = "true".equals(Configuration.getString("compatibility.mode"));
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public static String compat(String expr) {
        if (compatMode) {
            boolean toggle = false;
            StringTokenizer st = new StringTokenizer(expr, "'", true);

            if (!st.hasMoreTokens()) {
                return convert(expr);
            }

            StringBuffer sb = new StringBuffer();

            while (st.hasMoreTokens()) {
                String token = st.nextToken();

                if (!token.equals("'")) {
                    if (toggle) {
                        sb.append(token);
                    } else {
                        sb.append(convert(token));
                    }
                } else {
                    sb.append('\'');
                    toggle = !toggle;
                }
            }

            return sb.toString();
        }

        return expr;
    }

    private static String convert(String expr) {
        expr = expr.replaceAll("\\.\\.", "[1]");
        expr = expr.replaceAll("\\.", "top");
        expr = expr.replaceAll("([^\\d\\/ ]+?)\\/([^\\d\\/ ]+?)", "$1\\.$2");
        expr = expr.replaceAll("\\@([^\\. \\[]+)", "#attr\\.get\\('$1'\\)");
        expr = expr.replaceAll("\\$([^\\. \\[]+)", "#parameters\\.get\\('$1'\\)");

        return expr;
    }
}

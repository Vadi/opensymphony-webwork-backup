package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.config.Configuration;

/**
 * User: plightbo
 * Date: Nov 4, 2003
 * Time: 7:32:57 AM
 */
public class CompatUtil {
    private static boolean compatMode = false;
    static {
        if (Configuration.isSet("compatibility.mode")) {
            compatMode = "true".equals(Configuration.getString("compatibility.mode"));
        }
    }

    public static String compat(String expr) {
        if (compatMode) {
            expr = expr.replaceAll("\\.\\.", "[1]");
            expr = expr.replaceAll("\\.", "top");
            expr = expr.replaceAll("([^\\d\\/ ]+?)\\/([^\\d\\/ ]+?)", "$1\\.$2");
            expr = expr.replaceAll("\\@([^\\. \\[]+)", "#attr\\.get\\($1\\)");
            expr = expr.replaceAll("\\$([^\\. \\[]+)", "#parameters\\.get\\($1\\)");
        }

        return expr;
    }
}

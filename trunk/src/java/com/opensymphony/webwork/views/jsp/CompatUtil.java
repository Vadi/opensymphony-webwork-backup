package com.opensymphony.webwork.views.jsp;

/**
 * User: plightbo
 * Date: Nov 4, 2003
 * Time: 7:32:57 AM
 */
public class CompatUtil {
    public static String compat(String expr) {
        expr = expr.replaceAll("\\.\\.", "[1]");
        expr = expr.replaceAll("\\.", "top");
        expr = expr.replaceAll("([^\\d\\/ ]+?)\\/([^\\d\\/ ]+?)", "$1\\.$2");
        expr = expr.replaceAll("\\@([^\\. \\[]+)", "#request\\.get\\($1\\)");

        return expr;
    }
}

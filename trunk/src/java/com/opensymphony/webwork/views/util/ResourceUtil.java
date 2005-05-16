package com.opensymphony.webwork.views.util;

import javax.servlet.http.HttpServletRequest;

/**
 * User: plightbo
 * Date: May 15, 2005
 * Time: 6:36:59 PM
 */
public class ResourceUtil {
    public static String getResourceBase(HttpServletRequest req) {
        String path = req.getServletPath();
        return path.substring(0, path.lastIndexOf('/'));
    }
}

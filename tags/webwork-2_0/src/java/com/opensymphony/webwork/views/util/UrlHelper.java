/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.util;

import com.opensymphony.webwork.ServletActionContext;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;

import java.net.URLEncoder;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * UrlHelper
 * @author Jason Carreira
 * Created Apr 19, 2003 9:32:19 PM
 */
public class UrlHelper {
    //~ Methods ////////////////////////////////////////////////////////////////

    private static final String AMP = "&";

    public static String buildUrl(String action, HttpServletRequest request, HttpServletResponse response, Map params) {
        return buildUrl(action, request, response, params, null, true, true);
    }

    public static String buildUrl(String action, HttpServletRequest request, HttpServletResponse response,
                                  Map params, String scheme, boolean includeContext, boolean encodeResult) {
        StringBuffer link = new StringBuffer();

         boolean changedScheme = false;

        // only append scheme if it is different to the current scheme
        if (scheme != null && !scheme.equals(request.getScheme())) {
            changedScheme = true;
            link.append(scheme);
            link.append("://");
            link.append(request.getServerName());

            // do not append port for default ports
            int port = request.getServerPort();
            if (!(scheme.equals("http") && port == 80) && !(scheme.equals("https") && port == 443)) {
                link.append(":");
                link.append(port);
            }
        }

        if (action != null) {
            // Check if context path needs to be added
            // Add path to absolute links
            if (action.startsWith("/") && includeContext) {
                link.append(request.getContextPath());
            }
            else if (changedScheme) {
                String uri = request.getRequestURI();
                link.append(uri.substring(0, uri.lastIndexOf('/')));
            }

            // Add page
            link.append(action);
        } else {
            // Go to "same page"
            String requestURI = (String) request.getAttribute("webwork.request_uri");
            if (requestURI == null) {
                requestURI = request.getRequestURI();
            }

            link.append(requestURI);
        }

        //if the action was not explicitly set grab the params from the request
        if ((params != null) && (params.size() > 0)) {
            if (link.toString().indexOf("?") == -1) {
                link.append("?");
            } else {
                link.append(AMP);
            }

            // Set params
            Iterator enum = params.entrySet().iterator();

            String[] valueHolder = new String[1];
            while (enum.hasNext()) {
                Map.Entry entry = (Map.Entry) enum.next();
                String name = (String) entry.getKey();
                Object value = entry.getValue();

                String[] values;
                if (value instanceof String[]) {
                    values = (String[]) value;
                } else {
                    valueHolder[0] = value.toString();
                    values = valueHolder;
                }

                for (int i = 0; i < values.length; i++) {
                    if (values[i] != null) {
                        link.append(name);
                        link.append('=');
                        link.append(translateAndEncode(values[i]));
                    }

                    if (i < values.length - 1) {
                        link.append("&");
                    }
                }

                if (enum.hasNext()) {
                    link.append("&");
                }
            }
        }

        String result;

        try {
            result = encodeResult ? response.encodeURL(link.toString()) : link.toString();
        } catch (Exception ex) {
            // Could not encode the URL for some reason
            // Use it unchanged
            result = link.toString();
        }

        return result;
    }

    /**
     * Translates any script expressions using {@link com.opensymphony.xwork.util.TextParseUtil#translateVariables} and
     * encodes the URL using {@link java.net.URLEncoder#encode}
     * @param input
     * @return the translated and encoded string
     */
    public static String translateAndEncode(String input) {
        OgnlValueStack valueStack = ServletActionContext.getContext().getValueStack();
        String output = TextParseUtil.translateVariables(input, valueStack);

        return URLEncoder.encode(output);
    }
}

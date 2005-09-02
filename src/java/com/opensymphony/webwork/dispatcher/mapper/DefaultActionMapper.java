package com.opensymphony.webwork.dispatcher.mapper;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.dispatcher.ServletRedirectResult;
import com.opensymphony.xwork.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * Default action mapper implementation, using the standard *.[ext] (where ext
 * usually "action") pattern. This implementation does not concern itself with
 * parameters.
 *
 * @author Patrick Lightbody
 */
public class DefaultActionMapper implements ActionMapper {
    public ActionMapping getMapping(HttpServletRequest request) {
        String uri = request.getServletPath();
        if (uri == null) {
            uri = request.getRequestURI();
            uri = uri.substring(request.getContextPath().length());
        }
        String includeUri = (String) request.getAttribute("javax.servlet.include.servlet_path");
        if (includeUri != null) {
            uri = includeUri;
        }

        String ext = (String) Configuration.get("webwork.action.extension");
        if (!uri.endsWith("." + ext)) {
            return null;
        }

        // get the namespace
        String namespace = uri.substring(0, uri.lastIndexOf("/"));

        // Get action name ("Foo.action" -> "Foo" action)
        int beginIdx = uri.lastIndexOf("/");
        int endIdx = uri.lastIndexOf(".");
        String name = uri.substring(((beginIdx == -1) ? 0 : (beginIdx + 1)), (endIdx == -1) ? uri.length() : endIdx);

        String method = "";
        Result result = null;
        if (name.indexOf("!") != -1) {
            endIdx = name.lastIndexOf("!");
            method = name.substring(endIdx + 1, name.length());
            name = name.substring(0, endIdx);
        } else {
            for (Iterator iterator = request.getParameterMap().entrySet().iterator(); iterator.hasNext();) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                if (key.startsWith("method:")) {
                    method = key.substring("method:".length());
                } else if (key.startsWith("action:")) {
                    name = key.substring("action:".length());
                } else if (key.startsWith("redirect:")) {
                    String location = key.substring("redirect:".length());
                    ServletRedirectResult redirect = new ServletRedirectResult();
                    redirect.setLocation(location);
                    result = redirect;
                } else if (key.startsWith("redirect-action")) {
                    String location = key.substring("redirect-action:".length());
                    ServletRedirectResult redirect = new ServletRedirectResult();
                    redirect.setLocation(location + "." + ext);
                    result = redirect;
                }
            }
        }

        ActionMapping mapping = new ActionMapping(name, namespace, method, null);
        if (result != null) {
            mapping.setResult(result);
        }

        return mapping;
    }

    public String getUriFromActionMapping(ActionMapping mapping) {
        StringBuffer uri = new StringBuffer();

        uri.append(mapping.getNamespace()).append("/").append(mapping.getName());
        if (null != mapping.getMethod() && !"".equals(mapping.getMethod())) {
            uri.append("!").append(mapping.getMethod());
        }

        uri.append(".").append(Configuration.get("webwork.action.extension"));

        return uri.toString();
    }
}

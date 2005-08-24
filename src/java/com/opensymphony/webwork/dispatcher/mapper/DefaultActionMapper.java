package com.opensymphony.webwork.dispatcher.mapper;

import com.opensymphony.webwork.config.Configuration;

import javax.servlet.http.HttpServletRequest;

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
        String includeUri = (String) request.getAttribute("javax.servlet.include.servlet_path");
        if (includeUri != null) {
            uri = includeUri;
        }

        if (!uri.endsWith("." + Configuration.get("webwork.action.extension"))) {
            return null;
        }

        // get the namespace
        String namespace = uri.substring(0, uri.lastIndexOf("/"));

        // Get action name ("Foo.action" -> "Foo" action)
        int beginIdx = uri.lastIndexOf("/");
        int endIdx = uri.lastIndexOf(".");
        String name = uri.substring(((beginIdx == -1) ? 0 : (beginIdx + 1)), (endIdx == -1) ? uri.length() : endIdx);

        // remove the method ( "Foo!methodName" )
        String method = "";
        if (name.indexOf("!")!=-1) {
            endIdx = name.lastIndexOf("!");
            method = name.substring(endIdx+1, name.length());
            name = name.substring(0, endIdx);
        }

        return new ActionMapping(name, namespace, method, null);
    }

    public String getUriFromActionMapping(ActionMapping mapping) {
        StringBuffer uri = new StringBuffer();

        uri.append(mapping.getNamespace()).append("/").append(mapping.getName());
        if (null!=mapping.getMethod() && !"".equals(mapping.getMethod())) {
            uri.append("!").append(mapping.getMethod());
        }

        uri.append(".").append(Configuration.get("webwork.action.extension"));

        return uri.toString();
    }
}

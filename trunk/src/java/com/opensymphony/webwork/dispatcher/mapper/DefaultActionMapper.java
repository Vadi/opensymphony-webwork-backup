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

        if (!uri.endsWith("." + Configuration.get("webwork.action.extension"))) {
            return null;
        }

        // get the namespace
        String namespace = uri.substring(0, uri.lastIndexOf("/"));

        // Get action name ("Foo.action" -> "Foo" action)
        int beginIdx = uri.lastIndexOf("/");
        int endIdx = uri.lastIndexOf(".");
        String name = uri.substring(((beginIdx == -1) ? 0 : (beginIdx + 1)), (endIdx == -1) ? uri.length() : endIdx);

        return new ActionMapping(name, namespace, null);
    }

    public String getUriFromActionMapping(ActionMapping mapping) {
        return mapping.getNamespace() + "/" + mapping.getName() + Configuration.get("webwork.action.extension");
    }
}

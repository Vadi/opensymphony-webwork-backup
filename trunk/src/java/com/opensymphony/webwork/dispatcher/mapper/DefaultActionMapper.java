package com.opensymphony.webwork.dispatcher.mapper;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.util.PrefixTrie;
import com.opensymphony.webwork.dispatcher.ServletRedirectResult;
import com.opensymphony.webwork.views.util.UrlHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Default action mapper implementation, using the standard *.[ext] (where ext
 * usually "action") pattern. The extension is looked up from the WebWork
 * configuration key <b>webwork.action.exection</b>.
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * @author Patrick Lightbody
 */
public class DefaultActionMapper implements ActionMapper {

    static final String METHOD_PREFIX = "method:";
    static final String ACTION_PREFIX = "action:";
    static final String REDIRECT_PREFIX = "redirect:";
    static final String REDIRECT_ACTION_PREFIX = "redirect-action:";

    static PrefixTrie prefixTrie = new PrefixTrie() {{
        put(METHOD_PREFIX, new ParameterAction() {
            public void execute(String key, ActionMapping mapping) {
                mapping.setMethod(key.substring(METHOD_PREFIX.length()));
            }
        });

        put(ACTION_PREFIX, new ParameterAction() {
            public void execute(String key, ActionMapping mapping) {
                mapping.setName(key.substring(ACTION_PREFIX.length()));
            }
        });

        put(REDIRECT_PREFIX, new ParameterAction() {
            public void execute(String key, ActionMapping mapping) {
                ServletRedirectResult redirect = new ServletRedirectResult();
                redirect.setLocation(key.substring(REDIRECT_PREFIX.length()));
                mapping.setResult(redirect);
            }
        });

        put(REDIRECT_ACTION_PREFIX, new ParameterAction() {
            public void execute(String key, ActionMapping mapping) {
                String location = key.substring(REDIRECT_ACTION_PREFIX.length());
                ServletRedirectResult redirect = new ServletRedirectResult();
                String extension = getExtension();
                if (extension != null) {
                    location += "." + extension;
                }
                redirect.setLocation(location);
                mapping.setResult(redirect);
            }
        });
    }};

    public ActionMapping getMapping(HttpServletRequest request) {
        ActionMapping mapping = new ActionMapping();
        String uri = getUri(request);

        parseNameAndNamespace(uri, mapping);

        // handle special parameter prefixes.
        Map parameterMap = request.getParameterMap();
        for (Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            ParameterAction parameterAction = (ParameterAction) prefixTrie.get(key);
            if (parameterAction != null) {
                parameterAction.execute(key, mapping);
                break;
            }
        }

        if (mapping.getName() == null) {
            return null;
        }

        // handle "name!method" convention.
        String name = mapping.getName();
        int exclamation = name.lastIndexOf("!");
        if (exclamation != -1) {
            mapping.setName(name.substring(0, exclamation));
            mapping.setMethod(name.substring(exclamation + 1));
        }

        return mapping;
    }

    void parseNameAndNamespace(String uri, ActionMapping mapping) {
        String namespace, name;
        int lastSlash = uri.lastIndexOf("/");
        if (lastSlash == -1) {
            namespace = "";
            name = uri;
        } else {
            namespace = uri.substring(0, lastSlash);
            name = uri.substring(lastSlash + 1);
        }
        mapping.setNamespace(namespace);
        mapping.setName(dropExtension(name));
    }

    String dropExtension(String name) {
        String extension = getExtension();
        if (extension == null) {
            return name;
        }

        extension = "." + extension;
        return name.endsWith(extension)
            ? name.substring(0, name.length() - extension.length())
            : null;
    }

    /**
     * Returns null if no extension is specified.
     */
    static String getExtension() {
        String extension = (String) Configuration.get("webwork.action.extension");
        return extension.equals("") ? null : extension;
    }

    String getUri(HttpServletRequest request) {
        // handle http dispatcher includes.
        String uri = (String) request.getAttribute("javax.servlet.include.servlet_path");
        if (uri != null) {
            return uri;
        }

        uri = request.getServletPath();
        if (uri != null && !"".equals(uri)) {
            return uri;
        }

        uri = request.getRequestURI();
        return uri.substring(request.getContextPath().length());
    }

    public String getUriFromActionMapping(ActionMapping mapping) {
        StringBuffer uri = new StringBuffer();

        uri.append(mapping.getNamespace()).append("/").append(mapping.getName());
        if (null != mapping.getMethod() && !"".equals(mapping.getMethod())) {
            uri.append("!").append(mapping.getMethod());
        }

        String extension = getExtension();
        if (extension != null) {
            uri.append(".").append(extension);
        }

        UrlHelper.buildParametersString(mapping.getParams(), uri);

        return uri.toString();
    }

    interface ParameterAction {
        void execute(String key, ActionMapping mapping);
    }
}

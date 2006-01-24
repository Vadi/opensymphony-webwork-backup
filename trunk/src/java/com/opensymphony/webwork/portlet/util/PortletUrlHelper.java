package com.opensymphony.webwork.portlet.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.portlet.PortletMode;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.portlet.context.PortletActionContext;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;

/**
 * UrlHelper
 * 
 * @author Jason Carreira Created Apr 19, 2003 9:32:19 PM
 */
public class PortletUrlHelper {
    private static final Log LOG = LogFactory.getLog(PortletUrlHelper.class);

    /**
     * Default HTTP port (80).
     */
    private static final int DEFAULT_HTTP_PORT = 80;

    /**
     * Default HTTPS port (443).
     */
    private static final int DEFAULT_HTTPS_PORT = 443;

    private static final String AMP = "&";

    public static String buildUrl(String action, String namespace, Map params,
            String type, String mode, String state) {
        return buildUrl(action, namespace, params, null, type, mode, state,
                true, true);
    }

    public static String buildUrl(String action, String namespace, Map params,
            String scheme, String type, String portletMode, String windowState,
            boolean includeContext, boolean encodeResult) {
        RenderRequest request = PortletActionContext.getRenderRequest();
        RenderResponse response = PortletActionContext.getRenderResponse();
        LOG.debug("Creating url. Action = " + action + ", Namespace = "
                + namespace + ", Type = " + type);
        namespace = prependNamespace(namespace, portletMode);
        if(StringUtils.isEmpty(portletMode)) {
            portletMode = PortletActionContext.getRenderRequest().getPortletMode().toString();
        }
        String result = null;
        int paramStartIndex = action.indexOf('?');
        if (paramStartIndex > 0) {
            String value = action;
            action = value.substring(0, value.indexOf('?'));
            String queryStr = value.substring(paramStartIndex + 1);
            StringTokenizer tok = new StringTokenizer(queryStr, "&");
            while (tok.hasMoreTokens()) {
                String paramVal = tok.nextToken();
                String key = paramVal.substring(0, paramVal.indexOf('='));
                String val = paramVal.substring(paramVal.indexOf('=') + 1);
                params.put(key, new String[] { val });
            }
        }
        if (StringUtils.isNotEmpty(namespace)) {
            StringBuffer sb = new StringBuffer();
            sb.append(namespace);
            if(!action.startsWith("/") && !namespace.endsWith("/")) {
                sb.append("/");
            }
            action = sb.append(action).toString();
            LOG.debug("Resulting actionPath: " + action);
        }
        params.put("action", new String[] { action });

        PortletURL url = null;
        if ("action".equalsIgnoreCase(type)) {
            LOG.debug("Creating action url");
            url = response.createActionURL();
        } else {
            LOG.debug("Creating render url");
            url = response.createRenderURL();
        }

        params.put("portletwork.mode", portletMode);
        url.setParameters(ensureParamsAreStringArrays(params));

        if ("HTTPS".equalsIgnoreCase(scheme)) {
            try {
                url.setSecure(true);
            } catch (PortletSecurityException e) {
                LOG.error("Cannot set scheme to https", e);
            }
        }
        try {
            url.setPortletMode(getPortletMode(request, portletMode));
            url.setWindowState(getWindowState(request, windowState));
        } catch (Exception e) {
            LOG.error("Unable to set mode or state:" + e.getMessage(), e);
        }
        result = url.toString();
        // TEMP BUG-WORKAROUND FOR DOUBLE ESCAPING OF AMPERSAND
        if(result.indexOf("&amp;") >= 0) {
            result = StringUtils.replace(result, "&amp;", "&");
        }
        return result;

    }

    /**
     * @param namespace
     * @return
     */
    private static String prependNamespace(String namespace, String portletMode) {
        StringBuffer sb = new StringBuffer();
        PortletMode mode = PortletActionContext.getRenderRequest().getPortletMode();
        if(StringUtils.isNotEmpty(portletMode)) {
            mode = new PortletMode(portletMode);
        }
        String portletNamespace = PortletActionContext.getPortletNamespace();
        String modeNamespace = (String)PortletActionContext.getModeNamespaceMap().get(mode);
        LOG.debug("PortletNamespace: " + portletNamespace + ", modeNamespace: " + modeNamespace);
        if(StringUtils.isNotEmpty(portletNamespace)) {
            sb.append(portletNamespace);
        }
        if(StringUtils.isNotEmpty(modeNamespace)) {
            if(!modeNamespace.startsWith("/")) {
                sb.append("/");
            }
            sb.append(modeNamespace);
        }
        if(StringUtils.isNotEmpty(namespace)) {
            if(!namespace.startsWith("/")) {
                sb.append("/");
            }
            sb.append(namespace);
        }
        LOG.debug("Resulting namespace: " + sb);
        return sb.toString();
    }

    /**
     * Encode an url to a non webwork action resource, like stylesheet, image or
     * servlet.
     * 
     * @param value
     * @return
     */
    public static String buildResourceUrl(String value, Map params) {
        StringBuffer sb = new StringBuffer();
        // Relative URLs are not allowed in a portlet
        if (!value.startsWith("/")) {
            sb.append("/");
        }
        sb.append(value);
        if(params != null && params.size() > 0) {
            sb.append("?");
            Iterator it = params.keySet().iterator();
            while(it.hasNext()) {
                String key = (String)it.next();
                String val = (String)params.get(key);
                sb.append(URLEncoder.encode(key)).append("=");
                sb.append(URLEncoder.encode(val));
                if(it.hasNext()) {
                    sb.append("&");
                }
            }
        }
        RenderResponse resp = PortletActionContext.getRenderResponse();
        RenderRequest req = PortletActionContext.getRenderRequest();
        return resp.encodeURL(req.getContextPath() + sb.toString());
    }

    public static void buildParametersString(Map params, StringBuffer link) {
        if ((params != null) && (params.size() > 0)) {
            if (link.toString().indexOf("?") == -1) {
                link.append("?");
            } else {
                link.append(AMP);
            }

            // Set params
            Iterator iter = params.entrySet().iterator();

            String[] valueHolder = new String[1];

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
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

                    if (i < (values.length - 1)) {
                        link.append("&amp;");
                    }
                }

                if (iter.hasNext()) {
                    link.append("&amp;");
                }
            }
        }
    }

    /**
     * Translates any script expressions using
     * {@link com.opensymphony.xwork.util.TextParseUtil#translateVariables}and
     * encodes the URL using {@link java.net.URLEncoder#encode}with the
     * encoding of UTF-8 (as recommended by the w3c and pointed out in issue
     * WW-747).
     * 
     * @param input
     * @return the translated and encoded string
     */
    public static String translateAndEncode(String input) {
        OgnlValueStack valueStack = ActionContext.getContext().getValueStack();
        String output = TextParseUtil.translateVariables(input, valueStack);

        try {
            return URLEncoder.encode(output, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.warn("Could not encode URL parameter '" + input
                    + "', returning value un-encoded");
            return output;
        }
    }

    /**
     * Will ensure that all entries in <code>params</code> are String arrays,
     * as requried by the setParameters on the PortletURL.
     * 
     * @param params The parameters to the URL.
     * @return A Map with all parameters as String arrays.
     */
    public static Map ensureParamsAreStringArrays(Map params) {
        Map result = null;
        if (params != null) {
            result = new HashMap(params.size());
            Iterator it = params.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                Object val = params.get(key);
                if (val instanceof String[]) {
                    result.put(key, val);
                } else {
                    result.put(key, new String[] { val.toString() });
                }
            }
        }
        return result;
    }

    /**
     * @param portletReq
     * @param url
     */
    private static WindowState getWindowState(RenderRequest portletReq,
            String windowState) {
        WindowState state = portletReq.getWindowState();
        if (StringUtils.isNotEmpty(windowState)) {
            state = portletReq.getWindowState();
            if ("maximized".equalsIgnoreCase(windowState)) {
                state = WindowState.MAXIMIZED;
            } else if ("normal".equalsIgnoreCase(windowState)) {
                state = WindowState.NORMAL;
            } else if ("minimized".equalsIgnoreCase(windowState)) {
                state = WindowState.MINIMIZED;
            }
        }
        return state;
    }

    /**
     * @param portletReq
     * @param url
     * @throws JspException
     */
    private static PortletMode getPortletMode(RenderRequest portletReq,
            String portletMode) {
        PortletMode mode = portletReq.getPortletMode();

        if (StringUtils.isNotEmpty(portletMode)) {
            mode = portletReq.getPortletMode();
            if ("edit".equalsIgnoreCase(portletMode)) {
                mode = PortletMode.EDIT;
            } else if ("view".equalsIgnoreCase(portletMode)) {
                mode = PortletMode.VIEW;
            } else if ("help".equalsIgnoreCase(portletMode)) {
                mode = PortletMode.HELP;
            }
        }
        return mode;
    }
}
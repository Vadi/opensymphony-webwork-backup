package com.opensymphony.webwork.dispatcher.mapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * An extension of {@link ActionMapping} including arguments like :-
 * <ul>
 *      <li>scheme</li>
 *      <li>includeContext</li>
 *      <li>encodeResult</li>
 *      <li>escapeAmp</li>
 *      <li>HttpServletRequest</li>
 *      <li>JttpServletResponse</li>
 * </ul>
 * such that {@link ActionMapper}'s implementation could used it to generate
 * url through {@link ActionMapper#getUriFromActionMapping(ActionMapping)}. 
 *
 * @author tmjee
 * @version $Date$ $Id$
 */
public class ActionMappingEx extends ActionMapping {

    private String scheme;
    private boolean includeContext;
    private boolean encodeResult;
    private boolean escapeAmp;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ActionMappingEx(String name, String namespace, String method, Map params,
                           String scheme, boolean includeContext, boolean encodeResult,
                           boolean escapeAmp, HttpServletRequest request,
                           HttpServletResponse response) {
        super(name, namespace, method, params);
        this.scheme = scheme;
        this.includeContext = includeContext;
        this.encodeResult = encodeResult;
        this.escapeAmp = escapeAmp;
        this.request = request;
        this.response = response;
    }

    /**
     * Get request scheme.
     * @return String
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * Do we need to include context?
     * @return boolean
     */
    public boolean isIncludeContext() {
        return includeContext;
    }

    /**
     * Do we need to encode result?
     * @return boolean
     */
    public boolean isEncodeResult() {
        return encodeResult;
    }

    /**
     * Get {@link HttpServletRequest}.
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Get {@link HttpServletResponse}.
     * @return HttpServletResponse.
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Do we need to escape the ampersand?
     * @return boolean.
     */
    public boolean isEscapeAmp() {
        return escapeAmp;
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockHttpServletRequest;
import junit.framework.AssertionFailedError;

import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * WebWorkMockHttpServletRequest
 *
 * @author Jason Carreira
 *         Created Mar 28, 2003 10:28:50 PM
 */
public class WebWorkMockHttpServletRequest extends MockHttpServletRequest {
    //~ Instance fields ////////////////////////////////////////////////////////

    Locale locale = Locale.US;
    private Map attributes = new HashMap();
    private Map parameterMap = new HashMap();
    private String context = "";
    private String pathInfo = "";
    private String queryString;
    private String requestURI;
    private String scheme;
    private String serverName;
    private int serverPort;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAttribute(String s, Object o) {
        attributes.put(s, o);
    }

    public Object getAttribute(String s) {
        return attributes.get(s);
    }

    public Enumeration getAttributeNames() {
        Vector v = new Vector();
        v.addAll(attributes.keySet());

        return v.elements();
    }

    public String getContextPath() {
        return this.context;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setParameterMap(Map parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map getParameterMap() {
        return parameterMap;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getScheme() {
        return scheme;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public HttpSession getSession() {
        HttpSession session = null;

        try {
            session = super.getSession();
        } catch (AssertionFailedError e) {
            //ignore
        }

        if (session == null) {
            session = new WebWorkMockHttpSession();
            setSession(session);
        }

        return session;
    }

    public void setupGetContext(String context) {
        this.context = context;
    }

    public void setupGetPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }
}

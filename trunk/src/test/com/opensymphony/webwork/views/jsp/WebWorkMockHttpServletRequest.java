/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockHttpServletRequest;

import junit.framework.AssertionFailedError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;


/**
 * WebWorkMockHttpServletRequest
 * @author Jason Carreira
 * Created Mar 28, 2003 10:28:50 PM
 */
public class WebWorkMockHttpServletRequest extends MockHttpServletRequest {
    //~ Instance fields ////////////////////////////////////////////////////////

    Locale locale = Locale.US;
    private Map attributes = new HashMap();
    private Map parameterMap = new HashMap();
    private String pathInfo;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAttribute(String s, Object o) {
        attributes.put(s, o);
    }

    public Object getAttribute(String s) {
        return attributes.get(s);
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

    public void setupGetPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }
}

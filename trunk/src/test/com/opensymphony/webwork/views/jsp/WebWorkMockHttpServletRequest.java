/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;


/**
 * WebWorkMockHttpServletRequest
 * @author Jason Carreira
 * Created Mar 28, 2003 10:28:50 PM
 */
public class WebWorkMockHttpServletRequest extends MockHttpServletRequest {
    //~ Instance fields ////////////////////////////////////////////////////////

    private Map attributes = new HashMap();

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAttribute(String s, Object o) {
        attributes.put(s, o);
    }

    public Object getAttribute(String s) {
        return attributes.get(s);
    }

    public HttpSession getSession() {
        if (super.getSession() == null) {
            setSession(new WebWorkMockHttpSession());
        }

        return super.getSession();
    }
}

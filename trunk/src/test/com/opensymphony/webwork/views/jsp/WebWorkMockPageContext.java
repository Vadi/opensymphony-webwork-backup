/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockPageContext;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletResponse;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-Mar-2003
 * Time: 5:52:36 PM
 * To change this template use Options | File Templates.
 */
public class WebWorkMockPageContext extends MockPageContext {
    //~ Instance fields ////////////////////////////////////////////////////////

    private Map attributes = new HashMap();
    private ServletResponse response;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAttribute(String s, Object o) {
        this.attributes.put(s, o);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public Object getAttributes(String key) {
        return this.attributes.get(key);
    }

    public void setResponse(ServletResponse response) {
        this.response = response;
    }

    public ServletResponse getResponse() {
        return response;
    }

    public Object findAttribute(String s) {
        return attributes.get(s);
    }

    public void removeAttribute(String key) {
        this.attributes.remove(key);
    }
}

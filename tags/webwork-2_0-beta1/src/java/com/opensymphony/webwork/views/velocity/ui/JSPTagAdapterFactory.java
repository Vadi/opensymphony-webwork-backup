/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity.ui;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * Created by IntelliJ IDEA.
 * User: matt
 * Date: May 9, 2003
 * Time: 1:47:10 AM
 * To change this template use Options | File Templates.
 */
public class JSPTagAdapterFactory {
    //~ Instance fields ////////////////////////////////////////////////////////

    private Map tagclassMap = new HashMap();

    //~ Methods ////////////////////////////////////////////////////////////////

    public JSPTagAdapter createJSPTagAdapter(ServletConfig config, ServletRequest request, ServletResponse response) {
        return new JSPTagAdapter(tagclassMap, config, request, response);
    }
}

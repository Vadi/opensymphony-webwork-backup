/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.tagext.TagSupport;


/**
 * Created by IntelliJ IDEA.
 * User: jcarreira
 * Date: Oct 16, 2003
 * Time: 11:00:38 PM
 * To change this template use Options | File Templates.
 */
public abstract class ParameterizedTagSupport extends WebWorkTagSupport implements ParameterizedTag {
    //~ Instance fields ////////////////////////////////////////////////////////

    Map params;

    //~ Methods ////////////////////////////////////////////////////////////////

    public Map getParams() {
        if (params == null) {
            params = new HashMap();
        }

        return params;
    }

    public void addParam(String key, Object value) {
        if (key != null) {
            Map myParams = getParams();

            if (value == null) {
                myParams.remove(key);
            } else {
                params.put(key, value);
            }
        }
    }
}

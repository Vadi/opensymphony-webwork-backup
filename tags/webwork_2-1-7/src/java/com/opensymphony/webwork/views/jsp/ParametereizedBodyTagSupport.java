/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * User: plightbo
 * Date: Nov 16, 2003
 * Time: 3:25:09 PM
 */
public class ParametereizedBodyTagSupport extends WebWorkBodyTagSupport implements ParamTag.Parametric {
    //~ Static fields/initializers /////////////////////////////////////////////

    final protected static Log log = LogFactory.getLog(ParameterizedTagSupport.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    Map params;

    //~ Methods ////////////////////////////////////////////////////////////////

    public Map getParameters() {
        if (params == null) {
            params = new HashMap();
        }

        return params;
    }

    public void addParameter(String key, Object value) {
        if (key != null) {
            Map myParams = getParameters();

            if (value == null) {
                myParams.remove(key);
            } else {
                params.put(key, value);
            }
        }
    }
}

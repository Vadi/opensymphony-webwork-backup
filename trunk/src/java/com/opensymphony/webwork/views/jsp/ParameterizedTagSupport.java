/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public abstract class ParameterizedTagSupport extends WebWorkTagSupport implements ParamTag.Parametric {
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

    /**
     * <p>
     * Resets the attributes of this tag so that the tag may be reused.  As a general rule, only properties that are
     * not specified as an attribute or properties that are derived need to be reset.  Examples of this would include
     * the parameters Map in ParameterizedTag and the namespace in the ActionTag (which can be a derived value).
     * </p>
     *
     * <p>
     * This should be the last thing called as part of the doEndTag
     * </p>
     */
    protected void reset() {
        this.getParameters().clear();
    }
}

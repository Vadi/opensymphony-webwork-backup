/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import java.util.Map;


/**
 * The ParameterizedTag indicates that a particular Tag may have embedded params.  For example, if we were wanted to
 * use the ComponentTag and wanted to provide custom params to assist with the rendering, we could declare something
 * like
 *
 * <pre>
 * &lt;ui:component&gt;
 *  &lt;ui:param name="key"     value="[0]"/&gt;
 *  &lt;ui:param name="value"   value="[1]"/&gt;
 *  &lt;ui:param name="context" value="[2]"/&gt;
 * &lt;/ui:component&gt;
 * </pre>
 *
 * where the key will be the identifier and the value the result of an OGNL expression run against the current
 * OgnlValueStack
 *
 * @see com.opensymphony.xwork.util.OgnlValueStack
 * @author $Author$
 * @version $Revision$
 */
public interface ParameterizedTag {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * This method is intended to be called by the Velocity Template when it needs to render the widget
     * @return a map of user defined parmeters
     */
    public Map getParams();

    /**
     * This method is intended to be called via the ParamTag
     * @param key the identifier to be used to retrieve the param
     * @param value the Object to be stored, not an Ognl expression!  While an Ognl expression may be used to derive
     * this Object, the Object itself should be passed, not the expression.
     */
    public void addParam(String key, Object value);
}

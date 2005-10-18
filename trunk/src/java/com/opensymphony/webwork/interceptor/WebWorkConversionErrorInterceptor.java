/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.ConversionErrorInterceptor;
import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * <!-- START SNIPPET: description -->
 * TODO: Give a description of the Interceptor.
 * <!-- END SNIPPET: description -->
 *
 * <!-- START SNIPPET: parameters -->
 * TODO: Describe the paramters for this Interceptor.
 * <!-- END SNIPPET: parameters -->
 *
 * <!-- START SNIPPET: extending -->
 * TODO: Discuss some possible extension of the Interceptor.
 * <!-- END SNIPPET: extending -->
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;!-- TODO: Describe how the Interceptor reference will effect execution --&gt;
 * &lt;action name="someAction" class="com.examples.SomeAction"&gt;
 *      TODO: fill in the interceptor reference.
 *     &lt;interceptor-ref name=""/&gt;
 *     &lt;result name="success"&gt;good_result.ftl&lt;/result&gt;
 * &lt;/action&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * This interceptor adds the conversion errors from the ActionContext to the field errors of the Action
 * if the field value is not null, "", or {""} (a size 1 String array with only an empty String).
 *
 * @author Jason Carreira
 * @see com.opensymphony.xwork.ActionContext#getConversionErrors()
 * @see ConversionErrorInterceptor
 */
public class WebWorkConversionErrorInterceptor extends ConversionErrorInterceptor {

    protected Object getOverrideExpr(ActionInvocation invocation, Object value) {
        OgnlValueStack stack = invocation.getStack();

        try {
            stack.push(value);

            return "'" + stack.findValue("top", String.class) + "'";
        } finally {
            stack.pop();
        }
    }

    /**
     * Returns <tt>false</tt> if the value is null, "", or {""} (array of size 1 with
     * a blank element). Returns <tt>true</tt> otherwise.
     *
     * @param propertyName the name of the property to check.
     * @param value        the value to error check.
     * @return <tt>false</tt>  if the value is null, "", or {""}, <tt>true</tt> otherwise.
     */
    protected boolean shouldAddError(String propertyName, Object value) {
        if (value == null) {
            return false;
        }

        if ("".equals(value)) {
            return false;
        }

        if (value instanceof String[]) {
            String[] array = (String[]) value;

            if (array.length == 0) {
                return false;
            }

            if (array.length > 1) {
                return true;
            }

            String str = array[0];

            if ("".equals(str)) {
                return false;
            }
        }

        return true;
    }
}

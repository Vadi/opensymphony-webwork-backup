/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.xwork.interceptor.ConversionErrorInterceptor;


/**
 * This interceptor adds the conversion errors from the ActionContext to the field errors of the Action
 * if the field value is not null, "", or {""} (a size 1 String array with only an empty String).
 *
 * @see com.opensymphony.xwork.ActionContext#getConversionErrors()
 * @see ConversionErrorInterceptor
 * @author Jason Carreira
 */
public class WebWorkConversionErrorInterceptor extends ConversionErrorInterceptor {

    /**
     * Returns <tt>false</tt> if the value is null, "", or {""} (array of size 1 with
     * a blank element). Returns <tt>true</tt> otherwise.
     *
     * @param propertyName the name of the property to check.
     * @param value the value to error check.
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

package com.opensymphony.webwork.util;

import ognl.DefaultTypeConverter;

import java.util.Map;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Base class for type converters used in WebWork. This class provides two abstract methods that are used to convert
 * both to and from strings -- the critical functionality that is core to WebWork's type coversion system.
 *
 * <p/> Type converters do not have to use this class. It is merely a helper base class, although it is recommended that
 * you use this class as it provides the common type conversion contract required for most web-based type conversion (to
 * and from String).
 *
 * <!-- END SNIPPET: javadoc -->
 */
public abstract class WebWorkTypeConverter extends DefaultTypeConverter {
    public Object convertValue(Map context, Object o, Class toClass) {
        if (toClass.equals(String.class)) {
            return convertToString(context, o);
        } else if (o instanceof String[]) {
            return convertFromString(context, (String[]) o, toClass);
        } else if (o instanceof String) {
            return convertFromString(context, new String[]{(String) o}, toClass);
        } else {
            return super.convertValue(context, o, toClass);
        }
    }

    /**
     * Converts one or more String values to the specified class.
     *
     * @param context the action context
     * @param values  the String values to be converted, such as those submitted from an HTML form
     * @param toClass the class to convert to
     * @return the converted object
     */
    public abstract Object convertFromString(Map context, String[] values, Class toClass);

    /**
     * Converts the specified object to a String.
     *
     * @param context the action context
     * @param o       the object to be converted
     * @return the converted String
     */
    public abstract String convertToString(Map context, Object o);
}

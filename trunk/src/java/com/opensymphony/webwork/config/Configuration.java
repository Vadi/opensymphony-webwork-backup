/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;


/**
 * @author Rickard Öberg
 * @author Jason Carreira
 * Created Apr 9, 2003 9:47:38 PM
 */
public class Configuration {
    //~ Static fields/initializers /////////////////////////////////////////////

    // Static --------------------------------------------------------
    static Configuration configurationImpl;
    static Configuration defaultImpl;
    static Locale locale; // Cached locale
    private static final Log LOG = LogFactory.getLog(Configuration.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Set the current configuration implementation. Can only be called once.
     */
    public static void setConfiguration(Configuration aConfig) throws IllegalStateException {
        configurationImpl = aConfig;
        locale = null; // Reset cached locale
    }

    /**
     * Get the current configuration implementation.
     */
    public static Configuration getConfiguration() {
        return (configurationImpl == null) ? getDefaultConfiguration() : configurationImpl;
    }

    /**
     * WebWork locale accessor.
     */
    public static Locale getLocale() {
        if (locale == null) {
            try {
                StringTokenizer localeTokens = new StringTokenizer(getString("webwork.locale"), "_");
                String lang = null;
                String country = null;

                if (localeTokens.hasMoreTokens()) {
                    lang = localeTokens.nextToken();
                }

                if (localeTokens.hasMoreTokens()) {
                    country = localeTokens.nextToken();
                }

                locale = new Locale(lang, country);
            } catch (Throwable t) {
                // Default
                LOG.warn("Setting locale to the default locale");
                locale = Locale.getDefault();
            }
        }

        return locale;
    }

    /**
     * Determines whether or not a value has been set. Useful for testing for the existance of parameter without
     * throwing an IllegalArgumentException.
     */
    public static boolean isSet(String aName) {
        return getConfiguration().isSetImpl(aName);
    }

    /**
     * Get a named setting as a string.
     */
    public static String getString(String aName) throws IllegalArgumentException {
        String val = get(aName).toString();

        return val;
    }

    /**
    * Get a named setting.
    */
    public static Object get(String aName) throws IllegalArgumentException {
        Object val = getConfiguration().getImpl(aName);

        return val;
    }

    /**
     * List setting names.
     */
    public static Iterator list() {
        return getConfiguration().listImpl();
    }

    /**
     * Determines whether or not a value has been set. Useful for testing for the existance of parameter without
     * throwing an IllegalArgumentException.
     */
    public boolean isSetImpl(String aName) {
        return false;
    }

    /**
     * Set a named setting.
     */
    public static void set(String aName, Object aValue) throws IllegalArgumentException, UnsupportedOperationException {
        getConfiguration().setImpl(aName, aValue);
    }

    /**
     * Set a named setting.
     */
    public void setImpl(String aName, Object aValue) throws IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException("This configuration does not support updating a setting");
    }

    /**
     * Get a named setting.
     *
     * @throws IllegalArgumentException if there is no configuration parameter with the given name.
     */
    public Object getImpl(String aName) throws IllegalArgumentException {
        return null;
    }

    /**
     * List setting names.
     */
    public Iterator listImpl() {
        throw new UnsupportedOperationException("This configuration does not support listing the settings");
    }

    private static Configuration getDefaultConfiguration() {
        if (defaultImpl == null) {
            // Create bootstrap implementation
            defaultImpl = new DefaultConfiguration();

            // Create default implementation
            try {
                String className = getString("webwork.configuration");

                if (!className.equals(defaultImpl.getClass().getName())) {
                    try {
                        defaultImpl = (Configuration) Thread.currentThread().getContextClassLoader().loadClass(className).newInstance();
                    } catch (Exception e) {
                        LOG.error("Could not instantiate configuration", e);
                    }
                }
            } catch (IllegalArgumentException ex) {
                // ignore
            }
        }

        return defaultImpl;
    }
}

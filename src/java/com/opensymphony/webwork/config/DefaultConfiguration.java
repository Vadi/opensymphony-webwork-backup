/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;


/**
 * @author Rickard �berg
 * @author Jason Carreira
 * Created Apr 9, 2003 9:48:55 PM
 */
public class DefaultConfiguration extends Configuration {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected Log log = LogFactory.getLog(this.getClass());

    // Attributes ----------------------------------------------------
    Configuration config;

    //~ Constructors ///////////////////////////////////////////////////////////

    // Constructors --------------------------------------------------
    public DefaultConfiguration() {
        // Create default implementations
        // Use default properties and webwork.properties
        ArrayList list = new ArrayList();

        try {
            list.add(new PropertiesConfiguration("webwork"));
        } catch (Exception e) {
            log.warn("Could not find webwork.properties");
        }

        try {
            list.add(new PropertiesConfiguration("com/opensymphony/webwork/default"));
        } catch (Exception e) {
            log.error("Could not find com/opensymphony/webwork/default.properties", e);
        }

        Configuration[] configList = new Configuration[list.size()];
        config = new DelegatingConfiguration((Configuration[]) list.toArray(configList));

        // Add list of additional properties configurations
        try {
            StringTokenizer configFiles = new StringTokenizer((String) config.getImpl("webwork.custom.properties"), ",");

            while (configFiles.hasMoreTokens()) {
                String name = configFiles.nextToken();

                try {
                    list.add(new PropertiesConfiguration(name));
                } catch (Exception e) {
                    log.error("Could not find " + name + ".properties. Skipping");
                }
            }

            configList = new Configuration[list.size()];
            config = new DelegatingConfiguration((Configuration[]) list.toArray(configList));
        } catch (IllegalArgumentException e) {
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Set a named setting
     */
    public void setImpl(String aName, Object aValue) throws IllegalArgumentException, UnsupportedOperationException {
        config.setImpl(aName, aValue);
    }

    /**
     * Get a named setting.
     */
    public Object getImpl(String aName) throws IllegalArgumentException {
        // Delegate
        return config.getImpl(aName);
    }

    /**
     * determines whether or not a value has been set.  useful for testing for the existance of parameter without
     * throwing an IllegalArgumentException
     * @return true if this setting is defined
     */
    public boolean isSetImpl(String aName) {
        return config.isSetImpl(aName);
    }

    /**
     * List setting names
     */
    public Iterator listImpl() {
        return config.listImpl();
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.config;

import java.io.IOException;

import java.net.URL;

import java.util.Iterator;
import java.util.Properties;


/**
 * @author Rickard Öberg
 * @author Jason Carreira
 * Created Apr 9, 2003 9:50:03 PM
 */
public class PropertiesConfiguration extends Configuration {
    //~ Instance fields ////////////////////////////////////////////////////////

    // Attributes ----------------------------------------------------
    Properties settings;

    //~ Constructors ///////////////////////////////////////////////////////////

    // Constructors --------------------------------------------------
    public PropertiesConfiguration(String aName) {
        settings = new Properties();

        URL settingsUrl = Thread.currentThread().getContextClassLoader().getResource(aName + ".properties");

        if (settingsUrl == null) {
            throw new IllegalStateException(aName + ".properties missing");
        }

        // Load settings
        try {
            settings.load(settingsUrl.openStream());
        } catch (IOException e) {
            throw new RuntimeException("Could not load " + aName + ".properties:" + e);
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setImpl(String aName, Object aValue) {
        settings.put(aName, aValue);
    }

    /**
     * Get a named setting.
     */
    public Object getImpl(String aName) throws IllegalArgumentException {
        Object setting = settings.get(aName);

        if (setting == null) {
            throw new IllegalArgumentException("No such setting:" + aName);
        }

        return setting;
    }

    public boolean isSetImpl(String aName) {
        if (settings.get(aName) != null) {
            return true;
        } else {
            return false;
        }
    }

    public Iterator listImpl() {
        return settings.keySet().iterator();
    }
}

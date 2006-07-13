/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.config;

import com.opensymphony.webwork.WebWorkException;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;


/**
 * A class to handle configuration via a properties file.
 *
 * @author Rickard �berg
 * @author Jason Carreira
 * @author Bill Lynch (docs)
 */
public class PropertiesConfiguration extends Configuration {

    Properties settings;


    /**
     * Creates a new properties config given the name of a properties file. The name is expected to NOT have
     * the ".properties" file extension.  So when <tt>new PropertiesConfiguration("foo")</tt> is called
     * this class will look in the classpath for the <tt>foo.properties</tt> file.
     *
     * @param name the name of the properties file, excluding the ".properties" extension.
     */
    public PropertiesConfiguration(String name) {
        settings = new Properties();

        URL settingsUrl = Thread.currentThread().getContextClassLoader().getResource(name + ".properties");

        if (settingsUrl == null) {
            throw new IllegalStateException(name + ".properties missing");
        }

        // Load settings
        try {
            settings.load(settingsUrl.openStream());
        } catch (IOException e) {
            throw new WebWorkException("Could not load " + name + ".properties:" + e);
        }
    }


    /**
     * Sets a property in the properties file.
     *
     * @see #set(String, Object)
     */
    public void setImpl(String aName, Object aValue) {
        settings.put(aName, aValue);
    }

    /**
     * Gets a property from the properties file.
     *
     * @see #get(String)
     */
    public Object getImpl(String aName) throws IllegalArgumentException {
        Object setting = settings.get(aName);

        if (setting == null) {
            throw new IllegalArgumentException("No such setting:" + aName);
        }

        return setting;
    }

    /**
     * Tests to see if a property exists in the properties file.
     *
     * @see #isSet(String)
     */
    public boolean isSetImpl(String aName) {
        if (settings.get(aName) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Lists all keys in the properties file.
     *
     * @see #list()
     */
    public Iterator listImpl() {
        return settings.keySet().iterator();
    }
}

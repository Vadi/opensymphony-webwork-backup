/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.config;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * @author Rickard Öberg
 * @author Jason Carreira
 * Created Apr 9, 2003 9:52:30 PM
 */
public class DelegatingConfiguration extends Configuration {
    //~ Instance fields ////////////////////////////////////////////////////////

    // Attributes ----------------------------------------------------
    Configuration[] configList;

    //~ Constructors ///////////////////////////////////////////////////////////

    // Constructors --------------------------------------------------
    public DelegatingConfiguration(Configuration[] aConfigList) {
        configList = aConfigList;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Set a named setting
     */
    public void setImpl(String aName, Object aValue) throws IllegalArgumentException, UnsupportedOperationException {
        // Determine which config to use by using get
        // Delegate to the other configurations
        IllegalArgumentException e = null;

        for (int i = 0; i < configList.length; i++) {
            try {
                configList[i].getImpl(aName);

                // Found it, now try setting
                configList[i].setImpl(aName, aValue);

                // Worked, now return
                return;
            } catch (IllegalArgumentException ex) {
                e = ex;

                // Try next config
            }
        }

        throw e;
    }

    /**
     * Get a named setting.
     */
    public Object getImpl(String aName) throws IllegalArgumentException {
        // Delegate to the other configurations
        IllegalArgumentException e = null;

        for (int i = 0; i < configList.length; i++) {
            try {
                return configList[i].getImpl(aName);
            } catch (IllegalArgumentException ex) {
                e = ex;

                // Try next config
            }
        }

        throw e;
    }

    /**
     * determines whether or not a value has been set.  useful for testing for the existance of parameter without
     * throwing an IllegalArgumentException
     */
    public boolean isSetImpl(String aName) {
        for (int i = 0; i < configList.length; i++) {
            if (configList[i].isSetImpl(aName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * List setting names
     */
    public Iterator listImpl() {
        boolean workedAtAll = false;

        Set settingList = new HashSet();
        UnsupportedOperationException e = null;

        for (int i = 0; i < configList.length; i++) {
            try {
                Iterator list = configList[i].listImpl();

                while (list.hasNext()) {
                    settingList.add(list.next());
                }

                workedAtAll = true;
            } catch (UnsupportedOperationException ex) {
                e = ex;

                // Try next config
            }
        }

        if (!workedAtAll) {
            throw (e == null) ? new UnsupportedOperationException() : e;
        } else {
            return settingList.iterator();
        }
    }
}

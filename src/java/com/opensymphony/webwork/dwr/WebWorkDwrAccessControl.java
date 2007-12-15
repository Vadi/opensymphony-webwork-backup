/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dwr;

import org.directwebremoting.extend.Creator;
import org.directwebremoting.impl.DefaultAccessControl;

import java.lang.reflect.Method;

/**
 * Control who should be accessing which methods on which classes, allowing
 * WebWork to access classes under org.directwebremoting.webwork package.
 * 
 * @author tmjee
 * @version $Date$ $Id$
 */
public class WebWorkDwrAccessControl extends DefaultAccessControl {

    public static final String WEBWORK_INTEGRATION_PACKAGE = "org.directwebremoting.webwork";

    /**
     * Check the parameters are not DWR internal but allows it if its webwork integration
     * {@link #WEBWORK_INTEGRATION_PACKAGE}.
     * 
     * @param method The method that we want to execute
     */
    protected void assertAreParametersDwrInternal(Method method) {
        for (int j = 0; j < method.getParameterTypes().length; j++) {
            Class paramType = method.getParameterTypes()[j];
            if (paramType.getName().startsWith(WEBWORK_INTEGRATION_PACKAGE)) {
                // we allow access to WebWork integration stuff.
                return;
            }
            else {
                super.assertAreParametersDwrInternal(method);
            }
        }
    }

    /**
     * Is the class that we are executing a method on part of DWR? if so deny but if its
     * WebWork integration stuff, allows it {@link #WEBWORK_INTEGRATION_PACKAGE}.
     * 
     * @param creator The {@link Creator} that exposes the class
     */
    protected void assertIsClassDwrInternal(Creator creator) {
        if (creator.getType().getName().startsWith(WEBWORK_INTEGRATION_PACKAGE)) {
            // we allow access to WebWork integration stuff.
            return;
        }
        else {
            super.assertIsClassDwrInternal(creator);
        }
    }
}

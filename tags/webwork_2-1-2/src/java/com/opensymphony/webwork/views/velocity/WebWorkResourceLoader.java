/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.InputStream;


/**
 * Loads resource from the Thread's context ClassLoader.
 *
 * @author $Author$
 * @version $Revision$
 */
public class WebWorkResourceLoader extends ClasspathResourceLoader {
    //~ Methods ////////////////////////////////////////////////////////////////

    public synchronized InputStream getResourceStream(String name) throws ResourceNotFoundException {
        InputStream result = null;

        if ((name == null) || (name.length() == 0)) {
            throw new ResourceNotFoundException("No template name provided");
        }

        if (name.startsWith("/")) {
            name = name.substring(1);
        }

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            result = classLoader.getResourceAsStream(name);

            if (result == null) {
                classLoader = this.getClass().getClassLoader();
                result = classLoader.getResourceAsStream(name);
            }
        } catch (Exception fnfe) {
            throw new ResourceNotFoundException(fnfe.getMessage());
        }

        return result;
    }
}

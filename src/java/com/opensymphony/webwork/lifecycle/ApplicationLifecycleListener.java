/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.lifecycle;

import com.opensymphony.xwork.interceptor.component.ComponentConfiguration;
import com.opensymphony.xwork.interceptor.component.ComponentManager;
import com.opensymphony.xwork.interceptor.component.DefaultComponentManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 *
 *
 * @author joew@thoughtworks.com
 * @author $Author$
 * @version $Revision$
 */
public class ApplicationLifecycleListener implements ServletContextListener {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(ApplicationLifecycleListener.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    public void contextDestroyed(ServletContextEvent event) {
        ServletContext application = event.getServletContext();
        ComponentManager container = (ComponentManager) application.getAttribute("DefaultComponentManager");

        container.dispose();
    }

    public void contextInitialized(ServletContextEvent event) {
        ServletContext application = event.getServletContext();
        ComponentManager container = new DefaultComponentManager();
        ComponentConfiguration config = loadConfiguration();

        config.configure(container, "application");

        application.setAttribute("DefaultComponentManager", container);
        application.setAttribute("ComponentConfiguration", config);
    }

    private ComponentConfiguration loadConfiguration() {
        try {
            ComponentConfiguration config = new ComponentConfiguration();
            InputStream configXml = Thread.currentThread().getContextClassLoader().getResourceAsStream("components.xml");

            config.loadFromXml(configXml);

            return config;
        } catch (Exception e) {
            String message = "Cannot load components.xml configuration: " + e.getMessage();
            log.error(message, e);
            throw new RuntimeException(message);
        }
    }
}

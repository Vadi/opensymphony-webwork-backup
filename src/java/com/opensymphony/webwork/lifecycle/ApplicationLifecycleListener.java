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
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;


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

        if (container != null) {
            container.dispose();
        }
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
        ComponentConfiguration config = new ComponentConfiguration();
        InputStream configXml = Thread.currentThread().getContextClassLoader().getResourceAsStream("components.xml");

        if (configXml == null) {
            final String message = "Unable to find the file components.xml in the classpath.";
            log.error(message);
            throw new RuntimeException(message);
        }

        try {
            config.loadFromXml(configXml);
        } catch (IOException ioe) {
            log.error(ioe);
            throw new RuntimeException("Unable to load component configuration");
        } catch (SAXException sae) {
            log.error(sae);
            throw new RuntimeException("Unable to load component configuration");
        }

        return config;
    }
}

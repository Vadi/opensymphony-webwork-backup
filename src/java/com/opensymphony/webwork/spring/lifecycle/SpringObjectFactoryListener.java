/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.spring.lifecycle;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.spring.SpringObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ApplicationContextListener that sets up the environment so that XWork and
 * Webwork can load data and information from Spring. Relies on Spring's
 * {@link org.springframework.web.context.ContextLoaderListener}having been
 * called first.
 *
 * @author sms
 */
public class SpringObjectFactoryListener implements ServletContextListener
{
    /**
     * Creates a {@link com.opensymphony.xwork.spring.SpringObjectFactory}and sets that as the default
     * {@link ObjectFactory}to use for XWork.
     *
     * @param event
     *            The ServletContextEvent.
     */
    public void contextInitialized(ServletContextEvent event) {
        ServletContext app = event.getServletContext();
        ApplicationContext appContext = WebApplicationContextUtils
                .getWebApplicationContext(app);
        SpringObjectFactory objFactory = new SpringObjectFactory();
        objFactory.setApplicationContext(appContext);
        ObjectFactory.setObjectFactory(objFactory);
    }
    public void contextDestroyed(ServletContextEvent arg0) {
        // Nothing to do.
    }
}
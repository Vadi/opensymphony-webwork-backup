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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 *
 *
 * @author joew@thoughtworks.com
 * @author cameronbraid
 */
public class SessionLifecycleListener implements HttpSessionListener {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(SessionLifecycleListener.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    public void sessionCreated(HttpSessionEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("Session DefaultComponentManager : init");
        }

        HttpSession session = event.getSession();
		ComponentManager container = new SessionComponentManager();

        ServletContext application = session.getServletContext();
        ComponentManager fallback = (ComponentManager) application.getAttribute("DefaultComponentManager");

        container.setFallback(fallback);

        ComponentConfiguration config = (ComponentConfiguration) application.getAttribute("ComponentConfiguration");

        config.configure(container, "session");

        session.setAttribute("DefaultComponentManager", container);
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("Session DefaultComponentManager : destroy");
        }

        HttpSession session = event.getSession();
        ComponentManager container = (ComponentManager) session.getAttribute("DefaultComponentManager");

        if (container != null) {
            container.dispose();
        }
    }
    
	class SessionComponentManager extends DefaultComponentManager implements HttpSessionBindingListener
	{
		public void valueBound(HttpSessionBindingEvent event)
		{
		}

		public void valueUnbound(HttpSessionBindingEvent event)
		{
			if (log.isDebugEnabled()) {
					log.debug("Session DefaultComponentManager : destroy");
				}
			this.dispose();
		}
	}

}

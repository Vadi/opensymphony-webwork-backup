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

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * A filter to handle the lifecycle of an HTTP request-based XWork component manager.
 *
 * @author <a href="mailto:scottnelsonsmith@yahoo.com">Scott N. Smith</a>
 * @author <a href="mailto:joew@thoughtworks.com">Joe Walnes</a>
 * @author Bill Lynch (docs)
 */
public class RequestLifecycleFilter implements Filter {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(RequestLifecycleFilter.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Does nothing.
     */
    public void destroy() {
    }

    /**
     * Initializes an XWork component manager for the lifetime of the request. Once the manager is initialized,
     * control is passed down the filter chain and when it returns the container is destroyed.
     *
     * @param request  the ServletRequest object
     * @param response the ServletResponse object
     * @param chain    the FilterChain object
     * @throws IOException      if an error occurs while executing the filter
     * @throws ServletException if an error occurs while executing the filter
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ComponentManager container = createComponentManager();

        try {
            if (log.isDebugEnabled()) {
                log.debug("Request DefaultComponentManager : init");
            }

            HttpSession session = ((HttpServletRequest) request).getSession(true);
            ComponentManager fallback = (ComponentManager) session.getAttribute("DefaultComponentManager");

            container.setFallback(fallback);

            ComponentConfiguration config = (ComponentConfiguration) getServletContext(session).getAttribute("ComponentConfiguration");

            config.configure(container, "request");

            request.setAttribute("DefaultComponentManager", container);
            chain.doFilter(request, response);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("Request DefaultComponentManager : dispose");
            }

            container.dispose();
        }
    }

    /**
     * Does nothing.
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Servlet 2.3 specifies that the servlet context can be retrieved from the session. Unfortunately, some
     * versions of WebLogic can only retrieve the servlet context from the filter config. Hence, this method
     * enables subclasses to retrieve the servlet context from other sources.
     *
     * @param session the HTTP session where, in Servlet 2.3, the servlet context can be retrieved
     * @return the servlet context.
     */
    protected ServletContext getServletContext(HttpSession session) {
        return session.getServletContext();
    }

    /**
     * Returns a new <tt>DefaultComponentManager</tt> instance. This method is useful for developers
     * wishing to subclass this class and provide a different implementation of <tt>DefaultComponentManager</tt>.
     *
     * @return a new <tt>DefaultComponentManager</tt> instance.
     */
    protected DefaultComponentManager createComponentManager() {
        return new DefaultComponentManager();
    }
}

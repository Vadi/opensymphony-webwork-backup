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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 *
 * @author joew@thoughtworks.com
 * @author $Author$
 * @version $Revision$
 */
public class RequestLifecycleFilter implements Filter {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(RequestLifecycleFilter.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ComponentManager container = new DefaultComponentManager();

        try {
            if (log.isDebugEnabled()) {
                log.debug("Request DefaultComponentManager : init");
            }

            HttpSession session = ((HttpServletRequest) request).getSession(true);
            ComponentManager fallback = (ComponentManager) session.getAttribute("DefaultComponentManager");

            container.setFallback(fallback);

            ComponentConfiguration config = (ComponentConfiguration) session.getServletContext().getAttribute("ComponentConfiguration");

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

    public void init(FilterConfig filterConfig) throws ServletException {
    }
}

/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.lifecycle;

import com.opensymphony.xwork.interceptor.component.ComponentManager;
import org.hibernate.auction.persistence.components.PersistenceManager;
import org.hibernate.auction.persistence.components.PersistenceManagerAware;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.*;
import java.io.IOException;

/**
 * TransactionFilter
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public class TransactionFilter implements Filter {
    private static Log LOG = LogFactory.getLog(TransactionFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.debug("TransactionFilter.init() called.");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        ComponentManager cm = (ComponentManager) servletRequest.getAttribute(ComponentManager.COMPONENT_MANAGER_KEY);
        PersistenceManager persistenceManager = (PersistenceManager) cm.getComponent(PersistenceManagerAware.class);
        persistenceManager.begin();
        try{
            filterChain.doFilter(servletRequest,servletResponse);
            persistenceManager.commit();
        } catch(Throwable t) {
            persistenceManager.rollback();
            throw new ServletException("Transaction rolled back",t);
        }
        persistenceManager.commit();
    }

    public void destroy() {
    }
}

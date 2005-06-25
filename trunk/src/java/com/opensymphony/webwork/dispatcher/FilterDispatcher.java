/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapperFactory;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.xwork.ActionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Patrick Lightbody
 */
public class FilterDispatcher implements Filter, WebWorkStatics {
    private static final Log LOG = LogFactory.getLog(FilterDispatcher.class);

    private FilterConfig filterConfig;

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        ServletContext servletContext = filterConfig.getServletContext();

        // store a reference to ourself into the SessionContext so that we can generate a PageContext
        servletContext.setAttribute("webwork.servlet", this);

        DispatcherUtils.initialize(servletContext);
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        ActionMapping mapping = ActionMapperFactory.getMapper().getMapping(request);

        try {
            if (mapping == null) {
                // there is no action in this request, continue filter
                chain.doFilter(request, response);
                return;
            } else {
                DispatcherUtils du = DispatcherUtils.getInstance();
                du.prepare(request, response);

                try {
                    request = du.wrapRequest(request, filterConfig.getServletContext());
                } catch (IOException e) {
                    String message = "Could not wrap servlet request with MultipartRequestWrapper!";
                    LOG.error(message, e);
                    du.sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, new ServletException(message, e));
                    return;
                }

                du.serviceAction(request, response, filterConfig.getServletContext(), mapping);
            }
        } finally {
            // always clean up the thread request, even if an action hasn't been executed
            ActionContext.setContext(null);
        }
    }
}

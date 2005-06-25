/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.util.ClassLoaderUtil;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapperFactory;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.interceptor.component.ComponentConfiguration;
import com.opensymphony.xwork.interceptor.component.ComponentManager;
import com.opensymphony.xwork.interceptor.component.DefaultComponentManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Patrick Lightbody
 */
public class FilterDispatcher implements Filter, WebWorkStatics {
    private static final Log LOG = LogFactory.getLog(FilterDispatcher.class);

    protected FilterConfig filterConfig;
    protected String[] pathPrefixes;

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.pathPrefixes = parse(filterConfig.getInitParameter("packages") + " com.opensymphony.webwork.static template");
        DispatcherUtils.initialize(filterConfig.getServletContext());
    }

    protected String[] parse(String packages) {
        if (packages == null) {
            return null;
        }
        List pathPrefixes = new ArrayList();

        StringTokenizer st = new StringTokenizer(packages, ", \n\t");
        while (st.hasMoreTokens()) {
            String pathPrefix = st.nextToken().replace('.', '/');
            if (!pathPrefix.endsWith("/")) {
                pathPrefix += "/";
            }
            pathPrefixes.add(pathPrefix);
        }

        return (String[]) pathPrefixes.toArray(new String[pathPrefixes.size()]);
    }


    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        ActionMapping mapping = ActionMapperFactory.getMapper().getMapping(request);
        ComponentManager componentManager = null;

        try {
            componentManager = setupContainer(request);

            if (mapping == null) {
                // there is no action in this request, should we look for a static resource?
                if (request.getServletPath().startsWith("/webwork")) {
                    String name = request.getServletPath().substring("/webwork".length());
                    findStaticResource(name, response);
                } else {
                    // this is a normal request, let it pass through
                    chain.doFilter(request, response);
                }
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
            // tear down the component manager if it was created
            if (componentManager != null) {
                componentManager.dispose();
            }

            // always clean up the thread request, even if an action hasn't been executed
            ActionContext.setContext(null);
        }
    }

    protected ComponentManager setupContainer(HttpServletRequest request) {
        ComponentManager container = createComponentManager();
        HttpSession session = ((HttpServletRequest) request).getSession(true);
        ComponentManager fallback = (ComponentManager) session.getAttribute(ComponentManager.COMPONENT_MANAGER_KEY);
        container.setFallback(fallback);
        ComponentConfiguration config = (ComponentConfiguration) getServletContext(session).getAttribute("ComponentConfiguration");
        config.configure(container, "request");
        request.setAttribute(ComponentManager.COMPONENT_MANAGER_KEY, container);

        return container;
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

    protected void tearDownContainer() {

    }

    protected void findStaticResource(String name, HttpServletResponse response) throws IOException {
        for (int i = 0; i < pathPrefixes.length; i++) {
            InputStream is = findInputStream(name, pathPrefixes[i]);
            if (is != null) {
                try {
                    copy(is, response.getOutputStream());
                } finally {
                    is.close();
                }
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    protected void copy(InputStream input, OutputStream output) throws IOException {
        final byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

    protected InputStream findInputStream(String name, String packagePrefix) throws IOException {
        String resourcePath = packagePrefix + name;

        // TODO allow the enc type to be configured
        resourcePath = URLDecoder.decode(resourcePath);

        return ClassLoaderUtil.getResourceAsStream(resourcePath, getClass());
    }

    /**
     * handle .. chars here and other URL hacks
     */
    protected boolean checkUrl(URL url, String rawResourcePath) {

        // ignore folder resources - they provide streams too ! dunno why :)
        if (url.getPath().endsWith("/")) {
            return false;
        }

        // check for parent path access
        // NOTE : most servlet containers shoudl resolve .. chars in the request url anyway
        if (url.toExternalForm().indexOf(rawResourcePath) == -1) {
            return false;
        }

        return true;
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

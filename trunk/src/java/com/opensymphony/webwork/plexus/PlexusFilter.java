package com.opensymphony.webwork.plexus;

import org.codehaus.plexus.PlexusContainer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

/**
 * @author Patrick Lightbody (plightbo at gmail dot com)
 */
public class PlexusFilter implements Filter {
    public static boolean loaded = false;

    private ServletContext ctx;

    public void init(FilterConfig filterConfig) throws ServletException {
        ctx = filterConfig.getServletContext();
        loaded = true;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpSession session = request.getSession(false);
            PlexusContainer child;
            PlexusContainer parent;
            if (session != null) {
                parent = (PlexusContainer) session.getAttribute(PlexusLifecycleListener.KEY);
            } else {
                parent = (PlexusContainer) ctx.getAttribute(PlexusLifecycleListener.KEY);
            }

            child = parent.createChildContainer("request", Collections.EMPTY_LIST, Collections.EMPTY_MAP);
            PlexusUtils.configure(child, "plexus-request.xml");
            child.initialize();
            child.start();
            PlexusThreadLocal.setPlexusContainer(child);
            chain.doFilter(req, res);
            child.dispose();
            PlexusThreadLocal.setPlexusContainer(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}

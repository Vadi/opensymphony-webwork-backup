package com.opensymphony.webwork.dispatcher;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.interceptor.component.ComponentManager;

import javax.servlet.*;
import java.io.IOException;

/**
 * Special filter designed to work with the {@link FilterDispatcher} and allow
 * for easier integration with SiteMesh. Normally, ordering your filters to have
 * SiteMesh go first, and then {@link FilterDispatcher} go second is perfectly fine.
 * However, sometimes you may wish to access WebWork-features, including the
 * value stack, from within your SiteMesh decorators. Because {@link FilterDispatcher}
 * cleans up the {@link ActionContext}, your decorator won't have access to the
 * date you want.
 * <p/>
 * <p/>
 * By adding this filter, the {@link FilterDispatcher} will know to not clean up and
 * instead defer cleanup to this filter. The ordering of the filters should then be:
 * <p/>
 * <ul>
 * <li>this filter</li>
 * <li>SiteMesh filter</li>
 * <li>{@link FilterDispatcher}</li>
 * </ul>
 *
 * @author Patrick Lightbody
 * @see FilterDispatcher
 * @since 2.2
 */
public class ActionContextCleanUp implements Filter {
    private static final String CLEANUP_PRESENT = "__cleanup_present";

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            req.setAttribute(CLEANUP_PRESENT, Boolean.TRUE);
            chain.doFilter(req, res);
        } finally {
            req.setAttribute(CLEANUP_PRESENT, Boolean.FALSE);
            cleanUp(req);
        }
    }

    protected static void cleanUp(ServletRequest req) {
        // should we clean up yet?
        Boolean dontClean = (Boolean) req.getAttribute(CLEANUP_PRESENT);
        if (dontClean != null && dontClean.booleanValue()) {
            return;
        }

        // tear down the component manager if it was created
        ComponentManager componentManager = (ComponentManager) req.getAttribute(ComponentManager.COMPONENT_MANAGER_KEY);
        if (componentManager != null) {
            componentManager.dispose();
        }

        // always dontClean up the thread request, even if an action hasn't been executed
        ActionContext.setContext(null);
    }

    public void destroy() {
    }
}

package com.opensymphony.webwork.lifecycle;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.opensymphony.webwork.config.ServletContextSingleton;

/**
 * @author Scott N. Smith scottnelsonsmith@yahoo.com
 * @version $Id$
 */
public class RequestLifecycleFilterCompatWeblogic61
    extends RequestLifecycleFilter
    implements Filter
{

    /**
     * dummy setter for {@link #filterConfig}; this method
     * sets up the {@link ServletContextSingleton} with
     * the servlet context from the filter configuration.
     * <p>
     * This is needed by Weblogic Server 6.1 because it
     * uses a slightly obsolete Servlet 2.3-minus spec
     * whose {@link Filter} interface requires this method.
     * <p>
     * 
     * @param filterConfig the filter configuration.
     */
    public void setFilterConfig(FilterConfig filterConfig)
    {
        ServletContextSingleton singleton =
            ServletContextSingleton.getInstance();
        singleton.setServletContext(filterConfig.getServletContext());
    }
    
    /**
     * answers the filter configuration.
     * 
     * @return <code>null</code> since this filter doesn't
     *          use a filter config
     */
    public FilterConfig getFilterConfig()
    {
        return null;
    }
    
    /**
     * answers the servlet context.
     * <p>
     * Servlet 2.3 specifies that this can be retrieved from
     * the session.  Unfortunately, weblogic.jar can only retrieve
     * the servlet context from the filter config.  Hence, this
     * returns the servlet context from the singleton that was
     * setup by {@link #setFilterConfig(FilterConfig)}.
     * 
     * @param session the HTTP session.  Not used
     * @return the servlet context.
     */
    protected ServletContext getServletContext(HttpSession session)
    {
        ServletContextSingleton singleton =
            ServletContextSingleton.getInstance();
        return singleton.getServletContext();
    }

}

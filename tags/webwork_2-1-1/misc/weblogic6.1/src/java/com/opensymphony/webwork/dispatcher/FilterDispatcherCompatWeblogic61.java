package com.opensymphony.webwork.dispatcher;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * When running Weblogic Server 6.1, this class should be
 * specified in web.xml instead of {@link FilterDispatcher}.
 * <p>
 * This class properly handles the weblogic.jar handling
 * of servlet filters.  There is one serious incompatibility, and
 * that is that while {@link FilterConfig#init(FilterConfig)}
 * throws a {@link ServletException}, this class's method
 * {@link #setFilterConfig(FilterConfig)} does not throw
 * the exception.  Since {@link #setFilterConfig(FilterConfig)}
 * invokes {@link FilterConfig#init(FilterConfig)}, the setter
 * must "swallow" the exception.  This it does by logging the
 * exception as an error.
 * 
 * @author Scott N. Smith scottnelsonsmith@yahoo.com
 * @version $Id$
 */
public class FilterDispatcherCompatWeblogic61
    extends FilterDispatcher
    implements Filter
{
    /** the standard logger */
    private static Log log =
        LogFactory.getLog(FilterDispatcherCompatWeblogic61.class);

    /**
     * This method is required by Weblogic 6.1 SP4 because
     * they defined this as a required method just before
     * the Servlet 2.3 specification was finalized.
     *
     * @return the filter's filter configuration
     */
    public FilterConfig getFilterConfig()
    {
        return super.getFilterConfig();
    }

    /**
     * This method is required by Weblogic 6.1 SP4
     * instead of {@link init(FilterConfig)} because
     * they defined this as a required method just before
     * the Servlet 2.3 specification was finalized.
     *
     * @param filterConfig the filter configuration for this filter
     */
    public final void setFilterConfig(FilterConfig filterConfig)
    {
        try
        {
            init(filterConfig);
        }
        catch (ServletException se)
        {
            log.error(
                "Couldn't set the filter configuration in this filter",
                se);
        }
    }

}

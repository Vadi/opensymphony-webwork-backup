package com.opensymphony.webwork.util;

import javax.servlet.ServletContext;

/**
 * For components that have a dependence on the Servlet context.
 */
public interface ServletContextAware
{
    public void setServletContext(ServletContext context);
}

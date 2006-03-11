package com.opensymphony.webwork.util;

import javax.servlet.ServletContext;

/**
 * Used to pass ServletContext init parameters to various
 * frameworks such as Spring, Plexus and Portlet.
 *
 * @author plightbo
 */
public interface ObjectFactoryInitializable {

    void init(ServletContext servletContext);

}

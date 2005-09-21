package com.opensymphony.webwork.util;

import javax.servlet.ServletContext;

/**
 * User: plightbo
 * Date: Sep 20, 2005
 * Time: 6:38:34 PM
 */
public interface ObjectFactoryInitializable {
    void init(ServletContext servletContext);
}

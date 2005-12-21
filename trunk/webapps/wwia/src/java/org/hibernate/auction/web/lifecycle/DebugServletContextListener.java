/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.lifecycle;

import com.opensymphony.xwork.util.LocalizedTextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * DebugServletContextListener sets debug settings at startup
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class DebugServletContextListener implements ServletContextListener {
    private static final Log LOG = LogFactory.getLog(DebugServletContextListener.class);

    public void contextInitialized(ServletContextEvent event) {
        LocalizedTextUtil.setReloadBundles(true);
    }

    public void contextDestroyed(ServletContextEvent event) {

    }
}

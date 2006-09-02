/*
 *  Copyright (c) 2002-2006 by OpenSymphony
 *  All rights reserved.
 */
package com.opensymphony.webwork.quickstart;

import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;

import java.util.List;
import java.util.Map;
import java.io.File;

/**
 * To start a Jetty server used by the QuickStart application.
 */
public class JettyServer {
    /**
     * The system property name used to specify a directory of webapps.
     */
    public static final String WEBAPPS_DIR_PROPERTY = "webapps.dir";

    public static void startServer(int port, String context, List pathPriority, Map paths, String resolver) throws Exception {
        try {
            Server server = new Server();
            SocketListener socketListener = new SocketListener();
            socketListener.setPort(port);
            server.addListener(socketListener);

            WebApplicationContext ctx;
            if (resolver == null) {
                ctx = new MultiWebApplicationContext(pathPriority, paths);
            } else {
                ctx = new MultiWebApplicationContext(pathPriority, paths, resolver);
            }
            ctx.setClassLoader(Thread.currentThread().getContextClassLoader());
            ctx.setContextPath(context);
            server.addContext(null, ctx);

            // Add in extra webapps dir (see WW-1319)
            String webappsDir = System.getProperty(WEBAPPS_DIR_PROPERTY);
            if (webappsDir != null && new File(webappsDir).exists()) {
                server.addWebApplications(webappsDir);
            }

            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

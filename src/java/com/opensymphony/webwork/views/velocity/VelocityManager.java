/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.jsp.ui.OgnlTool;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * @author Matt Ho <matt@indigoegg.com>
 */
public class VelocityManager {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(VelocityManager.class);
    private static VelocityEngine velocityEngine;
    private static OgnlTool ognlTool = OgnlTool.getInstance();
    public static final String REQUEST = "req";
    public static final String RESPONSE = "res";
    public static final String STACK = "stack";
    public static final String OGNL = "ognl";
    public static final String UI = "ui";
    public static final String ACTION = "action";

    // the parent JSP tag
    public static final String PARENT = "parent";

    // the current JSP tag
    public static final String TAG = "tag";

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
    * return  a reference to the VelocityEngine used by <b>all</b> webwork velocity thingies with the exception of
    * directly accessed *.vm pages
    * @return
    */
    public static VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    /**
    * This method is responsible for creating the standard VelocityContext used by all WW2 velocity views.  The
    * following context parameters are defined:
    *
    * <ul>
    *   <li><strong>req</strong> - the current HttpServletRequest</li>
    *   <li><strong>res</strong> - the current HttpServletResponse</li>
    *   <li><strong>stack</strong> - the current OgnlValueStack</li>
    *   <li><strong>ui</strong> - a singleton JSPTagAdapter that provides access to the WW2 UI Tags</li>
    * </ul>
    *
    * @return a new WebWorkVelocityContext
    */
    public static Context createContext(OgnlValueStack stack, ServletRequest servletRequest, ServletResponse servletResponse) {
        WebWorkVelocityContext context = new WebWorkVelocityContext(stack);
        context.put(REQUEST, servletRequest);
        context.put(RESPONSE, servletResponse);
        context.put(STACK, stack);
        context.put(OGNL, ognlTool);

        ActionInvocation invocation = (ActionInvocation) stack.getContext().get(ActionContext.ACTION_INVOCATION);

        if (invocation != null) {
            context.put(ACTION, invocation.getAction());
        }

        return context;
    }

    /**
    * initializes the VelocityManager.  this should be called during the initialization process, say by
    * ServletDispatcher.  this may be called multiple times safely although calls beyond the first won't do anything
    * @param context the current servlet context
    */
    public synchronized static void init(ServletContext context) {
        if (velocityEngine == null) {
            velocityEngine = newVelocityEngine(context);
        }
    }

    /**
    * load optional velocity properties using the following loading strategy
    * <ul>
    *  <li>relative to the servlet context path</li>
    *  <li>relative to the WEB-INF directory</li>
    *  <li>on the classpath</li>
    * </ul>
    * @param context the current ServletContext.  may <b>not</b> be null
    * @return the optional properties if webwork.velocity.configfile was specified, an empty Properties file otherwise
    */
    public static Properties loadConfiguration(ServletContext context) {
        if (context == null) {
            String gripe = "Error attempting to create a loadConfiguration from a null ServletContext!";
            log.error(gripe);
            throw new IllegalArgumentException(gripe);
        }

        Properties properties = new Properties();

        /**
        * if the user has specified an external velocity configuration file, we'll want to search for it in the
        * following order
        *
        * 1. relative to the context path
        * 2. relative to /WEB-INF
        * 3. in the class path
        */
        String configfile;

        if (Configuration.isSet("webwork.velocity.configfile")) {
            configfile = Configuration.getString("webwork.velocity.configfile");
            log.info("Initializing velocity using '" + configfile + "'");
        } else {
            configfile = "velocity.properties";
            log.info("Initializing velocity using '" + configfile + "'");
        }

        configfile = configfile.trim();

        InputStream in = null;

        try {
            if (context.getRealPath(configfile) != null) {
                // 1. relative to context path, i.e. /velocity.properties
                String filename = context.getRealPath(configfile);

                if (filename != null) {
                    File file = new File(filename);

                    if (file.isFile()) {
                        in = new FileInputStream(file);
                    }

                    // 2. if nothing was found relative to the context path, search relative to the WEB-INF directory
                    if (in == null) {
                        file = new File(context.getRealPath("/WEB-INF/" + configfile));

                        if (file.isFile()) {
                            in = new FileInputStream(file);
                        }
                    }
                }
            }

            // 3. finally, if there's no physical file, how about something in our classpath
            if (in == null) {
                in = VelocityManager.class.getClassLoader().getResourceAsStream(configfile);
            }

            // if we've got something, load 'er up
            if (in != null) {
                properties.load(in);
            }
        } catch (IOException e) {
            log.warn("unable to load velocity configuration file, '" + configfile + "'", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        // now apply our systemic defaults
        applyDefaultConfiguration(context, properties);

        // for debugging purposes, allows users to dump out the properties that have been configured
        if (log.isDebugEnabled()) {
            log.debug("Initializing Velocity with the following properties ...");

            for (Iterator iter = properties.keySet().iterator();
                    iter.hasNext();) {
                String key = (String) iter.next();
                String value = properties.getProperty(key);

                if (log.isDebugEnabled()) {
                    log.debug("    '" + key + "' = '" + value + "'");
                }
            }
        }

        return properties;
    }

    /**
    * <p>
    * instantiate a new VelocityEngine
    * </p>
    * <p>
    * the following is the default Velocity configuration
    * </p>
    * <pre>
    *  resource.loader = file, class
    *  file.resource.loader.path = real path of webapp
    *  class.resource.loader.description = Velocity Classpath Resource Loader
    *  class.resource.loader.class = com.opensymphony.webwork.views.velocity.WebWorkResourceLoader
    * </pre>
    * <p>
    * this default configuration can be overridden by specifying a webwork.velocity.configfile property in the
    * webwork.properties file.  the specified config file will be searched for in the following order:
    * </p>
    * <ul>
    *  <li>relative to the servlet context path</li>
    *  <li>relative to the WEB-INF directory</li>
    *  <li>on the classpath</li>
    * </ul>
    * @param context the current ServletContext.  may <b>not</b> be null
    */
    protected static VelocityEngine newVelocityEngine(ServletContext context) {
        if (context == null) {
            String gripe = "Error attempting to create a new VelocityEngine from a null ServletContext!";
            log.error(gripe);
            throw new IllegalArgumentException(gripe);
        }

        Properties p = loadConfiguration(context);

        VelocityEngine velocityEngine = new VelocityEngine();

        try {
            velocityEngine.init(p);
        } catch (Exception e) {
            String gripe = "Unable to instantiate VelocityEngine!";
            log.error(gripe, e);
            throw new RuntimeException(gripe);
        }

        return velocityEngine;
    }

    /**
    * once we've loaded up the user defined configurations, we will want to apply WebWork specification configurations.
    * <ul>
    *   <li>if Velocity.RESOURCE_LOADER has not been defined, then we will use the defaults which is a joined file,
    *       class loader for unpackaed wars and a straight class loader otherwise</li>
    *   <li>we need to define the various WebWork custom user directives such as #param, #tag, and #bodytag</li>
    * </ul>
    *
    * @param context
    * @param p
    */
    private static void applyDefaultConfiguration(ServletContext context, Properties p) {
        /**
        * Load a default resource loader definition if there isn't one present.
        * Ben Hall (22/08/2003)
        */
        if (p.getProperty(Velocity.RESOURCE_LOADER) == null) {
            p.setProperty(Velocity.RESOURCE_LOADER, "wwfile, wwclass");
        }

        /**
        * If there's a "real" path add it for the wwfile resource loader.
        * If there's no real path and they haven't configured a loader then we change
        * resource loader property to just use the wwclass loader
        * Ben Hall (22/08/2003)
        */
        if (context.getRealPath("") != null) {
            p.setProperty("wwfile.resource.loader.description", "Velocity File Resource Loader");
            p.setProperty("wwfile.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            p.setProperty("wwfile.resource.loader.path", context.getRealPath(""));
        } else if (p.getProperty(Velocity.RESOURCE_LOADER) == null) {
            p.setProperty(Velocity.RESOURCE_LOADER, "wwclass");
        }

        /**
        * Refactored the Velocity templates for the WebWork taglib into the classpath from the web path.  This will
        * enable WebWork projects to have access to the templates by simply including the WebWork jar file.
        * Unfortunately, there does not appear to be a macro for the class loader keywords
        * Matt Ho - Mon Mar 17 00:21:46 PST 2003
        */
        p.setProperty("wwclass.resource.loader.description", "Velocity Classpath Resource Loader");
        p.setProperty("wwclass.resource.loader.class", "com.opensymphony.webwork.views.velocity.WebWorkResourceLoader");

        /**
        * the TagDirective and BodyTagDirective must be added to the userdirective
        * TODO: there must be a better place for this
        */
        String userdirective = p.getProperty("userdirective");
        String directives = "com.opensymphony.webwork.views.velocity.ParamDirective" + "," + "com.opensymphony.webwork.views.velocity.TagDirective" + "," + "com.opensymphony.webwork.views.velocity.BodyTagDirective";

        if ((userdirective == null) || userdirective.trim().equals("")) {
            userdirective = directives;
        } else {
            userdirective = directives + "," + userdirective.trim();
        }

        p.setProperty("userdirective", userdirective);
    }
}

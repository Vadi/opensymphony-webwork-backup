/*
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu</a>
 * @since 2005-12-12
 *
 */
package com.opensymphony.webwork.portlet.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu</a>
 * @since 2005-12-12
 */
public class PortalContainer {


    public static final int UNKNOWN   		= 0;
    public static final int JETSPEED_PORTAL   = 1;
    public static final int IBM_PORTAL     	= 2;
    public static final int LIFERAY_PORTAL    = 3;
    public static final int JBOSS_PORTAL  	= 4;

    private static int result = -1;

    /**
     * A map containing classes that can be searched for,
     * and which container they are typically found in.
     */
    private static Map classMappings = null;

    static {
        // initialize the classes that can be searched for
        classMappings = new HashMap(6);
        classMappings.put("org.apache.jetspeed.container.JetspeedContainerServlet",  new Integer(JETSPEED_PORTAL));
        classMappings.put("com.ibm.wps.Copyright",                            		 new Integer(IBM_PORTAL));
        classMappings.put("com.liferay.portal.servlet.MainServlet",                  new Integer(LIFERAY_PORTAL));
        classMappings.put("org.jboss.portal.core.model.portal.PortalObjectContainer",new Integer(JBOSS_PORTAL));
    }

    /** Get the current container. */
    public static int get() {
        if (result == -1) {
            final String classMatch = searchForClosestClass(classMappings);

            if (classMatch == null) {
                result = UNKNOWN;
            }
            else {
                result = ((Integer) classMappings.get(classMatch)).intValue();
            }
        }
        return result;
    }

    /**
     * Walk up the classloader hierachy and attempt to find a class in the classMappings Map
     * that can be loaded.
     *
     * @return Name of the match class, or null if not found.
     */
    private static String searchForClosestClass(Map classMappings) {
        // get closest classloader
        ClassLoader loader = PortalContainer.class.getClassLoader();

        // iterate up through the classloader hierachy (through parents), until no more left.
        while (loader != null) {

            for (Iterator iterator = classMappings.keySet().iterator(); iterator.hasNext();) {
                String className = (String) iterator.next();

                try {
                    // attempt to load current classname with current classloader
                    loader.loadClass(className);
                    // if no exception has been thrown, we're in luck.
                    return className;
                }
                catch (ClassNotFoundException e) {
                    // no problem... we'll keep trying...
                }
            }
            loader = loader.getParent();
        }

        // couldn't find anything
        return null;
    }


}

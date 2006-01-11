package com.opensymphony.webwork.portlet.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

public class ClassLoaderUtils {

    public ClassLoaderUtils() {
    }

    public static Class loadClass(String className, Class callingClass) throws ClassNotFoundException {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException ex) {

                try {
                    return (com.opensymphony.webwork.portlet.util.ClassLoaderUtils.class).getClassLoader().loadClass(className);
                } catch (ClassNotFoundException exc) {
                    try {
                        return callingClass.getClassLoader().loadClass(className);
                    } catch (ClassNotFoundException exce) {
                        throw exce;
                    }
                }
            }
        }

    }

    public static URL getResource(String resourceName, Class callingClass) {
        URL url = null;
        url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null)
            url = (com.opensymphony.webwork.portlet.util.ClassLoaderUtils.class).getClassLoader().getResource(resourceName);
        if (url == null)
            url = callingClass.getClassLoader().getResource(resourceName);
        return url;
    }

    public static Enumeration getResources(String resourceName, Class callingClass) throws IOException {
        Enumeration urls = Thread.currentThread().getContextClassLoader().getResources(resourceName);
        if (urls == null) {
            urls = (com.opensymphony.webwork.portlet.util.ClassLoaderUtils.class).getClassLoader().getResources(resourceName);
            if (urls == null)
                urls = callingClass.getClassLoader().getResources(resourceName);
        }
        return urls;
    }

    public static InputStream getResourceAsStream(String resourceName, Class callingClass) {
        try {
            URL url = getResource(resourceName, callingClass);
            return url == null ? null : url.openStream();
        } catch (IOException e) {
            return null;
        }
    }

    public static void printClassLoader() {
        System.out.println("ClassLoaderUtils.printClassLoader");
        printClassLoader(Thread.currentThread().getContextClassLoader());
    }

    public static void printClassLoader(ClassLoader cl) {
        System.out.println("ClassLoaderUtils.printClassLoader(cl = " + cl + ")");
        if (cl != null)
            printClassLoader(cl.getParent());
    }
}
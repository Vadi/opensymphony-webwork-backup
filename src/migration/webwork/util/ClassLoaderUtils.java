package webwork.util;

import java.io.InputStream;
import java.net.URL;


public class ClassLoaderUtils extends com.opensymphony.webwork.util.ClassLoaderUtils {
    public static Class loadClass(String className, Class callingClass) throws ClassNotFoundException {
        return com.opensymphony.webwork.util.ClassLoaderUtils.loadClass(className, callingClass);
    }

    public static URL getResource(String resourceName, Class callingClass) {
        return com.opensymphony.webwork.util.ClassLoaderUtils.getResource(resourceName, callingClass);
    }

    public static InputStream getResourceAsStream(String resourceName, Class callingClass) {
        return com.opensymphony.webwork.util..getResourceAsStream(resourceName, callingClass);
    }

    public static void printClassLoader() {
        com.opensymphony.webwork.util.ClassLoaderUtils.printClassLoader();
    }

    public static void printClassLoader(ClassLoader cl) {
        com.opensymphony.webwork.util.ClassLoaderUtils.printClassLoader(cl);
    }
}

package com.opensymphony.webwork;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * User: plightbo
 * Date: Aug 9, 2005
 * Time: 12:22:06 AM
 */
public class Main {
    public static void main(String[] args) {
        ArrayList urls = new ArrayList();
        try {
            findJars(new File("lib"), urls);
            urls.add(new File("webwork-2.2.jar").toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Could not find URLs -- see stack trace.");
        }

        String command = args[0];
        if ("prototype".equals(command)) {
            URLClassLoader cl = new URLClassLoader((URL[]) urls.toArray(new URL[urls.size()]),
                    Main.class.getClassLoader());
            Thread.currentThread().setContextClassLoader(cl);
            try {
                Class clazz = cl.loadClass("com.opensymphony.webwork.Prototype");
                Method main = clazz.getDeclaredMethod("main", new Class[]{String[].class});
                main.invoke(null, new Object[]{new String[0]});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void findJars(File file, ArrayList urls) throws MalformedURLException {
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                findJars(f, urls);
            } else if (f.getName().endsWith(".jar")) {
                urls.add(f.toURL());
            }
        }
    }

    static class MyClassLoader extends URLClassLoader {
        private ClassLoader parent;

        public MyClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
            this.parent = parent;
        }

        public Class loadClass(String name) throws ClassNotFoundException {
            Class aClass = null;
            try {
                aClass = findClass(name);
            } catch (ClassNotFoundException e) {
            }

            if (aClass != null) {
                return aClass;
            } else {
                return super.loadClass(name);
            }
        }

        public URL getResource(String name) {
            URL url = findResource(name);
            if (url == null && parent != null) {
                url = super.getResource(name);
            }

            return url;
        }

    }
}

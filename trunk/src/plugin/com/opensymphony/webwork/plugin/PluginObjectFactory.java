package com.opensymphony.webwork.plugin;

import com.opensymphony.xwork.ObjectFactory;

import java.net.URLClassLoader;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.File;

public class PluginObjectFactory extends ObjectFactory {
    String base;

    public PluginObjectFactory(String base) {
        this.base = base;
    }

    public Class getClassInstance(String className) throws ClassNotFoundException {
        try {
            URLClassLoader cl = new URLClassLoader(new URL[] { new File(base).toURL() }, getClass().getClassLoader());
            return cl.loadClass(className);
        } catch (Exception e) {
            // try the super class
            return super.getClassInstance(className);
        }
    }
}

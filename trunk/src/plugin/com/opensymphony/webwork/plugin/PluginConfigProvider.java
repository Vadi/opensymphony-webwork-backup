package com.opensymphony.webwork.plugin;

import com.opensymphony.xwork.config.providers.XmlConfigurationProvider;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PluginConfigProvider extends XmlConfigurationProvider {
    String base;

    public PluginConfigProvider(String base) {
        this.base = base;
    }

    protected InputStream getInputStream(String fileName) {
        try {
            return new FileInputStream(base + "/" + fileName);
        } catch (FileNotFoundException e) {
            // try the classpath
            return super.getInputStream(fileName);
        }
    }
}

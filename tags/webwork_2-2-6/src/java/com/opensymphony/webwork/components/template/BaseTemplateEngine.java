/*
 *  Copyright (c) 2002-2006 by OpenSymphony
 *  All rights reserved.
 */
package com.opensymphony.webwork.components.template;

import com.opensymphony.util.ClassLoaderUtil;
import com.opensymphony.webwork.views.JspSupportServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Base class for template engines.
 *
 * @author jcarreira
 * @author tm_jee
 */
public abstract class BaseTemplateEngine implements TemplateEngine {
	
    private static final Log LOG = LogFactory.getLog(BaseTemplateEngine.class);
    
    /** The default theme properties file name. Default is 'theme.properties' */
    public static final String DEFAULT_THEME_PROPERTIES_FILE_NAME = "theme.properties";

    final Map themeProps = new HashMap();

    public Map getThemeProps(Template template) {
        synchronized (themeProps) {
            Properties props = (Properties) themeProps.get(template.getTheme());
            if (props == null) {
                String propName = template.getDir() + "/" + template.getTheme() + "/"+getThemePropertiesFileName();
                
                // WW-1292
                // let's try getting it from the filesystem
                File propFile = new File(propName);
                InputStream is = null;
                try {
                	if (propFile.exists()) {
                		is = new FileInputStream(propFile);
                	}
                }
                catch(FileNotFoundException e) {
                	LOG.warn("Unable to find file in filesystem ["+propFile.getAbsolutePath()+"]");
                }
                
                if (is == null) {
                	// let's try webapp's context
                	if (JspSupportServlet.jspSupportServlet != null) {
                		String _propName = propName.trim();
                		if (!_propName.startsWith("/")) {
                			_propName="/"+_propName;
                		}
                		is = JspSupportServlet.jspSupportServlet.getServletContext().getResourceAsStream(_propName);
                	}
                }
                
                if (is == null) {
                	// if its not in filesystem. let's try the classpath
                	is = ClassLoaderUtil.getResourceAsStream(propName, getClass());
                }
                
                
                
                props = new Properties();
                
                if (is != null) {
                    try {
                        props.load(is);
                    } catch (IOException e) {
                        LOG.error("Could not load " + propName, e);
                    }
                }
                
                themeProps.put(template.getTheme(), props);
            }

            return props;
        }
    }

    protected String getFinalTemplateName(Template template) {
        String t = template.toString();
        if (t.indexOf(".") <= 0) {
            return t + "." + getSuffix();
        }

        return t;
    }
    
    protected String getThemePropertiesFileName() {
    	return DEFAULT_THEME_PROPERTIES_FILE_NAME;
    }

    protected abstract String getSuffix();
}

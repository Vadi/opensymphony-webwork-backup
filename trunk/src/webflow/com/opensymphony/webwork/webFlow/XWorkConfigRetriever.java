/*
 * Created on Aug 14, 2004 by mgreer
 */
package com.opensymphony.webwork.webFlow;

import com.opensymphony.webwork.webFlow.collectors.ArbitraryXMLConfigurationProvider;
import com.opensymphony.webwork.webFlow.entities.View;
import com.opensymphony.webwork.webFlow.entities.XworkView;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.ConfigurationProvider;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.ResultConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Initializes and retrieves XWork config elements
 */
public class XWorkConfigRetriever {

    private static final Log LOG = LogFactory.getLog(XWorkConfigRetriever.class);
    private static String basePath = "";
    private static boolean isXWorkStarted = false;
    private static Map viewCache = new HashMap();

    /**
     * Returns a Map of all action names/configs
     *
     * @return
     */
    public static Map getActionConfigs() {
        if (!isXWorkStarted)
            initXWork();
        return ConfigurationManager.getConfiguration().getRuntimeConfiguration().getActionConfigs();
    }

    private static void initXWork() {
        String configFilePath = basePath + "WEB-INF/classes/xwork.xml";
        File configFile = new File(configFilePath);
        try {
            ConfigurationProvider configProvider = new ArbitraryXMLConfigurationProvider(configFile.getCanonicalPath());
            ConfigurationManager.addConfigurationProvider(configProvider);
            isXWorkStarted = true;
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
    }

    public static Set getNamespaces() {
        Set namespaces = Collections.EMPTY_SET;
        Map allActionConfigs = getActionConfigs();
        if (allActionConfigs != null) {
            namespaces = allActionConfigs.keySet();
        }
        return namespaces;
    }

    /**
     * Return a Map of the action names for this namespace
     *
     * @param namespace
     * @return
     */
    public static Set getActionNames(String namespace) {
        Set actionNames = Collections.EMPTY_SET;
        Map allActionConfigs = getActionConfigs();
        if (allActionConfigs != null) {
            Map actionMappings = (Map) allActionConfigs.get(namespace);
            if (actionMappings != null) {
                actionNames = actionMappings.keySet();
            }
        }
        return actionNames;
    }

    /**
     * Returns the ActionConfig for this action name at this namespace
     *
     * @param namespace
     * @param actionName
     * @return
     */
    public static ActionConfig getActionConfig(String namespace, String actionName) {
        ActionConfig config = null;
        Map allActionConfigs = getActionConfigs();
        if (allActionConfigs != null) {
            Map actionMappings = (Map) allActionConfigs.get(namespace);
            if (actionMappings != null) {
                config = (ActionConfig) actionMappings.get(actionName);
            }
        }
        return config;
    }

    public static ResultConfig getResultConfig(String namespace, String actionName,
                                               String resultName) {
        ResultConfig result = null;
        ActionConfig actionConfig = getActionConfig(namespace, actionName);
        if (actionConfig != null) {
            Map resultMap = actionConfig.getResults();
            result = (ResultConfig) resultMap.get(resultName);
        }
        return result;
    }

    public static File getViewFile(String namespace, String actionName, String resultName) {
        File viewFile = null;
        ResultConfig result = getResultConfig(namespace, actionName, resultName);
        if (result != null) {
            String location = (String) result.getParams().get("location");
            //TODO make sure to follow chaining and redirection to other
            // actions
            if (location != null && !location.matches(".*action.*")) {
                StringBuffer filePath = new StringBuffer(basePath);
                if (!location.startsWith("/"))
                    filePath.append(namespace + "/");
                filePath.append(location);
                viewFile = new File(filePath.toString());
            }
        }
        return viewFile;
    }

    public static View getView(String namespace, String actionName, String resultName) {
        String viewId = namespace + "/" + actionName + "/" + resultName;
        View view = (View) viewCache.get(viewId);
        if (view == null) {
            File viewFile = XWorkConfigRetriever.getViewFile(namespace, actionName,
                    resultName);
            if (viewFile != null && viewFile.exists()) {
                view = new XworkView(viewFile);
                viewCache.put(viewId, view);
            }
        }
        return view;
    }

    public static void setBasePath(final String newBasePath) {
        basePath = newBasePath;
        isXWorkStarted = false;
        viewCache = new HashMap();
    }

    public static String getBasePath() {
        return basePath;
    }
}
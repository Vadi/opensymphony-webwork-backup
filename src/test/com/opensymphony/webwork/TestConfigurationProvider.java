/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork;

import com.opensymphony.webwork.dispatcher.ServletDispatcherResult;
import com.opensymphony.webwork.interceptor.TokenInterceptor;
import com.opensymphony.webwork.interceptor.TokenSessionStoreInterceptor;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionChainResult;
import com.opensymphony.xwork.config.Configuration;
import com.opensymphony.xwork.config.ConfigurationProvider;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.PackageConfig;
import com.opensymphony.xwork.config.entities.ResultConfig;
import com.opensymphony.xwork.interceptor.ParametersInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * TestConfigurationProvider provides a simple configuration class without the need for xml files, etc. for simple testing.
 *
 * @author $author$
 */
public class TestConfigurationProvider implements ConfigurationProvider {
    //~ Static fields/initializers /////////////////////////////////////////////

    public static final String TEST_ACTION_NAME = "testAction";
    public static final String EXECUTION_COUNT_ACTION_NAME = "executionCountAction";
    public static final String TOKEN_ACTION_NAME = "tokenAction";
    public static final String TOKEN_SESSION_ACTION_NAME = "tokenSessionAction";
    public static final String TEST_NAMESPACE = "/testNamespace";
    public static final String TEST_NAMESPACE_ACTION = "testNamespaceAction";

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Allows the configuration to clean up any resources used
     */
    public void destroy() {
    }

    /**
     * Initializes the configuration object.
     */
    public void init(Configuration configurationManager) {
        PackageConfig defaultPackageConfig = new PackageConfig();

        HashMap results = new HashMap();

        HashMap successParams = new HashMap();
        successParams.put("propertyName", "executionCount");
        successParams.put("expectedValue", "1");

        ResultConfig successConfig = new ResultConfig(Action.SUCCESS, TestResult.class, successParams);

        results.put(Action.SUCCESS, successConfig);

        List interceptors = new ArrayList();

        ActionConfig executionCountActionConfig = new ActionConfig(null, ExecutionCountTestAction.class, null, results, interceptors);
        defaultPackageConfig.addActionConfig(EXECUTION_COUNT_ACTION_NAME, executionCountActionConfig);

        results = new HashMap();

        successParams = new HashMap();
        successParams.put("location", "success.jsp");

        successConfig = new ResultConfig(Action.SUCCESS, ServletDispatcherResult.class, successParams);

        results.put(Action.SUCCESS, successConfig);

        interceptors.add(new ParametersInterceptor());

        ActionConfig testActionConfig = new ActionConfig(null, TestAction.class, null, results, interceptors);
        defaultPackageConfig.addActionConfig(TEST_ACTION_NAME, testActionConfig);

        interceptors = new ArrayList();
        interceptors.add(new TokenInterceptor());

        results = new HashMap();

        ActionConfig tokenActionConfig = new ActionConfig(null, TestAction.class, null, results, interceptors);
        defaultPackageConfig.addActionConfig(TOKEN_ACTION_NAME, tokenActionConfig);

        interceptors = new ArrayList();
        interceptors.add(new TokenSessionStoreInterceptor());

        results = new HashMap();

        successParams = new HashMap();
        successParams.put("actionName", EXECUTION_COUNT_ACTION_NAME);

        successConfig = new ResultConfig(Action.SUCCESS, ActionChainResult.class, successParams);

        results.put(Action.SUCCESS, successConfig);

        ActionConfig tokenSessionActionConfig = new ActionConfig(null, TestAction.class, null, results, interceptors);
        defaultPackageConfig.addActionConfig(TOKEN_SESSION_ACTION_NAME, tokenSessionActionConfig);

        configurationManager.addPackageConfig("defaultPackage", defaultPackageConfig);

        PackageConfig namespacePackageConfig = new PackageConfig();
        namespacePackageConfig.setNamespace(TEST_NAMESPACE);
        namespacePackageConfig.addParent(defaultPackageConfig);

        ActionConfig namespaceAction = new ActionConfig(null, TestAction.class, null, null, null);
        namespacePackageConfig.addActionConfig(TEST_NAMESPACE_ACTION, namespaceAction);

        configurationManager.addPackageConfig("namespacePackage", namespacePackageConfig);
    }

    /**
     * Tells whether the ConfigurationProvider should reload its configuration
     * @return
     */
    public boolean needsReload() {
        return false;
    }
}

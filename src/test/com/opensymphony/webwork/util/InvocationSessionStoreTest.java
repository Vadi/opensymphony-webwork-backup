/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.opensymphony.webwork.TestAction;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.config.Configuration;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.ConfigurationProvider;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.PackageConfig;
import com.opensymphony.xwork.interceptor.Interceptor;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * InvocationSessionStoreTest
 * @author Jason Carreira
 * Created Apr 12, 2003 10:34:53 PM
 */
public class InvocationSessionStoreTest extends TestCase {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final String ACTION_NAME = "com.opensymphony.webwork.util.InvocationSessionStoreTest.action";
    private static final String INVOCATION_KEY = "com.opensymphony.webwork.util.InvocationSessionStoreTest.invocation";
    private static final String TOKEN_VALUE = "com.opensymphony.webwork.util.InvocationSessionStoreTest.token";

    //~ Instance fields ////////////////////////////////////////////////////////

    private Map extraContext;
    private Map session;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testStore() {
        try {
            ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy("", ACTION_NAME, extraContext);
            assertEquals(Action.SUCCESS, proxy.execute());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    protected void setUp() throws Exception {
        ConfigurationManager.clearConfigurationProviders();
        ConfigurationManager.addConfigurationProvider(new InvocationSessionStoreTestConfigurationProvider());
        ConfigurationManager.getConfiguration().reload();

        session = new HashMap();
        extraContext = new HashMap();
        extraContext.put(ActionContext.SESSION, session);
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    class InvocationSessionStoreTestConfigurationProvider implements ConfigurationProvider {
        public void destroy() {
        }

        /**
        * Initializes the configuration object.
        */
        public void init(Configuration configuration) {
            PackageConfig defaultPackageConfig = new PackageConfig();

            List interceptors = new ArrayList();
            interceptors.add(new InvocationSessionStoreTestInterceptor());

            ActionConfig testActionConfig = new ActionConfig(null, TestAction.class, null, null, interceptors);
            defaultPackageConfig.addActionConfig(ACTION_NAME, testActionConfig);

            configuration.addPackageConfig("defaultPackage", defaultPackageConfig);
        }

        /**
         * Tells whether the ConfigurationProvider should reload its configuration
         * @return
         */
        public boolean needsReload() {
            return false;
        }
    }

    class InvocationSessionStoreTestInterceptor implements Interceptor {
        public void destroy() {
        }

        public void init() {
        }

        public String intercept(ActionInvocation invocation) throws Exception {
            assertNull(InvocationSessionStore.loadInvocation(INVOCATION_KEY, TOKEN_VALUE));

            ActionContext context = ActionContext.getContext();
            InvocationSessionStore.storeInvocation(INVOCATION_KEY, TOKEN_VALUE, invocation);

            ActionContext newContext = new ActionContext(extraContext);
            ActionContext.setContext(newContext);
            Assert.assertEquals(newContext, ActionContext.getContext());
            Assert.assertEquals(invocation, InvocationSessionStore.loadInvocation(INVOCATION_KEY, TOKEN_VALUE));
            Assert.assertEquals(context, ActionContext.getContext());

            return Action.SUCCESS;
        }
    }
}

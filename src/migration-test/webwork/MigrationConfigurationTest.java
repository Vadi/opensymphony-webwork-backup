/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package webwork;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.entities.ActionConfig;

import junit.framework.TestCase;

import java.util.HashMap;

import ognl.Ognl;


/**
 * MigrationConfigurationTest
 * @author Jason Carreira
 * Date: Nov 6, 2003 1:05:37 AM
 */
public class MigrationConfigurationTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    private HashMap parameters;
    private ActionContext oldContext;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testCommandMethodIsExecutedForCommandParam() throws Exception {
        parameters.put("command", new String[] {"testCommand"});

        ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy("", "migration", ActionContext.getContext().getContextMap(), false);
        assertEquals(Action.SUCCESS, proxy.execute());

        MigrationTestAction action = (MigrationTestAction) proxy.getAction();
        assertTrue(!action.isDoExecuteCalled());
        assertTrue(action.isDoTestCommandCalled());
    }

    public void testMigrationConfigurationLoadsFromViewsProperties() {
        ActionConfig config = ConfigurationManager.getConfiguration().getRuntimeConfiguration().getActionConfig("", "migration");
        assertNotNull(config);
        assertNull(config.getMethodName());
    }

    protected void setUp() throws Exception {
        super.setUp();
        ConfigurationManager.destroyConfiguration();
        ConfigurationManager.clearConfigurationProviders();

        MigrationConfiguration configuration = new MigrationConfiguration();
        ConfigurationManager.setConfiguration(configuration);
        configuration.reload();
        parameters = new HashMap();
        oldContext = ActionContext.getContext();
        ActionContext.setContext(new ActionContext(Ognl.createDefaultContext(null)));
        ActionContext.getContext().setParameters(parameters);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ConfigurationManager.destroyConfiguration();
        ConfigurationManager.setConfiguration(null);
    }
}

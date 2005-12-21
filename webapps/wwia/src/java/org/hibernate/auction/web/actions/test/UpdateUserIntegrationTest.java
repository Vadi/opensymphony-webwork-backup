/*
 * Copyright (c) 2005 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.actions.test;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.interceptor.component.ComponentInterceptor;
import com.opensymphony.xwork.interceptor.component.DefaultComponentManager;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.mockobjects.dynamic.C;
import org.hibernate.auction.dao.UserDAO;
import org.hibernate.auction.dao.UserDAOAware;
import org.hibernate.auction.web.actions.users.UpdateUser;
import org.hibernate.auction.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * UpdateUserIntegrationTest
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class UpdateUserIntegrationTest extends AbstractUpdateUserTest {

    protected void setUp() throws Exception {
        super.setUp();
        ConfigurationManager.destroyConfiguration();
        ConfigurationManager.getConfiguration().reload();

        OgnlValueStack stack = new OgnlValueStack();
        ActionContext.setContext(new ActionContext(stack.getContext()));
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ConfigurationManager.clearConfigurationProviders();
        ConfigurationManager.destroyConfiguration();
        ActionContext.setContext(null);
    }


    public void testConfiguredInterceptorStackLoadsBeforeSettingProperties() throws Exception {
        Map extraContext = new HashMap();
        DefaultComponentManager dcm = new DefaultComponentManager();
        dcm.addEnabler(UserDAO.class,UserDAOAware.class);
        dcm.registerInstance(UserDAO.class,userDAO);
        extraContext.put(ComponentInterceptor.COMPONENT_MANAGER,dcm);
        extraContext.put(ActionContext.SESSION,session);
        Map params = new HashMap();
        // these are all required
        params.put("user.firstname", "Jason");
        params.put("user.lastname", "Carreira");
        params.put("user.username", "jcarreira");
        params.put("user.password", "password");
        params.put("user.email", "jcarreira@gmail.com");
        extraContext.put(ActionContext.PARAMETERS,params);
        // let's make sure this happens
        userDaoMock.expect("makePersistent", C.args(C.isA(User.class)));
        ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy("/secure", "updateProfile", extraContext);
        assertNotNull(proxy);
        proxy.setExecuteResult(false); // don't need to try to dispatch to a JSP
        assertEquals(Action.SUCCESS,proxy.execute());
        assertSame(user,((UpdateUser)proxy.getAction()).getModel());
        assertEquals("Jason", user.getFirstname());
        assertEquals("Carreira", user.getLastname());
        assertEquals("jcarreira", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("jcarreira@gmail.com", user.getEmail().toString());
    }
}

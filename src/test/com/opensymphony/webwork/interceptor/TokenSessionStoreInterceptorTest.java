/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.TestConfigurationProvider;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;

import java.util.Map;


/**
 * TokenSessionStoreInterceptorTest
 * @author Jason Carreira
 * Created Apr 18, 2003 7:14:25 AM
 */
public class TokenSessionStoreInterceptorTest extends TokenInterceptorTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testDuplicateToken() {
        try {
            ActionProxy proxy = buildProxy(getActionName());
            String token = setToken();
            Map session = ActionContext.getContext().getSession();
            assertEquals(Action.SUCCESS, proxy.execute());
            proxy = buildProxy(getActionName());
            ActionContext.getContext().setSession(session);
            setToken(token);
            assertEquals(Action.SUCCESS, proxy.execute());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    protected String getActionName() {
        return TestConfigurationProvider.TOKEN_SESSION_ACTION_NAME;
    }
}

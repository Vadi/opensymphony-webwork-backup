/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.TestConfigurationProvider;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionProxy;


/**
 * TokenSessionStoreInterceptorTest
 * @author Jason Carreira
 * Created Apr 18, 2003 7:14:25 AM
 */
public class TokenSessionStoreInterceptorTest extends TokenInterceptorTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
* @see {@link com.opensymphony.webwork.TestConfigurationProvider#init}
*/
    public void testDuplicateToken() throws Exception {
        ActionProxy proxy = buildProxy(getActionName());
        String token = setToken(request);
        assertEquals(Action.SUCCESS, proxy.execute());
        proxy = buildProxy(getActionName());
        setToken(token);
        assertEquals(Action.SUCCESS, proxy.execute());
    }

    protected String getActionName() {
        return TestConfigurationProvider.TOKEN_SESSION_ACTION_NAME;
    }
}

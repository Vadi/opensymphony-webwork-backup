/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.opensymphony.xwork.ActionContext;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;


/**
 * TokenHelperTest
 * @author Jason Carreira
 * Created Apr 3, 2003 10:13:08 AM
 */
public class TokenHelperTest extends TestCase {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSetToken() {
        Map session = new HashMap();
        ActionContext.getContext().setSession(session);

        String token = TokenHelper.setToken();
        assertEquals(token, session.get(TokenHelper.DEFAULT_TOKEN_NAME));
    }

    public void testSetTokenWithName() {
        Map session = new HashMap();
        ActionContext.getContext().setSession(session);

        String tokenName = "myTestToken";
        String token = TokenHelper.setToken(tokenName);
        assertEquals(token, session.get(tokenName));
    }

    public void testValidToken() {
        String tokenName = "validTokenTest";
        Map session = new HashMap();
        Map params = new HashMap();
        ActionContext.getContext().setSession(session);

        String token = TokenHelper.setToken(tokenName);
        assertEquals(token, session.get(tokenName));
        params.put(TokenHelper.TOKEN_NAME_FIELD, new String[] {tokenName});
        params.put(tokenName, new String[] {token});
        ActionContext.getContext().setParameters(params);
        assertTrue(TokenHelper.validToken());
    }
}

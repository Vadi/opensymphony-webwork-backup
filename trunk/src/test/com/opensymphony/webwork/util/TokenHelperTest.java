/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import com.opensymphony.webwork.views.jsp.WebWorkMockHttpSession;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * TokenHelperTest
 * @author Jason Carreira
 * Created Apr 3, 2003 10:13:08 AM
 */
public class TokenHelperTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    private HttpServletRequest request;
    private HttpSession session;
    private Mock mockRequest;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSetToken() {
        String token = TokenHelper.setToken(request);
        assertEquals(token, session.getAttribute(TokenHelper.DEFAULT_TOKEN_NAME));
    }

    public void testSetTokenWithName() {
        String tokenName = "myTestToken";
        String token = TokenHelper.setToken(tokenName, request);
        assertEquals(token, session.getAttribute(tokenName));
    }

    public void testValidToken() {
        String tokenName = "validTokenTest";
        Map params = new HashMap();

        String token = TokenHelper.setToken(tokenName, request);
        assertEquals(token, session.getAttribute(tokenName));
        params.put(TokenHelper.TOKEN_NAME_FIELD, new String[] {tokenName});
        params.put(tokenName, new String[] {token});
        mockRequest.matchAndReturn("getParameterMap", params);
        assertTrue(TokenHelper.validToken(request));
    }

    protected void setUp() throws Exception {
        session = new WebWorkMockHttpSession();
        mockRequest = new Mock(HttpServletRequest.class);
        mockRequest.matchAndReturn("getSession", C.ANY_ARGS, session);
        request = (HttpServletRequest) mockRequest.proxy();
    }

    protected void tearDown() {
        mockRequest.verify();
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.mockobjects.servlet.MockHttpSession;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.TestConfigurationProvider;
import com.opensymphony.webwork.util.TokenHelper;
import com.opensymphony.webwork.views.jsp.WebWorkMockHttpServletRequest;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.config.ConfigurationManager;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;


/**
 * TokenInterceptorTest
 * @author Jason Carreira
 * Created Apr 9, 2003 11:42:01 PM
 */
public class TokenInterceptorTest extends TestCase {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testNoTokenInParams() {
        try {
            ActionProxy proxy = buildProxy(getActionName());
            assertEquals(TokenInterceptor.INVALID_TOKEN_CODE, proxy.execute());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testNoTokenInSession() {
        try {
            ActionProxy proxy = buildProxy(getActionName());
            setToken();
            ActionContext.getContext().getSession().clear();
            assertEquals(TokenInterceptor.INVALID_TOKEN_CODE, proxy.execute());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testTokenInterceptorSuccess() {
        try {
            ActionProxy proxy = buildProxy(getActionName());
            setToken();
            assertEquals(Action.SUCCESS, proxy.execute());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    protected String getActionName() {
        return TestConfigurationProvider.TOKEN_ACTION_NAME;
    }

    protected String setToken() {
        String token = TokenHelper.setToken();
        setToken(token);

        return token;
    }

    protected void setToken(String token) {
        ActionContext.getContext().getParameters().put(TokenHelper.TOKEN_NAME_FIELD, new String[] {
                TokenHelper.DEFAULT_TOKEN_NAME
            });
        ActionContext.getContext().getParameters().put(TokenHelper.DEFAULT_TOKEN_NAME, new String[] {
                token
            });
    }

    protected void setUp() throws Exception {
        ConfigurationManager.clearConfigurationProviders();
        ConfigurationManager.addConfigurationProvider(new TestConfigurationProvider());
        ConfigurationManager.getConfiguration().reload();
    }

    protected ActionProxy buildProxy(String actionName) throws Exception {
        Map session = new HashMap();
        Map params = new HashMap();
        Map extraContext = new HashMap();
        extraContext.put(ActionContext.SESSION, session);
        extraContext.put(ActionContext.PARAMETERS, params);

        WebWorkMockHttpServletRequest request = new WebWorkMockHttpServletRequest();
        HttpSession httpSession = new MockHttpSession();
        request.setSession(httpSession);
        extraContext.put(ServletActionContext.HTTP_REQUEST, request);

        return ActionProxyFactory.getFactory().createActionProxy("", actionName, extraContext, true);
    }
}

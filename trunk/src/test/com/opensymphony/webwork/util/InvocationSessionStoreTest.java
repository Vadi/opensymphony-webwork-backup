/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.mockobjects.dynamic.Mock;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;


/**
 * InvocationSessionStoreTest
 * @author Jason Carreira
 * Created Apr 12, 2003 10:34:53 PM
 */
public class InvocationSessionStoreTest extends TestCase {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final String INVOCATION_KEY = "com.opensymphony.webwork.util.InvocationSessionStoreTest.invocation";
    private static final String TOKEN_VALUE = "com.opensymphony.webwork.util.InvocationSessionStoreTest.token";

    //~ Instance fields ////////////////////////////////////////////////////////

    private ActionInvocation invocation;
    private Map session;
    private Mock invocationMock;
    private OgnlValueStack stack;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testStore() {
        assertNull(InvocationSessionStore.loadInvocation(INVOCATION_KEY, TOKEN_VALUE));
        InvocationSessionStore.storeInvocation(INVOCATION_KEY, TOKEN_VALUE, invocation);
        assertNotNull(InvocationSessionStore.loadInvocation(INVOCATION_KEY, TOKEN_VALUE));
        assertEquals(invocation, InvocationSessionStore.loadInvocation(INVOCATION_KEY, TOKEN_VALUE));
    }

    public void testValueStackReset() {
        ActionContext actionContext = ActionContext.getContext();
        assertEquals(stack, actionContext.getValueStack());
        InvocationSessionStore.storeInvocation(INVOCATION_KEY, TOKEN_VALUE, invocation);
        actionContext.setValueStack(null);
        assertNull(actionContext.getValueStack());
        InvocationSessionStore.loadInvocation(INVOCATION_KEY, TOKEN_VALUE);
        assertEquals(stack, actionContext.getValueStack());
    }

    protected void setUp() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        session = new HashMap();
        actionContext.setSession(session);

        invocationMock = new Mock(ActionInvocation.class);
        invocation = (ActionInvocation) invocationMock.proxy();

        stack = new OgnlValueStack();
        actionContext.setValueStack(stack);
        invocationMock.matchAndReturn("getStack", stack);

        Mock proxyMock = new Mock(ActionProxy.class);
        proxyMock.matchAndReturn("getInvocation", invocation);

        ActionProxy proxy = (ActionProxy) proxyMock.proxy();

        invocationMock.matchAndReturn("getProxy", proxy);
    }
}

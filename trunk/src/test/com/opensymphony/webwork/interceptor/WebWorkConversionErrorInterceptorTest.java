/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.mockobjects.dynamic.Mock;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;


/**
 * WebWorkConversionErrorInterceptorTest
 * @author Jason Carreira
 * Date: Nov 27, 2003 10:11:42 PM
 */
public class WebWorkConversionErrorInterceptorTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected ActionContext context;
    protected ActionInvocation invocation;
    protected Map conversionErrors;
    protected Mock mockInvocation;
    protected OgnlValueStack stack;
    protected WebWorkConversionErrorInterceptor interceptor;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testEmptyValuesDoNotSetFieldErrors() throws Exception {
        conversionErrors.put("foo", new Long(123));
        conversionErrors.put("bar", "");
        conversionErrors.put("baz", new String[] {""});

        ActionSupport action = new ActionSupport();
        stack.push(action);
        assertNull(action.getFieldErrors().get("foo"));
        assertNull(action.getFieldErrors().get("bar"));
        assertNull(action.getFieldErrors().get("baz"));
        interceptor.intercept(invocation);
        assertTrue(action.hasFieldErrors());
        assertNotNull(action.getFieldErrors().get("foo"));
        assertNull(action.getFieldErrors().get("bar"));
        assertNull(action.getFieldErrors().get("baz"));
    }

    public void testFieldErrorAdded() throws Exception {
        conversionErrors.put("foo", new Long(123));

        ActionSupport action = new ActionSupport();
        stack.push(action);
        assertNull(action.getFieldErrors().get("foo"));
        interceptor.intercept(invocation);
        assertTrue(action.hasFieldErrors());
        assertNotNull(action.getFieldErrors().get("foo"));
    }

    protected void setUp() throws Exception {
        super.setUp();
        interceptor = new WebWorkConversionErrorInterceptor();
        mockInvocation = new Mock(ActionInvocation.class);
        invocation = (ActionInvocation) mockInvocation.proxy();
        stack = new OgnlValueStack();
        context = new ActionContext(stack.getContext());
        conversionErrors = new HashMap();
        context.setConversionErrors(conversionErrors);
        mockInvocation.matchAndReturn("getInvocationContext", context);
        mockInvocation.expectAndReturn("invoke", Action.SUCCESS);
    }
}

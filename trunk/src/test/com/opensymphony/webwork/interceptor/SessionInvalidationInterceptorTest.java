/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import java.util.LinkedHashMap;

import org.easymock.MockControl;
import org.springframework.mock.web.MockHttpSession;

import com.mockobjects.servlet.MockHttpServletRequest;
import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;

import junit.framework.TestCase;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class SessionInvalidationInterceptorTest extends TestCase {

	public void testInvocationNow() throws Exception {
		MockHttpSession session = new MockHttpSession();
		final MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(session);
		
		ActionContext actionContext = new ActionContext(new LinkedHashMap() {
			private static final long serialVersionUID = 1L;
			{put(ActionContext.SESSION, new SessionMap(request));}
		});
		
		
		MockControl control = MockControl.createControl(ActionInvocation.class);
		ActionInvocation invocation = (ActionInvocation) control.getMock();
		control.expectAndDefaultReturn(invocation.getInvocationContext(), actionContext);
		control.expectAndDefaultReturn(invocation.invoke(), "success");
		control.replay();
		
		assertFalse(session.isInvalid());
		assertNull(session.getAttribute("___invalidateSession"));
		
		SessionInvalidationInterceptor interceptor = new SessionInvalidationInterceptor();
		interceptor.setType(SessionInvalidationInterceptor.NOW);
		interceptor.intercept(invocation);
		
		assertNull(session.getAttribute("___invalidateSession"));
		assertTrue(session.isInvalid());
		control.verify();
	}
	
	public void testInvocationNextRequest() throws Exception{
		MockHttpSession session = new MockHttpSession();
		final MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(session);
		
		ActionContext actionContext = new ActionContext(new LinkedHashMap() {
			private static final long serialVersionUID = 1L;
			{put(ActionContext.SESSION, new SessionMap(request));}
		});
		
		
		MockControl control = MockControl.createControl(ActionInvocation.class);
		ActionInvocation invocation = (ActionInvocation) control.getMock();
		control.expectAndDefaultReturn(invocation.getInvocationContext(), actionContext);
		control.expectAndDefaultReturn(invocation.invoke(), "success");
		control.replay();
		
		assertFalse(session.isInvalid());
		assertNull(session.getAttribute("___invalidateSession"));
		
		SessionInvalidationInterceptor interceptor = new SessionInvalidationInterceptor();
		interceptor.setType(SessionInvalidationInterceptor.NEXT_REQUEST);
		interceptor.intercept(invocation);
		
		assertNotNull(session.getAttribute("___invalidateSession"));
		assertEquals(session.getAttribute("___invalidateSession"), "true");
		assertFalse(session.isInvalid());
		
		interceptor.intercept(invocation);
		
		assertTrue(session.isInvalid());
		control.verify();
	}
	
}

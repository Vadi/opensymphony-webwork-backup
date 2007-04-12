/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import java.util.LinkedHashMap;
import java.util.Map;

import org.easymock.MockControl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.interceptor.FlashInterceptor;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionSupport;

import junit.framework.TestCase;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class FlashResultTest extends TestCase {

	public void testResult() throws Exception {
		
		InternalAction action = new InternalAction();
		
		Map sessionMap = new LinkedHashMap();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		ActionContext actionContext = ActionContext.getContext();
		actionContext.getContextMap().put(ServletActionContext.HTTP_REQUEST, request);
		actionContext.getContextMap().put(ServletActionContext.HTTP_RESPONSE, response);
		actionContext.getContextMap().put(ActionContext.SESSION, sessionMap);
		
		MockControl control = MockControl.createControl(ActionInvocation.class);
		ActionInvocation invocation = (ActionInvocation) control.getMock();
		invocation.getInvocationContext();
		control.expectAndDefaultReturn(null, actionContext);
		invocation.getAction();
		control.expectAndDefaultReturn(null, action);
		
		control.replay();
		
		FlashResult result = new FlashResult();
		result.doExecute("http://www.google.com", invocation);
		
		assertEquals(sessionMap.size(), 1);
		assertTrue(sessionMap.containsKey(FlashInterceptor.DEFAULT_KEY));
		assertEquals(sessionMap.get(FlashInterceptor.DEFAULT_KEY), action);
		
		control.verify();
	}
	
	class InternalAction extends ActionSupport {
		private static final long serialVersionUID = 2836339824246564870L;
	}
}

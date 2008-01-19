/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.easymock.MockControl;

import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionSupport;


/**
 * Test case for MessageStoreInterceptor.
 * 
 * @author tmjee
 * 
 * @version $Date$ $Id$
 */
public class MessageStoreInterceptorTest extends WebWorkTestCase {

	public void testStoreMessage() throws Exception {
		MessageStoreInterceptor interceptor = new MessageStoreInterceptor();
		interceptor.setAllowRequestParameterSwitch(true);
		interceptor.setOperationMode(MessageStoreInterceptor.STORE_MODE);
		
		
		Map paramMap = new LinkedHashMap();
		Map sessionMap = new LinkedHashMap();
		
		ActionSupport action = new ActionSupport();
		action.addActionError("some action error 1");
		action.addActionError("some action error 2");
		action.addActionMessage("some action message 1");
		action.addActionMessage("some action message 2");
		action.addFieldError("field1", "some field error 1");
		action.addFieldError("field2", "some field error 2");
		
		ActionContext actionContext = new ActionContext(new HashMap());
		actionContext.put(ActionContext.PARAMETERS, paramMap);
		actionContext.put(ActionContext.SESSION, sessionMap);
		
		// Mock (ActionInvocation)
		MockControl mockControlActionInvocation = MockControl.createControl(ActionInvocation.class);
		ActionInvocation mockActionInvocation  = (ActionInvocation) mockControlActionInvocation.getMock();
		mockControlActionInvocation.expectAndDefaultReturn(
				mockActionInvocation.getInvocationContext(), actionContext);
		
		mockControlActionInvocation.expectAndDefaultReturn(
				mockActionInvocation.invoke(), Action.SUCCESS);
		
		mockControlActionInvocation.expectAndDefaultReturn(
				mockActionInvocation.getAction(), action);
		
		mockControlActionInvocation.replay();
		
		interceptor.init();
		interceptor.intercept(mockActionInvocation);
		interceptor.destroy();
		
		assertEquals(sessionMap.size(), 3);
		assertTrue(sessionMap.containsKey(MessageStoreInterceptor.actionErrorsSessionKey));
		assertTrue(sessionMap.containsKey(MessageStoreInterceptor.actionMessagesSessionKey));
		assertTrue(sessionMap.containsKey(MessageStoreInterceptor.fieldErrorsSessionKey));
		
		List actionErrors = (List) sessionMap.get(MessageStoreInterceptor.actionErrorsSessionKey);
		List actionMessages = (List) sessionMap.get(MessageStoreInterceptor.actionMessagesSessionKey);
		Map fieldErrors = (Map) sessionMap.get(MessageStoreInterceptor.fieldErrorsSessionKey);
		
		assertEquals(actionErrors.size(), 2);
		assertEquals(actionMessages.size(), 2);
		assertEquals(fieldErrors.size(), 2);
		
		assertTrue(actionErrors.contains("some action error 1"));
		assertTrue(actionErrors.contains("some action error 2"));
		assertTrue(actionMessages.contains("some action message 1"));
		assertTrue(actionMessages.contains("some action message 2"));
		assertTrue(fieldErrors.containsKey("field1"));
		assertTrue(fieldErrors.containsKey("field2"));
		assertEquals(((List)fieldErrors.get("field1")).size(), 1);
		assertEquals(((List)fieldErrors.get("field2")).size(), 1);
		assertEquals(((List)fieldErrors.get("field1")).get(0), "some field error 1");
		assertEquals(((List)fieldErrors.get("field2")).get(0), "some field error 2");
		
		mockControlActionInvocation.verify();
	}
	
	public void testRetrieveMessage() throws Exception {
		MessageStoreInterceptor interceptor = new MessageStoreInterceptor();
		interceptor.setOperationMode(MessageStoreInterceptor.RETRIEVE_MODE);
		interceptor.setAllowRequestParameterSwitch(true);
		
		
		ActionSupport action = new ActionSupport();
		
		MockControl mockControlActionInvocation = MockControl.createControl(ActionInvocation.class);
		ActionInvocation mockActionInvocation = (ActionInvocation) mockControlActionInvocation.getMock();
		mockControlActionInvocation.expectAndDefaultReturn(mockActionInvocation.invoke(), Action.SUCCESS);
		
		
		Map paramsMap = new LinkedHashMap();
		Map sessionMap = new LinkedHashMap();
		
		List actionErrors = new ArrayList();
		List actionMessages = new ArrayList();
		Map fieldErrors = new LinkedHashMap();
		
		actionErrors.add("some action error 1");
		actionErrors.add("some action error 2");
		actionMessages.add("some action messages 1");
		actionMessages.add("some action messages 2");
		List field1Errors = new ArrayList();
		field1Errors.add("some field error 1");
		List field2Errors = new ArrayList();
		field2Errors.add("some field error 2");
		fieldErrors.put("field1", field1Errors);
		fieldErrors.put("field2", field2Errors);
		
		sessionMap.put(MessageStoreInterceptor.actionErrorsSessionKey, actionErrors);
		sessionMap.put(MessageStoreInterceptor.actionMessagesSessionKey, actionMessages);
		sessionMap.put(MessageStoreInterceptor.fieldErrorsSessionKey, fieldErrors);
		
		
		ActionContext actionContext = new ActionContext(new HashMap());
		actionContext.put(ActionContext.PARAMETERS, paramsMap);
		actionContext.put(ActionContext.SESSION, sessionMap);
		
		mockControlActionInvocation.expectAndDefaultReturn(
				mockActionInvocation.getInvocationContext(), actionContext);
		
		mockControlActionInvocation.expectAndDefaultReturn(
				mockActionInvocation.getAction(), action);
		
		
		mockControlActionInvocation.replay();
		
		interceptor.init();
		interceptor.intercept(mockActionInvocation);
		interceptor.destroy();
		
		assertEquals(action.getActionErrors().size(), 2);
		assertEquals(action.getActionMessages().size(), 2);
		assertEquals(action.getFieldErrors().size(), 2);
		assertTrue(action.getActionErrors().contains("some action error 1"));
		assertTrue(action.getActionErrors().contains("some action error 2"));
		assertTrue(action.getActionMessages().contains("some action messages 1"));
		assertTrue(action.getActionMessages().contains("some action messages 2"));
		assertEquals(((List)action.getFieldErrors().get("field1")).size(), 1);
		assertEquals(((List)action.getFieldErrors().get("field2")).size(), 1);
		assertEquals(((List)action.getFieldErrors().get("field1")).get(0), "some field error 1");
		assertEquals(((List)action.getFieldErrors().get("field2")).get(0), "some field error 2");
		
		mockControlActionInvocation.verify();
	}
	
	public void testRequestOperationMode1() throws Exception {
		
		Map paramMap = new LinkedHashMap();
		paramMap.put("operationMode", new String[] { MessageStoreInterceptor.RETRIEVE_MODE });
		
		ActionContext actionContext = new ActionContext(new HashMap());
		actionContext.put(ActionContext.PARAMETERS, paramMap);
		
		MockControl mockControlActionInvocation = MockControl.createControl(ActionInvocation.class);
		ActionInvocation mockActionInvocation = (ActionInvocation) mockControlActionInvocation.getMock();
		mockControlActionInvocation.expectAndDefaultReturn(
				mockActionInvocation.getInvocationContext(), actionContext);
		
		mockControlActionInvocation.replay();
		
		MessageStoreInterceptor interceptor = new MessageStoreInterceptor();
		String operationMode = interceptor.getRequestOperationMode(mockActionInvocation);
		
		assertEquals(operationMode, MessageStoreInterceptor.RETRIEVE_MODE);
		
		mockControlActionInvocation.verify();
	}
	
	public void testRequestOperationMode2() throws Exception {
		
		Map paramMap = new LinkedHashMap();
		paramMap.put("operationMode", new String[] { MessageStoreInterceptor.STORE_MODE });
		
		ActionContext actionContext = new ActionContext(new HashMap());
		actionContext.put(ActionContext.PARAMETERS, paramMap);
		
		MockControl mockControlActionInvocation = MockControl.createControl(ActionInvocation.class);
		ActionInvocation mockActionInvocation = (ActionInvocation) mockControlActionInvocation.getMock();
		mockControlActionInvocation.expectAndDefaultReturn(
				mockActionInvocation.getInvocationContext(), actionContext);
		
		
		mockControlActionInvocation.replay();
		
		MessageStoreInterceptor interceptor = new MessageStoreInterceptor();
		String operationMode = interceptor.getRequestOperationMode(mockActionInvocation);
		
		assertEquals(operationMode, MessageStoreInterceptor.STORE_MODE);
		
		mockControlActionInvocation.verify();
	}
	
	public void testRequestOperationMode3() throws Exception {
		
		Map paramMap = new LinkedHashMap();
		
		ActionContext actionContext = new ActionContext(new HashMap());
		actionContext.put(ActionContext.PARAMETERS, paramMap);
		
		MockControl mockControlActionInvocation = MockControl.createControl(ActionInvocation.class);
		ActionInvocation mockActionInvocation = (ActionInvocation) mockControlActionInvocation.getMock();
		mockControlActionInvocation.expectAndDefaultReturn(mockActionInvocation.getInvocationContext(), actionContext);
		
		mockControlActionInvocation.replay();
		
		MessageStoreInterceptor interceptor = new MessageStoreInterceptor();
		String operationMode = interceptor.getRequestOperationMode(mockActionInvocation);
		
		assertEquals(operationMode, MessageStoreInterceptor.NONE);
		
		mockControlActionInvocation.verify();
	}
}

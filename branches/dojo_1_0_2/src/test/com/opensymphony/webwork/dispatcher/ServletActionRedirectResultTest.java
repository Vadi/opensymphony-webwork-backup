/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.webwork.dispatcher.ServletActionRedirectResult;
import com.opensymphony.webwork.views.util.UrlHelper;

import org.easymock.MockControl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.ResultConfig;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class ServletActionRedirectResultTest extends WebWorkTestCase {
	
	public void testIncludeParameterInResultWithConditionParseOn() throws Exception {
		
		ResultConfig resultConfig = new ResultConfig();
		resultConfig.addParam("actionName", "someActionName");
		resultConfig.addParam("namespace", "someNamespace");
		resultConfig.addParam("encode", "true");
		resultConfig.addParam("parse", "true");
		resultConfig.addParam("location", "someLocation");
		resultConfig.addParam("prependServletContext", "true");
		resultConfig.addParam("method", "someMethod");
		resultConfig.addParam("param1", "${#value1}");
		resultConfig.addParam("param2", "${#value2}");
		resultConfig.addParam("param3", "${#value3}");
		
		ActionContext context = ActionContext.getContext();
		OgnlValueStack stack = context.getValueStack();
		context.put("value1", "value 1");
		context.put("value2", "value 2");
		context.put("value3", "value 3");
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();
		context.put(ServletActionContext.HTTP_REQUEST, req);
		context.put(ServletActionContext.HTTP_RESPONSE, res);
		
		
		Map results=  new HashMap();
		results.put("myResult", resultConfig);
		
		ActionConfig actionConfig = new ActionConfig();
		actionConfig.setResults(results);
		
		ServletActionRedirectResult result = new ServletActionRedirectResult();
		result.setActionName("myAction");
		result.setNamespace("/myNamespace");
		result.setParse(false);
		result.setEncode(false);
		result.setPrependServletContext(false);
		
		MockControl actionProxyControl = MockControl.createControl(ActionProxy.class);
		ActionProxy mockActionProxy = (ActionProxy) actionProxyControl.getMock();
		mockActionProxy.getConfig();
		actionProxyControl.setDefaultReturnValue(actionConfig);
		
		MockControl actionInvocationControl = MockControl.createControl(ActionInvocation.class);
		ActionInvocation mockInvocation = (ActionInvocation) actionInvocationControl.getMock();
		mockInvocation.getProxy();
		actionInvocationControl.setDefaultReturnValue(mockActionProxy);
		mockInvocation.getResultCode();
		actionInvocationControl.setDefaultReturnValue("myResult");
		mockInvocation.getInvocationContext();
		actionInvocationControl.setDefaultReturnValue(context);
		mockInvocation.getStack();
		actionInvocationControl.setDefaultReturnValue(stack);
		
		
		actionProxyControl.replay();
		actionInvocationControl.replay();
		
		result.execute(mockInvocation);
		
		// ugly hack to ensure consistent ordering
		HashMap hm = new HashMap();
		hm.put("param1", "value 1");		
		hm.put("param2", "value 2");		
		hm.put("param3", "value 3");	
		StringBuffer expected = new StringBuffer("/myNamespace/myAction.action");
		UrlHelper.buildParametersString(hm, expected,"&");

		assertEquals(expected.toString(), res.getRedirectedUrl());
		
		actionProxyControl.verify();
		actionInvocationControl.verify();
	}
	
	
	
	public void testIncludeParameterInResult() throws Exception {
		
		ResultConfig resultConfig = new ResultConfig();
		resultConfig.addParam("actionName", "someActionName");
		resultConfig.addParam("namespace", "someNamespace");
		resultConfig.addParam("encode", "true");
		resultConfig.addParam("parse", "true");
		resultConfig.addParam("location", "someLocation");
		resultConfig.addParam("prependServletContext", "true");
		resultConfig.addParam("method", "someMethod");
		resultConfig.addParam("param1", "value 1");
		resultConfig.addParam("param2", "value 2");
		resultConfig.addParam("param3", "value 3");
		
		ActionContext context = ActionContext.getContext();
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();
		context.put(ServletActionContext.HTTP_REQUEST, req);
		context.put(ServletActionContext.HTTP_RESPONSE, res);
		
		
		Map results=  new HashMap();
		results.put("myResult", resultConfig);
		
		ActionConfig actionConfig = new ActionConfig();
		actionConfig.setResults(results);
		
		ServletActionRedirectResult result = new ServletActionRedirectResult();
		result.setActionName("myAction");
		result.setNamespace("/myNamespace");
		result.setParse(false);
		result.setEncode(false);
		result.setPrependServletContext(false);
		
		MockControl actionProxyControl = MockControl.createControl(ActionProxy.class);
		ActionProxy mockActionProxy = (ActionProxy) actionProxyControl.getMock();
		mockActionProxy.getConfig();
		actionProxyControl.setDefaultReturnValue(actionConfig);
		
		MockControl actionInvocationControl = MockControl.createControl(ActionInvocation.class);
		ActionInvocation mockInvocation = (ActionInvocation) actionInvocationControl.getMock();
		mockInvocation.getProxy();
		actionInvocationControl.setDefaultReturnValue(mockActionProxy);
		mockInvocation.getResultCode();
		actionInvocationControl.setDefaultReturnValue("myResult");
		mockInvocation.getInvocationContext();
		actionInvocationControl.setDefaultReturnValue(context);
		
		actionProxyControl.replay();
		actionInvocationControl.replay();

		
				
		result.execute(mockInvocation);
		
		//ugly hack to ensure consistent ordering
		HashMap hm = new HashMap();
		hm.put("param1", "value 1");		
		hm.put("param2", "value 2");		
		hm.put("param3", "value 3");	
		StringBuffer expected = new StringBuffer("/myNamespace/myAction.action");
		UrlHelper.buildParametersString(hm, expected,"&");

		assertEquals(expected.toString(), res.getRedirectedUrl());
		
		actionProxyControl.verify();
		actionInvocationControl.verify();
	}
}

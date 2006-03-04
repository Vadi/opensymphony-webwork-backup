/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.jmock.Mock;
import org.jmock.core.constraint.IsEqual;
import org.jmock.core.matcher.InvokeOnceMatcher;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.mock.MockActionInvocation;

/**
 * Test case for CreateSessionInterceptor.
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class CreateSessionInterceptorTest extends WebWorkTestCase {

	public void testCreateSession() throws Exception {
		Mock httpServletRequestMock = new Mock(HttpServletRequest.class);
		httpServletRequestMock.expects(new InvokeOnceMatcher()).method("getSession").with(new IsEqual(Boolean.TRUE));
		HttpServletRequest request = (HttpServletRequest) httpServletRequestMock.proxy();

		ServletActionContext.setRequest(request);

		CreateSessionInterceptor interceptor = new CreateSessionInterceptor();
		interceptor.intercept(new MockActionInvocation());

		httpServletRequestMock.verify();
	}
}

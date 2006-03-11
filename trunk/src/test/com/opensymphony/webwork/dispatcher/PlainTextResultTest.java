/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.opensymphony.util.ClassLoaderUtil;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.webwork.views.jsp.WebWorkMockHttpServletResponse;
import com.opensymphony.webwork.views.jsp.WebWorkMockServletContext;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.mock.MockActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

/**
 * Test case for PlainTextResult.
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class PlainTextResultTest extends TestCase {
	
	OgnlValueStack stack;
	MockActionInvocation invocation;
	ActionContext context;
	WebWorkMockHttpServletResponse response;
	PrintWriter writer;
	StringWriter stringWriter;
	WebWorkMockServletContext servletContext;
	

	public void testPlainText() throws Exception {
		PlainTextResult result = new PlainTextResult();
		result.setLocation("/someJspFile.jsp");
		
		response.setExpectedContentType("text/plain");
		response.setExpectedHeader("Content-Disposition", "inline");
		InputStream jspResourceInputStream = 
			ClassLoaderUtil.getResourceAsStream(
				"com/opensymphony/webwork/dispatcher/someJspFile.jsp", 
				PlainTextResultTest.class);
		
		
		try {
			servletContext.setResourceAsStream(jspResourceInputStream);
			result.execute(invocation);
			
			String r = AbstractUITagTest.normalize(stringWriter.getBuffer().toString(), true);
			String e = AbstractUITagTest.normalize(
					readAsString("com/opensymphony/webwork/dispatcher/someJspFile.jsp"), true);
			assertEquals(r, e);
		}
		finally {
			jspResourceInputStream.close();
		}
	}
	
	protected String readAsString(String resource) throws Exception {
		InputStream is = null;
		try {
			is = ClassLoaderUtil.getResourceAsStream(resource, PlainTextResultTest.class);
			int sizeRead = 0;
			byte[] buffer = new byte[1024];
			StringBuffer stringBuffer = new StringBuffer();
			while((sizeRead = is.read(buffer)) != -1) {
				stringBuffer.append(new String(buffer, 0, sizeRead));
			}
			return stringBuffer.toString();
		}
		finally {
			if (is != null) 
				is.close();
		}
	
	}
	
	
	protected void setUp() throws Exception {
		super.setUp();
		
		stringWriter = new StringWriter();
		writer = new PrintWriter(stringWriter);
		response = new WebWorkMockHttpServletResponse();
		response.setWriter(writer);
		servletContext = new WebWorkMockServletContext();
		stack = new OgnlValueStack();
		context = new ActionContext(stack.getContext());
		context.put(WebWorkStatics.HTTP_RESPONSE, response);
		context.put(WebWorkStatics.SERVLET_CONTEXT, servletContext);
		invocation = new MockActionInvocation();
		invocation.setStack(stack);
		invocation.setInvocationContext(context);
	}
	
	
	protected void tearDown() throws Exception {
		stack = null;
		invocation = null;
		context = null;
		response = null;
		writer = null;
		stringWriter = null;
		servletContext = null;
		
		super.tearDown();
	}
}

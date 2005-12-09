/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.mockobjects.servlet.MockFilterChain;
import com.mockobjects.servlet.MockFilterConfig;
import com.mockobjects.servlet.MockHttpServletRequest;
import com.mockobjects.servlet.MockHttpServletResponse;
import com.mockobjects.servlet.MockServletContext;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;

import junit.framework.TestCase;

/**
 * FilterDispatcher TestCase.
 * 
 * @author tm_jee (tm_jee (at) yahoo.co.uk )
 * @version $Date$ $Id$
 */
public class FilterDisplatcherTest extends TestCase {

	
	public void testParsePackages() throws Exception {
		FilterDispatcher filterDispatcher = new FilterDispatcher();
		String[] result1 = filterDispatcher.parse("foo.bar.package1 foo.bar.package2 foo.bar.package3");
		String[] result2 = filterDispatcher.parse("foo.bar.package1\tfoo.bar.package2\tfoo.bar.package3");
		String[] result3 = filterDispatcher.parse("foo.bar.package1,foo.bar.package2,foo.bar.package3");
		String[] result4 = filterDispatcher.parse("foo.bar.package1    foo.bar.package2  \t foo.bar.package3   , foo.bar.package4");
		
		assertEquals(result1[0], "foo/bar/package1/");
		assertEquals(result1[1], "foo/bar/package2/");
		assertEquals(result1[2], "foo/bar/package3/");
		
		assertEquals(result2[0], "foo/bar/package1/");
		assertEquals(result2[1], "foo/bar/package2/");
		assertEquals(result2[2], "foo/bar/package3/");
		
		assertEquals(result3[0], "foo/bar/package1/");
		assertEquals(result3[1], "foo/bar/package2/");
		assertEquals(result3[2], "foo/bar/package3/");
		
		assertEquals(result4[0], "foo/bar/package1/");
		assertEquals(result4[1], "foo/bar/package2/");
		assertEquals(result4[2], "foo/bar/package3/");
		assertEquals(result4[3], "foo/bar/package4/");
	}
	
	
	public void testDoNotParseNonWebWorkRequest() throws Exception {
		Configuration.set(WebWorkConstants.WEBWORK_MAPPER_CLASS, NullActionMapper.class.getName());
	
		MockFilterConfig config = new MockFilterConfig();
		MockServletContext context = new MockServletContext();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		
		request.setupGetServletPath("/asd");
		
		DispatcherUtils.setInstance(new DoNothingDispatcherUtils(context));
		
		TestingFilterDispatcher dispatcher = new TestingFilterDispatcher();
		dispatcher.doFilter(request, response, chain);
		
		assertFalse(dispatcher.getExecutedSetupContainer());
		request.verify();
		response.verify();
		config.verify();
		context.verify();
		chain.verify();
	}
	
	
	public void testParseWebWorkRequest() throws Exception {
		Configuration.set(WebWorkConstants.WEBWORK_MAPPER_CLASS, NullActionMapper.class.getName());
		
		MockFilterConfig config = new MockFilterConfig();
		MockServletContext context = new MockServletContext();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		
		NullActionMapper.setActionMapping(new ActionMapping());
		
		request.setupGetAttribute(Boolean.TRUE);
		
		TestingFilterDispatcher dispatcher = new TestingFilterDispatcher();
		dispatcher.doFilter(request, response, chain);
		
		assertTrue(dispatcher.getExecutedSetupContainer());
		request.verify();
		response.verify();
		config.verify();
		context.verify();
		chain.verify();
	}
	
	
	
	// ========= inner classes =================================================
	
	class DoNothingDispatcherUtils extends DispatcherUtils {
		protected DoNothingDispatcherUtils(ServletContext servletContext) {
			super(servletContext);
		}
		
		protected void init(ServletContext context) { }
	}
	
	
	
	// override FilterDispatcher's setupContainer(...) for testing purposes
	class TestingFilterDispatcher extends FilterDispatcher {
		private boolean _executedSetupContainer = false;
		protected void setupContainer(HttpServletRequest request) {
			_executedSetupContainer = true;
		}
		public void setExecutedSetupContainer(boolean executedSetupContainer) {
			_executedSetupContainer = executedSetupContainer;
		}
		public boolean getExecutedSetupContainer() {
			return _executedSetupContainer;
		}
	}
}

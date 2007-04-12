/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

import com.mockobjects.servlet.MockFilterChain;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapper;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapperFactory;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.webwork.util.ObjectFactoryDestroyable;
import com.opensymphony.webwork.util.ObjectFactoryInitializable;
import com.opensymphony.webwork.util.ObjectFactoryLifecycle;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.DefaultActionProxyFactory;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.mock.MockActionInvocation;
import com.opensymphony.xwork.mock.MockActionProxy;
import com.opensymphony.xwork.mock.MockConfiguration;

import junit.framework.TestCase;

/**
 * FilterDispatcher TestCase.
 *
 * @author tm_jee 
 * @version $Date$ $Id$
 */
public class FilterDispatcherTest extends TestCase {


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
	
	public void testObjectFactoryDestroy() throws Exception {
		Configuration.reset();
		DispatcherUtils.setInstance(null);
		
		DispatcherUtils.initialize(new MockServletContext());
		
		FilterDispatcher filterDispatcher = new FilterDispatcher();
		InnerDestroyableObjectFactory destroyedObjectFactory = new InnerDestroyableObjectFactory();
		ObjectFactory.setObjectFactory(destroyedObjectFactory);
		
		assertFalse(destroyedObjectFactory.destroyed);
		filterDispatcher.destroy();
		assertTrue(destroyedObjectFactory.destroyed);
	}
	
	
	public void testObjectFactoryInitializable() throws Exception {
		Configuration.reset();
		DispatcherUtils.setInstance(null);
		
		Map configMap = new HashMap();
		configMap.put(WebWorkConstants.WEBWORK_OBJECTFACTORY, "com.opensymphony.webwork.dispatcher.FilterDispatcherTest$InnerInitializableObjectFactory");
		configMap.put(WebWorkConstants.WEBWORK_CONFIGURATION_XML_RELOAD, "false");
		Configuration.setConfiguration(new InnerConfiguration(configMap));
		
		MockServletContext servletContext = new MockServletContext();
		MockFilterConfig filterConfig = new MockFilterConfig(servletContext);
	
		
		FilterDispatcher filterDispatcher = new FilterDispatcher();
		filterDispatcher.init(filterConfig);
		
		assertTrue(ObjectFactory.getObjectFactory() instanceof InnerInitializableObjectFactory);
		assertTrue(((InnerInitializableObjectFactory)ObjectFactory.getObjectFactory()).initializable);
	}
	
	public void testObjectFactoryLifecycle() throws Exception {
		Configuration.reset();
		DispatcherUtils.setInstance(null);
		try {
			Map configMap = new HashMap();
			configMap.put(WebWorkConstants.WEBWORK_OBJECTFACTORY, "com.opensymphony.webwork.dispatcher.FilterDispatcherTest$InnerInitailizableDestroyableObjectFactory");
			configMap.put(WebWorkConstants.WEBWORK_CONFIGURATION_XML_RELOAD, "false");
			Configuration.setConfiguration(new InnerConfiguration(configMap));
		
			MockServletContext servletContext = new MockServletContext();
			MockFilterConfig filterConfig = new MockFilterConfig(servletContext);
	
		
			FilterDispatcher filterDispatcher = new FilterDispatcher();
			filterDispatcher.init(filterConfig);
		
			assertTrue(ObjectFactory.getObjectFactory() instanceof InnerInitailizableDestroyableObjectFactory);
			assertTrue(((InnerInitailizableDestroyableObjectFactory)ObjectFactory.getObjectFactory()).initializable);
		
			assertFalse(((InnerInitailizableDestroyableObjectFactory)ObjectFactory.getObjectFactory()).destroyable);
			filterDispatcher.destroy();
			assertTrue(((InnerInitailizableDestroyableObjectFactory)ObjectFactory.getObjectFactory()).destroyable);
		}
		finally {
			Configuration.reset();
			DispatcherUtils.setInstance(null);
		}
	}
	
	public void testIfActionMapperIsNullDontServiceAction() throws Exception {
		try {
			MockServletContext servletContext = new MockServletContext();
			MockFilterConfig filterConfig = new MockFilterConfig(servletContext);
			MockHttpServletRequest req = new MockHttpServletRequest(
					servletContext);
			MockHttpServletResponse res = new MockHttpServletResponse();
			MockFilterChain filterChain = new MockFilterChain();

			ActionProxyFactory.setFactory(new InnerActionProxyFactory());

			Map conf = new HashMap();
			conf.put(WebWorkConstants.WEBWORK_MAPPER_CLASS, "com.opensymphony.webwork.dispatcher.FilterDispatcherTest$NullInnerActionMapper");
			Configuration.setConfiguration(new InnerConfiguration(conf));

			NoOpDispatcherUtil du = new NoOpDispatcherUtil(servletContext);
			DispatcherUtils.setInstance(du);

			FilterDispatcher filter = new FilterDispatcher();
			filter.init(filterConfig);
			filter.doFilter(req, res, filterChain);

			assertFalse(du.serviceAction);

		}
		finally {
			DispatcherUtils.setInstance(null);
			Configuration.reset();
			ActionProxyFactory.setFactory(new DefaultActionProxyFactory());
		}
	}
	
	public void testCharacterEncodingSetBeforeRequestWrappingAndActionService() throws Exception {
		try {
		MockServletContext servletContext = new MockServletContext();
			MockFilterConfig filterConfig = new MockFilterConfig(servletContext);
			MockHttpServletRequest req = new MockHttpServletRequest(
					servletContext);
			MockHttpServletResponse res = new MockHttpServletResponse();
			MockFilterChain filterChain = new MockFilterChain();

			ActionProxyFactory.setFactory(new InnerActionProxyFactory());

			Map conf = new HashMap();
			conf.put(WebWorkConstants.WEBWORK_I18N_ENCODING, "UTF-16_DUMMY");
			conf.put(WebWorkConstants.WEBWORK_MAPPER_CLASS, "com.opensymphony.webwork.dispatcher.FilterDispatcherTest$InnerActionMapper");
			Configuration.setConfiguration(new InnerConfiguration(conf));

			InnerDispatcherUtil du = new InnerDispatcherUtil(servletContext);
			DispatcherUtils.setInstance(du);

			FilterDispatcher filter = new FilterDispatcher();
			filter.init(filterConfig);
			filter.doFilter(req, res, filterChain);

			assertTrue(du.wrappedRequest);
			assertTrue(du.serviceAction);
		}
		finally {
			Configuration.reset();
			DispatcherUtils.setInstance(null);
			ActionProxyFactory.setFactory(new DefaultActionProxyFactory());
		}
	}
	
	
	// === inner class ========
	public static class InnerConfiguration extends Configuration {
		Map m;
		public InnerConfiguration(Map configMap) {
			m = configMap;
		}
		
		public boolean isSetImpl(String name) {
			if (!m.containsKey(name)) 
				return super.isSetImpl(name);
			else 
				return true;
		}
		
		public Object getImpl(String aName) throws IllegalArgumentException {
			if (!m.containsKey(aName))
				return super.getImpl(aName);
			else 
				return m.get(aName);
		}
	}
	
	public static class InnerActionProxyFactory extends ActionProxyFactory {

		public ActionInvocation createActionInvocation(ActionProxy actionProxy, Map extraContext) throws Exception {
			return new MockActionInvocation();
		}

		public ActionInvocation createActionInvocation(ActionProxy actionProxy) throws Exception {
			return new MockActionInvocation();
		}

		public ActionInvocation createActionInvocation(ActionProxy actionProxy, Map extraContext, boolean pushAction) throws Exception {
			return new MockActionInvocation();
		}

		public ActionProxy createActionProxy(String namespace, String actionName, Map extraContext) throws Exception {
			return new MockActionProxy();
		}

		public ActionProxy createActionProxy(String namespace, String actionName, Map extraContext, boolean executeResult, boolean cleanupContext) throws Exception {
			return new MockActionProxy();
		}
		
	}
	
	
	public static class NullInnerActionMapper implements ActionMapper {
		public ActionMapping getMapping(HttpServletRequest request) {
			return null;
		}

		public String getUriFromActionMapping(ActionMapping mapping) {
			return null;
		}
	}
	
	public static class InnerActionMapper implements ActionMapper {

		public ActionMapping getMapping(HttpServletRequest request) {
			return new ActionMapping();
		}

		public String getUriFromActionMapping(ActionMapping mapping) {
			return null;
		}
	}
	
	public static class NoOpDispatcherUtil extends DispatcherUtils {
		protected boolean wrappedRequest = false;
		protected boolean serviceAction = false;
		
		protected NoOpDispatcherUtil(ServletContext servletContext) {
			super(servletContext);
		}
		
		public HttpServletRequest wrapRequest(HttpServletRequest request, ServletContext servletContext) throws IOException {
			wrappedRequest = true;
			return request;
		}
		
		public void serviceAction(HttpServletRequest request, HttpServletResponse response, ServletContext context, ActionMapping mapping) throws ServletException {
			serviceAction = true;
		}
	}
	
	public static class InnerDispatcherUtil extends DispatcherUtils {

		protected boolean wrappedRequest = false;
		protected boolean serviceAction = false;
		
		protected InnerDispatcherUtil(ServletContext servletContext) {
			super(servletContext);
		}
		
		public HttpServletRequest wrapRequest(HttpServletRequest request, ServletContext servletContext) throws IOException {
			wrappedRequest = true;
			String characterEncoding = request.getCharacterEncoding();
			// if we set the chracter encoding AFTER we do a wrapp, we will get
			// a failing test
			assertEquals(characterEncoding, "UTF-16_DUMMY");
			return request;
		}
		
		public void serviceAction(HttpServletRequest request, HttpServletResponse response, ServletContext context, ActionMapping mapping) throws ServletException {
			serviceAction = true;
			String characterEncoding = request.getCharacterEncoding();
			// if we set the chracter encoding AFTER we do an action service, we will get
			// a failing test
			assertEquals(characterEncoding, "UTF-16_DUMMY");
		}
	}
	
	public static class InnerDestroyableObjectFactory extends ObjectFactory implements ObjectFactoryDestroyable {
		public boolean destroyed = false;
		
		public void destroy() {
			destroyed = true;
		}
	}
	
	public static class InnerInitializableObjectFactory extends ObjectFactory implements ObjectFactoryInitializable {
		public boolean initializable = false;
		public void init(ServletContext servletContext) {
			initializable = true;
		}
	}
	
	public static class InnerInitailizableDestroyableObjectFactory extends ObjectFactory implements ObjectFactoryLifecycle {
		public boolean initializable = false;
		public boolean destroyable = false;
		
		public void init(ServletContext servletContext) {
			initializable = true;
		}

		public void destroy() {
			destroyable = true;
		}
	}
	
	
}

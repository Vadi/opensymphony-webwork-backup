/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockServletContext;

import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.util.ObjectFactoryDestroyable;
import com.opensymphony.webwork.util.ObjectFactoryInitializable;
import com.opensymphony.webwork.util.ObjectFactoryLifecycle;
import com.opensymphony.xwork.ObjectFactory;

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

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import org.springframework.mock.web.MockServletContext;

import com.opensymphony.webwork.util.ObjectFactoryDestroyable;
import com.opensymphony.xwork.ObjectFactory;

import junit.framework.TestCase;

/**
 * FilterDispatcher TestCase.
 *
 * @author tm_jee (tm_jee (at) yahoo.co.uk )
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
	
	public void testDestroy() throws Exception {
		DispatcherUtils.initialize(new MockServletContext());
		
		FilterDispatcher filterDispatcher = new FilterDispatcher();
		InnerDestroyableObjectFactory destroyedObjectFactory = new InnerDestroyableObjectFactory();
		ObjectFactory.setObjectFactory(destroyedObjectFactory);
		
		assertFalse(destroyedObjectFactory.destroyed);
		filterDispatcher.destroy();
		assertTrue(destroyedObjectFactory.destroyed);
	}
	
	
	// === inner class ========
	
	class InnerDestroyableObjectFactory extends ObjectFactory implements ObjectFactoryDestroyable {
		public boolean destroyed = false;
		
		public void destroy() {
			destroyed = true;
		}
		
	}
}

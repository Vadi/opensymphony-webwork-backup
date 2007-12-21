package com.opensymphony.webwork.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.opensymphony.webwork.util.ContainUtil;

public class ContainUtilTest extends TestCase {

	public void testNull() throws Exception {
		assertFalse(ContainUtil.contains(null, null));
		assertFalse(ContainUtil.contains(new Object(), null));
		assertFalse(ContainUtil.contains(null, new Object()));
	}
	
	public void testSimpleList() throws Exception {
		List l = new ArrayList();
		l.add("one");
		l.add("two");
		
		assertFalse(ContainUtil.contains(l, "three"));
		assertTrue(ContainUtil.contains(l, "one"));
		assertTrue(ContainUtil.contains(l, "two"));
	}
	
	public void testSimpleSet() throws Exception {
		Set s = new LinkedHashSet();
		s.add("one");
		s.add("two");
		
		assertFalse(ContainUtil.contains(s, "thre"));
		assertTrue(ContainUtil.contains(s, "one"));
		assertTrue(ContainUtil.contains(s, "two"));
	}
	
	public void testComplexList() throws Exception {
		List l = new ArrayList();
		l.add(new MyObject("tm_jee", Integer.valueOf("20")));
		l.add(new MyObject("jenny", Integer.valueOf("22")));
		
		assertFalse(ContainUtil.contains(l, new MyObject("paul", Integer.valueOf("50"))));
		assertFalse(ContainUtil.contains(l, new MyObject("tm_jee", Integer.valueOf("44"))));
		assertTrue(ContainUtil.contains(l, new MyObject("tm_jee", Integer.valueOf("20"))));
		assertTrue(ContainUtil.contains(l, new MyObject("jenny", Integer.valueOf("22"))));
	}
	
	public void testComplexMap() throws Exception {
		Set s = new LinkedHashSet();
		s.add(new MyObject("tm_jee", Integer.valueOf("20")));
		s.add(new MyObject("jenny", Integer.valueOf("22")));
		
		assertFalse(ContainUtil.contains(s, new MyObject("paul", Integer.valueOf("50"))));
		assertFalse(ContainUtil.contains(s, new MyObject("tm_jee", Integer.valueOf("44"))));
		assertTrue(ContainUtil.contains(s, new MyObject("tm_jee", Integer.valueOf("20"))));
		assertTrue(ContainUtil.contains(s, new MyObject("jenny", Integer.valueOf("22"))));
	}
	
	public void testObject() throws Exception {
		assertFalse(ContainUtil.contains("aaa", "bbb"));
		assertFalse(ContainUtil.contains(new MyObject("tm_jee", Integer.valueOf("22")), new MyObject("tmjee", Integer.valueOf("22"))));
		assertTrue(ContainUtil.contains("apple", "apple"));
		assertTrue(ContainUtil.contains(new MyObject("tm_jee", Integer.valueOf("22")), new MyObject("tm_jee", Integer.valueOf("22"))));
	}


	public static class MyObject {
		private String name;
		private Integer age;
		
		public MyObject(String name, Integer age) {
			this.name = name;
			this.age = age;
		}
		
		public int hashCode() {
			return this.name.hashCode();
		}
		
		public boolean equals(Object obj) {
			if (obj == null) { return false; }
			if (! (obj instanceof MyObject)) { return false; }
			MyObject tmp = (MyObject) obj;
			if (
					tmp.name.equals(this.name) &&
					tmp.age.equals(this.age)
				) {
				return true;
			}
			return false;
				
		}
	}
}

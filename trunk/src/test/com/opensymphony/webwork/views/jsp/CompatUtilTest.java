package com.opensymphony.webwork.views.jsp;

import junit.framework.TestCase;

/**
 * User: plightbo
 * Date: Nov 4, 2003
 * Time: 7:34:05 AM
 */
public class CompatUtilTest extends TestCase {
    public void testPathConversion() {
        assertEquals("foo", CompatUtil.compat("foo"));
        assertEquals("foo.bar", CompatUtil.compat("foo/bar"));
        assertEquals("foo.bar.baz", CompatUtil.compat("foo/bar/baz"));
    }

    public void testHierarchySupport() {
        assertEquals("[1].foo", CompatUtil.compat("../foo"));
        assertEquals("[1].foo.bar", CompatUtil.compat("../foo/bar"));
        assertEquals("[1].[1].foo", CompatUtil.compat("../../foo"));
    }

    public void testAtSign() {
        assertEquals("#attr.get(foo)", CompatUtil.compat("@foo"));
        assertEquals("#attr.get(foo).bar", CompatUtil.compat("@foo/bar"));
    }

    public void testMath() {
        assertEquals("1 + 2", CompatUtil.compat("1 + 2"));
        assertEquals("1 - 2", CompatUtil.compat("1 - 2"));
        assertEquals("1 * 2", CompatUtil.compat("1 * 2"));
        assertEquals("1 / 2", CompatUtil.compat("1 / 2"));
    }

    public void testTop() {
        assertEquals("top", CompatUtil.compat("."));
        assertEquals("top + 5", CompatUtil.compat(". + 5"));
        assertEquals("[1].top", CompatUtil.compat("../."));
    }

    public void testDollarSign() {
        assertEquals("#parameters.get(foo)", CompatUtil.compat("$foo"));
        assertEquals("#parameters.get(foo).bar", CompatUtil.compat("$foo/bar"));
        assertEquals("#parameters.get(foo).bar.baz", CompatUtil.compat("$foo/bar/baz"));
    }
}

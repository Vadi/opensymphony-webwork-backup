/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.util;

import com.mockobjects.dynamic.Mock;

import junit.framework.TestCase;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class UrlHelperTest extends TestCase {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * just one &, not &amp;
     */
    public void testBuildUrlCorrectlyAddsAmp() {
        String expectedString = "my.actionName?foo=bar&hello=world";
        Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
        Mock mockHttpServletResponse = new Mock(HttpServletResponse.class);
        mockHttpServletResponse.expectAndReturn("encodeURL", expectedString, expectedString);

        String actionName = "my.actionName";
        TreeMap params = new TreeMap();
        params.put("hello", "world");
        params.put("foo", "bar");

        String urlString = UrlHelper.buildUrl(actionName, (HttpServletRequest) mockHttpServletRequest.proxy(), (HttpServletResponse) mockHttpServletResponse.proxy(), params);
        assertEquals(expectedString, urlString);
    }

    public void testBuildUrlWithStringArray() {
        String expectedString = "my.actionName?foo=bar&hello=earth&hello=mars";
        Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
        Mock mockHttpServletResponse = new Mock(HttpServletResponse.class);
        mockHttpServletResponse.expectAndReturn("encodeURL", expectedString, expectedString);

        String actionName = "my.actionName";
        TreeMap params = new TreeMap();
        params.put("hello", new String[] {"earth", "mars"});
        params.put("foo", "bar");

        String urlString = UrlHelper.buildUrl(actionName, (HttpServletRequest) mockHttpServletRequest.proxy(), (HttpServletResponse) mockHttpServletResponse.proxy(), params);
        assertEquals(expectedString, urlString);
    }

}

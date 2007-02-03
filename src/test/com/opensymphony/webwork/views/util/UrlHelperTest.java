/*
 * Copyright (c) 2002-2005 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mockobjects.dynamic.Mock;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.webwork.config.Configuration;


/**
 * Test case for UrlHelper.
 * 
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class UrlHelperTest extends WebWorkTestCase {
	
	
	
	public void testForceAddSchemeHostAndPort() throws Exception {
		String expectedUrl = "http://localhost/contextPath/path1/path2/myAction.action";
		
		Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
		mockHttpServletRequest.expectAndReturn("getScheme", "http");
		mockHttpServletRequest.expectAndReturn("getServerName", "localhost");
        mockHttpServletRequest.expectAndReturn("getContextPath", "/contextPath");

        Mock mockHttpServletResponse = new Mock(HttpServletResponse.class);
        mockHttpServletResponse.expectAndReturn("encodeURL", expectedUrl, expectedUrl);
		
		String result = UrlHelper.buildUrl("/path1/path2/myAction.action", (HttpServletRequest) mockHttpServletRequest.proxy(), (HttpServletResponse)mockHttpServletResponse.proxy(), null, "http", true, true, true);
		assertEquals(expectedUrl, result);
        mockHttpServletRequest.verify();
    }
	
	public void testDoNotForceAddSchemeHostAndPort() throws Exception {
		String expectedUrl = "/contextPath/path1/path2/myAction.action";
		
		Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
		mockHttpServletRequest.expectAndReturn("getScheme", "http");
		mockHttpServletRequest.expectAndReturn("getServerName", "localhost");
        mockHttpServletRequest.expectAndReturn("getContextPath", "/contextPath");

        Mock mockHttpServletResponse = new Mock(HttpServletResponse.class);
        mockHttpServletResponse.expectAndReturn("encodeURL", expectedUrl, expectedUrl);
		
		String result = UrlHelper.buildUrl("/path1/path2/myAction.action", (HttpServletRequest)mockHttpServletRequest.proxy(), (HttpServletResponse)mockHttpServletResponse.proxy(), null, "http", true, true, false);
		
		assertEquals(expectedUrl, result);
	}
	
	
	public void testBuildParametersStringWithUrlHavingSomeExistingParameters() throws Exception {
		String expectedUrl = "http://localhost:8080/myContext/myPage.jsp?initParam=initValue&amp;param1=value1&amp;param2=value2";
		
		Map parameters = new LinkedHashMap();
		parameters.put("param1", "value1");
		parameters.put("param2", "value2");
		
		StringBuffer url = new StringBuffer("http://localhost:8080/myContext/myPage.jsp?initParam=initValue");
		
		UrlHelper.buildParametersString(parameters, url);
		
		assertEquals(
		   expectedUrl, url.toString());
	}
	
	

    public void testBuildWithRootContext() {
        String expectedUrl = "/MyAction.action";

        Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
        mockHttpServletRequest.expectAndReturn("getContextPath", "/");
        mockHttpServletRequest.expectAndReturn("getScheme", "http");

        Mock mockHttpServletResponse = new Mock(HttpServletResponse.class);
        mockHttpServletResponse.expectAndReturn("encodeURL", expectedUrl, expectedUrl);

        String actualUrl = UrlHelper.buildUrl(expectedUrl, (HttpServletRequest) mockHttpServletRequest.proxy(),
                (HttpServletResponse) mockHttpServletResponse.proxy(), new HashMap());
        assertEquals(expectedUrl, actualUrl);
    }

    /**
     * just one &, not &amp;
     */
    public void testBuildUrlCorrectlyAddsAmp() {
        String expectedString = "my.actionName?foo=bar&amp;hello=world";
        Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
        mockHttpServletRequest.expectAndReturn("getScheme", "http");
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
        String expectedString = "my.actionName?foo=bar&amp;hello=earth&amp;hello=mars";
        Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
        mockHttpServletRequest.expectAndReturn("getScheme", "http");
        Mock mockHttpServletResponse = new Mock(HttpServletResponse.class);
        mockHttpServletResponse.expectAndReturn("encodeURL", expectedString, expectedString);

        String actionName = "my.actionName";
        TreeMap params = new TreeMap();
        params.put("hello", new String[]{"earth", "mars"});
        params.put("foo", "bar");

        String urlString = UrlHelper.buildUrl(actionName, (HttpServletRequest) mockHttpServletRequest.proxy(), (HttpServletResponse) mockHttpServletResponse.proxy(), params);
        assertEquals(expectedString, urlString);
    }

    /**
     * The UrlHelper should build a URL that starts with "https" followed by the server name when the scheme of the
     * current request is "http" and the port for the "https" scheme is 443.
     */
    public void testSwitchToHttpsScheme() {
        String expectedString = "https://www.mydomain.com/mywebapp/MyAction.action?foo=bar&amp;hello=earth&amp;hello=mars";

        Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
        mockHttpServletRequest.expectAndReturn("getServerName", "www.mydomain.com");
        mockHttpServletRequest.expectAndReturn("getScheme", "http");
        mockHttpServletRequest.expectAndReturn("getServerPort", 80);
        mockHttpServletRequest.expectAndReturn("getContextPath", "/mywebapp");

        Mock mockHttpServletResponse = new Mock(HttpServletResponse.class);
        mockHttpServletResponse.expectAndReturn("encodeURL", expectedString, expectedString);

        String actionName = "/MyAction.action";
        TreeMap params = new TreeMap();
        params.put("hello", new String[]{"earth", "mars"});
        params.put("foo", "bar");

        String urlString = UrlHelper.buildUrl(actionName, (HttpServletRequest) mockHttpServletRequest.proxy(), (HttpServletResponse) mockHttpServletResponse.proxy(), params, "https", true, true);
        assertEquals(expectedString, urlString);
    }

    /**
     * The UrlHelper should build a URL that starts with "http" followed by the server name when the scheme of the
     * current request is "https" and the port for the "http" scheme is 80.
     */
    public void testSwitchToHttpScheme() {
        String expectedString = "http://www.mydomain.com/mywebapp/MyAction.action?foo=bar&amp;hello=earth&amp;hello=mars";

        Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
        mockHttpServletRequest.expectAndReturn("getServerName", "www.mydomain.com");
        mockHttpServletRequest.expectAndReturn("getScheme", "https");
        mockHttpServletRequest.expectAndReturn("getServerPort", 443);
        mockHttpServletRequest.expectAndReturn("getContextPath", "/mywebapp");

        Mock mockHttpServletResponse = new Mock(HttpServletResponse.class);
        mockHttpServletResponse.expectAndReturn("encodeURL", expectedString, expectedString);

        String actionName = "/MyAction.action";
        TreeMap params = new TreeMap();
        params.put("hello", new String[]{"earth", "mars"});
        params.put("foo", "bar");

        String urlString = UrlHelper.buildUrl(actionName, (HttpServletRequest) mockHttpServletRequest.proxy(), (HttpServletResponse) mockHttpServletResponse.proxy(), params, "http", true, true);
        assertEquals(expectedString, urlString);
    }

    /**
     * This test is similar to {@link #testSwitchToHttpsScheme()} with the HTTP port equal to 7001 and the HTTPS port
     * equal to 7002.
     */
    public void testSwitchToHttpsNonDefaultPort() {

        String expectedString = "https://www.mydomain.com:7002/mywebapp/MyAction.action?foo=bar&amp;hello=earth&amp;hello=mars";

        Configuration.set(WebWorkConstants.WEBWORK_URL_HTTP_PORT, "7001");
        Configuration.set(WebWorkConstants.WEBWORK_URL_HTTPS_PORT, "7002");

        Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
        mockHttpServletRequest.expectAndReturn("getServerName", "www.mydomain.com");
        mockHttpServletRequest.expectAndReturn("getScheme", "http");
        mockHttpServletRequest.expectAndReturn("getServerPort", 7001);
        mockHttpServletRequest.expectAndReturn("getContextPath", "/mywebapp");

        Mock mockHttpServletResponse = new Mock(HttpServletResponse.class);
        mockHttpServletResponse.expectAndReturn("encodeURL", expectedString, expectedString);

        String actionName = "/MyAction.action";
        TreeMap params = new TreeMap();
        params.put("hello", new String[]{"earth", "mars"});
        params.put("foo", "bar");

        String urlString = UrlHelper.buildUrl(actionName, (HttpServletRequest) mockHttpServletRequest.proxy(), (HttpServletResponse) mockHttpServletResponse.proxy(), params, "https", true, true);
        assertEquals(expectedString, urlString);
    }

    /**
     * This test is similar to {@link #testSwitchToHttpScheme()} with the HTTP port equal to 7001 and the HTTPS port
     * equal to port 7002.
     */
    public void testSwitchToHttpNonDefaultPort() {

        String expectedString = "http://www.mydomain.com:7001/mywebapp/MyAction.action?foo=bar&amp;hello=earth&amp;hello=mars";

        Configuration.set(WebWorkConstants.WEBWORK_URL_HTTP_PORT, "7001");
        Configuration.set(WebWorkConstants.WEBWORK_URL_HTTPS_PORT, "7002");

        Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
        mockHttpServletRequest.expectAndReturn("getServerName", "www.mydomain.com");
        mockHttpServletRequest.expectAndReturn("getScheme", "https");
        mockHttpServletRequest.expectAndReturn("getServerPort", 7002);
        mockHttpServletRequest.expectAndReturn("getContextPath", "/mywebapp");

        Mock mockHttpServletResponse = new Mock(HttpServletResponse.class);
        mockHttpServletResponse.expectAndReturn("encodeURL", expectedString, expectedString);

        String actionName = "/MyAction.action";
        TreeMap params = new TreeMap();
        params.put("hello", new String[]{"earth", "mars"});
        params.put("foo", "bar");

        String urlString = UrlHelper.buildUrl(actionName, (HttpServletRequest) mockHttpServletRequest.proxy(), (HttpServletResponse) mockHttpServletResponse.proxy(), params, "http", true, true);
        assertEquals(expectedString, urlString);
    }

    /**
     * A check to verify that the scheme, server, and port number are omitted when the scheme of the current request
     * matches the scheme supplied when building the URL.
     */
    public void testBuildWithSameScheme() {
        String expectedString = "/mywebapp/MyAction.action?foo=bar&amp;hello=earth&amp;hello=mars";

        Mock mockHttpServletRequest = new Mock(HttpServletRequest.class);
        mockHttpServletRequest.expectAndReturn("getServerName", "www.mydomain.com");
        mockHttpServletRequest.expectAndReturn("getScheme", "https");
        mockHttpServletRequest.expectAndReturn("getServerPort", 443);
        mockHttpServletRequest.expectAndReturn("getContextPath", "/mywebapp");

        Mock mockHttpServletResponse = new Mock(HttpServletResponse.class);
        mockHttpServletResponse.expectAndReturn("encodeURL", expectedString, expectedString);

        String actionName = "/MyAction.action";
        TreeMap params = new TreeMap();
        params.put("hello", new String[]{"earth", "mars"});
        params.put("foo", "bar");

        String urlString = UrlHelper.buildUrl(actionName, (HttpServletRequest) mockHttpServletRequest.proxy(), (HttpServletResponse) mockHttpServletResponse.proxy(), params, "https", true, true);
        assertEquals(expectedString, urlString);
    }

    public void testParseQuery() throws Exception {
    	Map result = UrlHelper.parseQueryString("aaa=aaaval&bbb=bbbval&ccc=");

    	assertEquals(result.get("aaa"), "aaaval");
    	assertEquals(result.get("bbb"), "bbbval");
    	assertEquals(result.get("ccc"), "");
    }
    
    public void testParseEmptyQuery() throws Exception {
    	Map result = UrlHelper.parseQueryString("");
    	
    	assertNotNull(result);
    	assertEquals(result.size(), 0);
    }
    
    public void testParseNullQuery() throws Exception {
        Map result = UrlHelper.parseQueryString(null);
        
        assertNotNull(result);
        assertEquals(result.size(), 0);
    }

    public void testParseMultiQuery() throws Exception {
        Map result = UrlHelper.parseQueryString("param=1&param=1&param=1");
        
        assertNotNull(result);
        assertEquals(result.size(), 1);
        String values[] = (String[]) result.get("param");
        Arrays.sort(values);
        assertEquals(values.length, 3);
        assertEquals(values[0], "1");
        assertEquals(values[1], "1");
        assertEquals(values[2], "1");
    }

    public void testParseDuplicateQuery() throws Exception {
        Map result = UrlHelper.parseQueryString("param=1&param=2&param=3");
        
        assertNotNull(result);
        assertEquals(result.size(), 1);
        String values[] = (String[]) result.get("param");
        Arrays.sort(values);
        assertEquals(values.length, 3);
        assertEquals(values[0], "1");
        assertEquals(values[1], "2");
        assertEquals(values[2], "3");
    }

    public void testTranslateAndEncode() throws Exception {
    	Object defaultI18nEncoding = Configuration.get(WebWorkConstants.WEBWORK_I18N_ENCODING);
    	try {
    		Configuration.set(WebWorkConstants.WEBWORK_I18N_ENCODING, "UTF-8");
    		String result = UrlHelper.translateAndEncode("\u65b0\u805e");
    		String expectedResult = "%E6%96%B0%E8%81%9E";

    		assertEquals(result, expectedResult);
    	}
    	finally {
    		Configuration.set(WebWorkConstants.WEBWORK_I18N_ENCODING, defaultI18nEncoding);
    	}
    }

    public void testTranslateAndDecode() throws Exception {
    	Object defaultI18nEncoding = Configuration.get(WebWorkConstants.WEBWORK_I18N_ENCODING);
    	try {
    		Configuration.set(WebWorkConstants.WEBWORK_I18N_ENCODING, "UTF-8");
    		String result = UrlHelper.translateAndDecode("%E6%96%B0%E8%81%9E");
    		String expectedResult = "\u65b0\u805e";

    		assertEquals(result, expectedResult);
    	}
    	finally {
    		Configuration.set(WebWorkConstants.WEBWORK_I18N_ENCODING, defaultI18nEncoding);
    	}
    }
}

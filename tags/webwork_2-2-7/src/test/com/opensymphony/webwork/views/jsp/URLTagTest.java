/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.StringWriter;

import com.opensymphony.webwork.components.URL;

import com.opensymphony.webwork.views.jsp.ParamTag;
import com.opensymphony.webwork.views.util.UrlHelper;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
                                                                        
import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * Unit test for {@link URLTag}.
 *
 * @author Brock Bulger (brockman_bulger@hotmail.com)
 * @version $Date$ $Id$
 */
public class URLTagTest extends AbstractUITagTest {

    private URLTag tag;
    
    
    /**
     * To test priority of parameter passed in to url component though 
     * various way 
     *  - current request url
     *  - tag's value attribute
     *  - tag's nested param tag
     * 
	 * id1
	 * ===
	 * - found in current request url
	 * - found in tag's value attribute
	 * - found in tag's param tag
	 * CONCLUSION: tag's param tag takes precedence (paramId1)
	 * 
	 * id2
	 * ===
	 * - found in current request url
	 * - found in tag's value attribute
	 * CONCLUSION: tag's value attribute take precedence (tagId2)
	 * 
	 * urlParam1
	 * =========
	 * - found in current request url
	 * CONCLUSION: param in current request url will be used (urlValue1)
	 * 
	 * urlParam2
	 * =========
	 * - found in current request url
	 * CONCLUSION: param in current request url will be used. (urlValue2)
	 * 
	 * tagId
	 * =====
	 * - found in tag's value attribute
	 * CONCLUSION: param in tag's value attribute wil; be used. (tagValue)
	 * 
	 * param1
	 * ======
	 * - found in nested param tag
	 * CONCLUSION: param in nested param tag will be used. (param1value)
	 * 
	 * param2
	 * ======
	 * - found in nested param tag
	 * CONCLUSION: param in nested param tag will be used. (param2value)
	 */
    public void testParametersPriority() throws Exception {
    	request.setQueryString("id1=urlId1&id2=urlId2&urlParam1=urlValue1&urlParam2=urlValue2");
    	
    	tag.setValue("testAction.action?id1=tagId1&id2=tagId2&tagId=tagValue");
    	
    	ParamTag param1 = new ParamTag();
    	param1.setPageContext(pageContext);
    	param1.setName("param1");
    	param1.setValue("%{'param1value'}");
    	
    	ParamTag param2 = new ParamTag();
    	param2.setPageContext(pageContext);
    	param2.setName("param2");
    	param2.setValue("%{'param2value'}");
    	
    	ParamTag param3 = new ParamTag();
    	param3.setPageContext(pageContext);
    	param3.setName("id1");
    	param3.setValue("%{'paramId1'}");
    	
    	
    	tag.doStartTag();
    	param1.doStartTag();
    	param1.doEndTag();
    	param2.doStartTag();
    	param2.doEndTag();
    	param3.doStartTag();
    	param3.doEndTag();
    	
    	URL url = (URL) tag.getComponent();
    	Map parameters = url.getParameters();
    	
    	
    	assertNotNull(parameters);
    	assertEquals(parameters.size(), 7);
    	assertEquals(parameters.get("id1"), "paramId1");
    	assertEquals(parameters.get("id2"), "tagId2");
    	assertEquals(parameters.get("urlParam1"), "urlValue1");
    	assertEquals(parameters.get("urlParam2"), "urlValue2");
    	assertEquals(parameters.get("tagId"), "tagValue");
    	assertEquals(parameters.get("param1"), "param1value");
    	assertEquals(parameters.get("param2"), "param2value");
    }
    
    
    /**
     * To test priority of parameter passed in to url component though 
     * various way, with includeParams="NONE"
     *  - current request url
     *  - tag's value attribute
     *  - tag's nested param tag
     *  
     *  In this case only parameters from the tag itself is taken into account.
     *  Those from request will not count, only those in tag's value attribute 
     *  and nested param tag.
     *  
     * @throws Exception
     */
    public void testParametersPriorityWithIncludeParamsAsNONE() throws Exception {
    	request.setQueryString("id1=urlId1&id2=urlId2&urlParam1=urlValue1&urlParam2=urlValue2");
    	
    	tag.setValue("testAction.action?id1=tagId1&id2=tagId2&tagId=tagValue");
    	tag.setIncludeParams("NONE");
    	
    	ParamTag param1 = new ParamTag();
    	param1.setPageContext(pageContext);
    	param1.setName("param1");
    	param1.setValue("%{'param1value'}");
    	
    	ParamTag param2 = new ParamTag();
    	param2.setPageContext(pageContext);
    	param2.setName("param2");
    	param2.setValue("%{'param2value'}");
    	
    	ParamTag param3 = new ParamTag();
    	param3.setPageContext(pageContext);
    	param3.setName("id1");
    	param3.setValue("%{'paramId1'}");
    	
    	
    	tag.doStartTag();
    	param1.doStartTag();
    	param1.doEndTag();
    	param2.doStartTag();
    	param2.doEndTag();
    	param3.doStartTag();
    	param3.doEndTag();
    	
    	URL url = (URL) tag.getComponent();
    	Map parameters = url.getParameters();
    	
    	assertEquals(parameters.size(), 5);
    	assertEquals(parameters.get("id1"), "paramId1");
    	assertEquals(parameters.get("id2"), "tagId2");
    	assertEquals(parameters.get("tagId"), "tagValue");
    	assertEquals(parameters.get("param1"), "param1value");
    	assertEquals(parameters.get("param2"), "param2value");
    }

    public void testIncludeParamsDefaultToGET() throws Exception {
    	request.setQueryString("one=oneVal&two=twoVal&three=threeVal");

    	// request parameter map should not have any effect, as includeParams
    	// default to GET, which get its param from request.getQueryString()
    	Map tmp = new HashMap();
    	tmp.put("one", "aaa");
    	tmp.put("two", "bbb");
    	tmp.put("three", "ccc");
    	request.setParameterMap(tmp);

    	tag.setValue("TestAction.acton");

    	tag.doStartTag();

    	URL url = (URL) tag.getComponent();
    	Map parameters = url.getParameters();

    	tag.doEndTag();

    	assertEquals(parameters.get("one"), "oneVal");
    	assertEquals(parameters.get("two"), "twoVal");
    	assertEquals(parameters.get("three"), "threeVal");
    }


    public void testActionURL() throws Exception {
        tag.setValue("TestAction.action");

        tag.doStartTag();
        tag.doEndTag();
        assertEquals("TestAction.action", writer.toString());
    }

    public void testAddParameters() throws Exception {
        request.setAttribute("webwork.request_uri", "/Test.action");

        request.setAttribute("webwork.request_uri", "/TestAction.action");
        request.setQueryString("param0=value0");

        tag.doStartTag();
        tag.component.addParameter("param1", "value1");
        tag.component.addParameter("param2", "value2");
        tag.doEndTag();
        
		//ugly hack to ensure consistent ordering
		HashMap hm = new HashMap();
		hm.put("param1", "value1");		
		hm.put("param2", "value2");		
		hm.put("param0", "value0");	
		StringBuffer expected = new StringBuffer("/TestAction.action");
		UrlHelper.buildParametersString(hm, expected);

        assertEquals(expected.toString(), writer.toString());
    }

    public void testEvaluateValue() throws Exception {
        Foo foo = new Foo();
        foo.setTitle("test");
        stack.push(foo);
        tag.setValue("%{title}");

        tag.doStartTag();
        tag.doEndTag();
        assertEquals("test", writer.toString());
    }

    public void testHttps() throws Exception {
        request.setScheme("https");
        request.setServerName("localhost");
        request.setServerPort(443);

        tag.setValue("list-members.action");

        tag.doStartTag();
        tag.doEndTag();
        assertEquals("list-members.action", writer.toString());
    }

    public void testAnchor() throws Exception {
        request.setScheme("https");
        request.setServerName("localhost");
        request.setServerPort(443);

        tag.setValue("list-members.action");
        tag.setAnchor("test");

        tag.doStartTag();
        tag.doEndTag();
        assertEquals("list-members.action#test", writer.toString());
    }
    
    public void testParamPrecedence() throws Exception {
    	request.setRequestURI("/context/someAction.action");
    	request.setQueryString("id=22&name=John");
    	
    	URLTag urlTag = new URLTag();
    	urlTag.setPageContext(pageContext);
    	urlTag.setIncludeParams("get");
    	urlTag.setEncode("%{false}");
    	
    	ParamTag paramTag = new ParamTag();
    	paramTag.setPageContext(pageContext);
    	paramTag.setName("id");
    	paramTag.setValue("%{'33'}");
    	
    	urlTag.doStartTag();
    	paramTag.doStartTag();
    	paramTag.doEndTag();
    	urlTag.doEndTag();
       	
		//ugly hack to ensure consistent ordering
		HashMap hm = new HashMap();
		hm.put("name", "John");		
		hm.put("id", "33");		
		StringBuffer expected = new StringBuffer("/context/someAction.action");
		UrlHelper.buildParametersString(hm, expected);
    	
    	assertEquals(expected.toString(),writer.getBuffer().toString());
    }

    public void testParamPrecedenceWithAnchor() throws Exception {
    	request.setRequestURI("/context/someAction.action");
    	request.setQueryString("id=22&name=John");

    	URLTag urlTag = new URLTag();
    	urlTag.setPageContext(pageContext);
    	urlTag.setIncludeParams("get");
    	urlTag.setEncode("%{false}");
        urlTag.setAnchor("testAnchor");

        ParamTag paramTag = new ParamTag();
    	paramTag.setPageContext(pageContext);
    	paramTag.setName("id");
    	paramTag.setValue("%{'33'}");

    	urlTag.doStartTag();
    	paramTag.doStartTag();
    	paramTag.doEndTag();
    	urlTag.doEndTag();
    	
		//ugly hack to ensure consistent ordering
		HashMap hm = new HashMap();
		hm.put("name", "John");		
		hm.put("id", "33");		
		StringBuffer expected = new StringBuffer("/context/someAction.action");
		UrlHelper.buildParametersString(hm, expected);
		expected.append("#testAnchor");
		
    	assertEquals(expected.toString(), writer.getBuffer().toString());
    }

    public void testPutId() throws Exception {
        tag.setValue("/public/about");
        assertEquals(null, stack.findString("myId")); // nothing in stack
        tag.setId("myId");
        tag.doStartTag();
        tag.doEndTag();
        assertEquals("", writer.toString());
        assertEquals("/public/about", stack.findString("myId")); // is in stack now
    }
    
    public void testUsingValueOnly() throws Exception {
        tag.setValue("/public/about/team.jsp");
        tag.doStartTag();
        tag.doEndTag();
        assertEquals("/public/about/team.jsp", writer.toString());
    }

    public void testRequestURIActionIncludeNone() throws Exception {
        request.setRequestURI("/public/about");
        request.setQueryString("section=team&company=acme inc");

        tag.setAction("team");
        tag.setIncludeParams("none");
        tag.doStartTag();
        tag.doEndTag();

        assertEquals("/team.action", writer.toString());
    }

    public void testRequestURIActionIncludeGet() throws Exception {
        request.setRequestURI("/public/about");
        request.setQueryString("section=team&company=acme inc");

        tag.setAction("team");
        tag.setIncludeParams("get");
        tag.doStartTag();
        tag.doEndTag();
		//ugly hack to ensure consistent ordering
		HashMap hm = new HashMap();
		hm.put("section", "team");		
		hm.put("company", "acme inc");		
		StringBuffer expected = new StringBuffer("/team.action");
		UrlHelper.buildParametersString(hm, expected);

        assertEquals(expected.toString(), writer.toString());
    }

    public void testRequestURINoActionIncludeNone() throws Exception {
        request.setRequestURI("/public/about");
        request.setQueryString("section=team&company=acme inc");

        tag.setAction(null);
        tag.setIncludeParams("none");
        tag.doStartTag();
        tag.doEndTag();

        assertEquals("/public/about", writer.toString());
    }

    public void testNoActionIncludeGet() throws Exception {
        request.setRequestURI("/public/about");
        request.setQueryString("section=team&company=acme inc");

        tag.setAction(null);
        tag.setIncludeParams("get");
        tag.doStartTag();
        tag.doEndTag();

		//ugly hack to ensure consistent ordering
		HashMap hm = new HashMap();
		hm.put("section", "team");		
		hm.put("company", "acme inc");		
		StringBuffer expected = new StringBuffer("/public/about");
		UrlHelper.buildParametersString(hm, expected);

        assertEquals(expected.toString(), writer.toString());
    }

    public void testRequestURIActionIncludeAll() throws Exception {
        request.setRequestURI("/public/about");
        request.setQueryString("section=team&company=acme inc");

        tag.setAction("team");
        tag.setIncludeParams("all");

        tag.doStartTag();

        // include nested param tag
        ParamTag paramTag = new ParamTag();
        paramTag.setPageContext(pageContext);
        paramTag.setName("year");
        paramTag.setValue("2006");
        paramTag.doStartTag();
        paramTag.doEndTag();

        tag.doEndTag();
		//ugly hack to ensure consistent ordering
		HashMap hm = new HashMap();
		hm.put("section", "team");		
		hm.put("company", "acme inc");		
		hm.put("year", "2006");	
		StringBuffer expected = new StringBuffer("/team.action");
		UrlHelper.buildParametersString(hm, expected);

        assertEquals(expected.toString(), writer.toString());
   }

    public void testRequestURINoActionIncludeAll() throws Exception {
        request.setRequestURI("/public/about");
        request.setQueryString("section=team&company=acme inc");

        tag.setAction(null);
        tag.setIncludeParams("all");

        tag.doStartTag();

        // include nested param tag
        ParamTag paramTag = new ParamTag();
        paramTag.setPageContext(pageContext);
        paramTag.setName("year");
        paramTag.setValue("2006");
        paramTag.doStartTag();
        paramTag.doEndTag();

        tag.doEndTag();
		//ugly hack to ensure consistent ordering
		HashMap hm = new HashMap();
		hm.put("section", "team");		
		hm.put("company", "acme inc");		
		hm.put("year", "2006");	
		StringBuffer expected = new StringBuffer("/public/about");
		UrlHelper.buildParametersString(hm, expected);

        assertEquals(expected.toString(), writer.toString());
    }

    public void testUnknownIncludeParam() throws Exception {
        request.setRequestURI("/public/about");
        request.setQueryString("section=team");

        tag.setIncludeParams("unknown"); // will log at WARN level
        tag.doStartTag();
        tag.doEndTag();
        assertEquals("/public/about", writer.toString()); // should not add any request parameters
    }

    public void testRequestURIWithAnchor() throws Exception {
        request.setRequestURI("/public/about");
        request.setQueryString("company=acme inc#canada");

        tag.setAction("company");
        tag.setIncludeParams("get");
        tag.doStartTag();
        tag.doEndTag();

        assertEquals("/company.action?company=acme+inc", writer.toString()); // will always chop anchor if using requestURI
    }

    public void testIncludeContext() throws Exception {
        request.setupGetContext("/myapp");

        tag.setIncludeContext("true");
        tag.setAction("company");
        tag.doStartTag();
        tag.doEndTag();

        assertEquals("/myapp/company.action", writer.toString());
    }

    protected void setUp() throws Exception {
        super.setUp();

        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(80);

        tag = new URLTag();
        tag.setPageContext(pageContext);
        JspWriter jspWriter = new WebWorkMockJspWriter(writer);
        pageContext.setJspWriter(jspWriter);
    }

    public class Foo {
        private String title;

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public String toString() {
            return "Foo is: " + title;
        }
    }
}

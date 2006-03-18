/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.StringWriter;


/**
 * URLTag testcase.
 *
 * @author Brock Bulger (brockman_bulger@hotmail.com)
 * @version $Date$ $Id$
 */
public class URLTagTest extends AbstractUITagTest {
    private StringWriter writer = new StringWriter();
    private URLTag tag;

    public void testActionURL() {
        tag.setValue("TestAction.action");

        try {
            tag.doStartTag();
            tag.doEndTag();
            assertEquals("TestAction.action", writer.toString());
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testAddParameters() {
        request.setAttribute("webwork.request_uri", "/Test.action");

        request.setAttribute("webwork.request_uri", "/TestAction.action");
        request.setQueryString("param0=value0");

        try {
            tag.doStartTag();
            tag.component.addParameter("param1", "value1");
            tag.component.addParameter("param2", "value2");
            tag.doEndTag();
            assertEquals("/TestAction.action?param2=value2&amp;param0=value0&amp;param1=value1", writer.toString());
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testEvaluateValue() {
        Foo foo = new Foo();
        foo.setTitle("test");
        stack.push(foo);
        tag.setValue("%{title}");

        try {
            tag.doStartTag();
            tag.doEndTag();
            assertEquals("test", writer.toString());
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testHttps() {
        request.setScheme("https");
        request.setServerName("localhost");
        request.setServerPort(443);

        tag.setValue("list-members.action");

        try {
            tag.doStartTag();
            tag.doEndTag();
            assertEquals("list-members.action", writer.toString());
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testAnchor() {
        request.setScheme("https");
        request.setServerName("localhost");
        request.setServerPort(443);

        tag.setValue("list-members.action");
        tag.setAnchor("test");

        try {
            tag.doStartTag();
            tag.doEndTag();
            assertEquals("list-members.action#test", writer.toString());
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
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
    	
    	assertEquals(writer.getBuffer().toString(), "/context/someAction.action?name=John&amp;id=33");
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

    	assertEquals(writer.getBuffer().toString(), "/context/someAction.action?name=John&amp;id=33#testAnchor");
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

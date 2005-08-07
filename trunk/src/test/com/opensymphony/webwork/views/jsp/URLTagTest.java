/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.StringWriter;


/**
 * DOCUMENT ME!
 *
 * @author Brock Bulger (brockman_bulger@hotmail.com)
 * @version $Revision$
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
            tag.addParameter("param1", "value1");
            tag.addParameter("param2", "value2");
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

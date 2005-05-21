/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockJspWriter;

import javax.servlet.jsp.JspException;


/**
 * DOCUMENT ME!
 *
 * @author Brock Bulger (brockman_bulger@hotmail.com)
 * @version $Revision$
 */
public class URLTagTest extends AbstractUITagTest {
    //~ Instance fields ////////////////////////////////////////////////////////

    private MockJspWriter jspWriter;
    private URLTag tag;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testActionURL() {
        jspWriter.setExpectedData("TestAction.action");
        tag.setValue("TestAction.action");

        try {
            tag.doStartTag();
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testAddParameters() {
        request.setAttribute("webwork.request_uri", "/Test.action");

        jspWriter.setExpectedData("/TestAction.action?param2=value2&param0=value0&param1=value1");
        request.setAttribute("webwork.request_uri", "/TestAction.action");
        request.setQueryString("param0=value0");

        try {
            tag.doStartTag();
            tag.addParameter("param1", "value1");
            tag.addParameter("param2", "value2");
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testEvaluateValue() {
        Foo foo = new Foo();
        foo.setTitle("test");
        stack.push(foo);
        jspWriter.setExpectedData("test");
        tag.setValue("%{title}");

        try {
            tag.doStartTag();
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testHttps() {
        request.setScheme("https");
        request.setServerName("localhost");
        request.setServerPort(443);

        jspWriter.setExpectedData("list-members.action");
        tag.setValue("list-members.action");

        try {
            tag.doStartTag();
            tag.doEndTag();
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
        jspWriter = new MockJspWriter();
        pageContext.setJspWriter(jspWriter);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        jspWriter.verify();
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

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

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockHttpServletRequest;
import com.mockobjects.servlet.MockJspWriter;
import com.mockobjects.servlet.MockPageContext;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

import java.util.HashMap;

import javax.servlet.jsp.JspException;


/**
 * DOCUMENT ME!
 *
 * @author Brock Bulger (brockman_bulger@hotmail.com)
 * @version $Revision$
 */
public class URLTagTest extends AbstractJspTest {
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
        jspWriter.setExpectedData("TestAction.action?param2=value2&param1=value1");
        tag.setValue("TestAction.action");

        try {
            tag.doStartTag();
            tag.addParam("param1", "value1");
            tag.addParam("param2", "value2");
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
        tag.setValue("title");

        try {
            tag.doStartTag();
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testEvaluateValueNotFound() {
        Foo foo = new Foo();
        foo.setTitle("test");
        stack.push(foo);
        jspWriter.setExpectedData("email");
        tag.setValue("email");

        try {
            tag.doStartTag();
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testSamePageUrl() {
        request.setupGetRequestURI("testSamePageUrl.action?foo=bar");

        // Unfortunately, the MockHttpServletRequest returns null for getParameterMap()
        //        request.setupAddParameter("requestParam1", "requestValue1");
        //        request.setupAddParameter("requestParam2", "requestValue2");
        request.setupGetParameterMap(new HashMap());
        jspWriter.setExpectedData("testSamePageUrl.action?foo=bar");

        try {
            tag.doStartTag();
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testSetPageAndValueAttribute() {
        jspWriter.setExpectedData("bar.jsp");
        tag.setPage("foo.jsp");
        tag.setValue("'bar.jsp'");

        try {
            tag.doStartTag();
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testSetPageAttribute() {
        jspWriter.setExpectedData("foo.jsp");
        tag.setPage("foo.jsp");

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

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
public class URLTagTest extends TestCase {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testActionURL() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        MockJspWriter jspWriter = new MockJspWriter();
        jspWriter.setExpectedData("TestAction.action");

        MockPageContext pageContext = new MockPageContext();
        pageContext.setJspWriter(jspWriter);
        pageContext.setRequest(request);

        URLTag tag = new URLTag();
        tag.setPageContext(pageContext);
        tag.setValue("TestAction.action");

        try {
            tag.doStartTag();
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }

        request.verify();
        jspWriter.verify();
        pageContext.verify();
    }

    public void testAddParameters() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        MockJspWriter jspWriter = new MockJspWriter();
        jspWriter.setExpectedData("TestAction.action?parm1=value1");

        MockPageContext pageContext = new MockPageContext();
        pageContext.setJspWriter(jspWriter);
        pageContext.setRequest(request);

        URLTag tag = new URLTag();
        tag.setPageContext(pageContext);
        tag.setValue("TestAction.action");

        try {
            tag.doStartTag();
            tag.addParam("parm1", "value1");
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }

        request.verify();
        jspWriter.verify();
        pageContext.verify();
    }

    public void testEvaluateValue() {
        Foo foo = new Foo();
        foo.setTitle("test");

        OgnlValueStack stack = new OgnlValueStack();
        stack.push(foo);

        MockHttpServletRequest request = new MockHttpServletRequest();
        ActionContext.getContext().setValueStack(stack);

        MockJspWriter jspWriter = new MockJspWriter();
        jspWriter.setExpectedData("test");

        MockPageContext pageContext = new MockPageContext();
        pageContext.setJspWriter(jspWriter);
        pageContext.setRequest(request);

        URLTag tag = new URLTag();
        tag.setPageContext(pageContext);
        tag.setValue("title");

        try {
            tag.doStartTag();
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }

        request.verify();
        jspWriter.verify();
        pageContext.verify();
    }

    public void testEvaluateValueNotFound() {
        Foo foo = new Foo();
        foo.setTitle("test");

        OgnlValueStack stack = new OgnlValueStack();
        stack.push(foo);

        MockHttpServletRequest request = new MockHttpServletRequest();
        ActionContext.getContext().setValueStack(stack);

        MockJspWriter jspWriter = new MockJspWriter();
        jspWriter.setExpectedData("email");

        MockPageContext pageContext = new MockPageContext();
        pageContext.setJspWriter(jspWriter);
        pageContext.setRequest(request);

        URLTag tag = new URLTag();
        tag.setPageContext(pageContext);
        tag.setValue("email");

        try {
            tag.doStartTag();
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }

        request.verify();
        jspWriter.verify();
        pageContext.verify();
    }

    public void testSamePageUrl() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setupGetRequestURI("testSamePageUrl.action?foo=bar");

        // Unfortunately, the MockHttpServletRequest returns null for getParameterMap()
        //        request.setupAddParameter("requestParam1", "requestValue1");
        //        request.setupAddParameter("requestParam2", "requestValue2");
        request.setupGetParameterMap(new HashMap());

        MockJspWriter jspWriter = new MockJspWriter();
        jspWriter.setExpectedData("testSamePageUrl.action?foo=bar");

        MockPageContext pageContext = new MockPageContext();
        pageContext.setJspWriter(jspWriter);
        pageContext.setRequest(request);

        URLTag tag = new URLTag();
        tag.setPageContext(pageContext);

        try {
            tag.doStartTag();
            tag.doEndTag();
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }

        request.verify();
        jspWriter.verify();
        pageContext.verify();
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

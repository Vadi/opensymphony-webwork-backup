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

import javax.servlet.jsp.JspException;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class PropertyTagTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    MockHttpServletRequest request = new MockHttpServletRequest();
    OgnlValueStack stack = new OgnlValueStack();

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testDefaultValue() {
        PropertyTag tag = new PropertyTag();

        Foo foo = new Foo();

        stack.push(foo);

        MockJspWriter jspWriter = new MockJspWriter();
        jspWriter.setExpectedData("TEST");

        MockPageContext pageContext = new MockPageContext();
        pageContext.setJspWriter(jspWriter);
        pageContext.setRequest(request);

        tag.setPageContext(pageContext);
        tag.setValue("title");
        tag.setDefault("TEST");

        try {
            tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        request.verify();
        jspWriter.verify();
        pageContext.verify();
    }

    public void testNull() {
        PropertyTag tag = new PropertyTag();

        Foo foo = new Foo();

        stack.push(foo);

        MockJspWriter jspWriter = new MockJspWriter();
        jspWriter.setExpectedData("");

        MockPageContext pageContext = new MockPageContext();
        pageContext.setJspWriter(jspWriter);
        pageContext.setRequest(request);

        tag.setPageContext(pageContext);
        tag.setValue("title");

        try {
            tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        request.verify();
        jspWriter.verify();
        pageContext.verify();
    }

    public void testSimple() {
        PropertyTag tag = new PropertyTag();

        Foo foo = new Foo();
        foo.setTitle("test");

        stack.push(foo);

        MockJspWriter jspWriter = new MockJspWriter();
        jspWriter.setExpectedData("test");

        MockPageContext pageContext = new MockPageContext();
        pageContext.setJspWriter(jspWriter);
        pageContext.setRequest(request);

        tag.setPageContext(pageContext);
        tag.setValue("title");

        try {
            tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        request.verify();
        jspWriter.verify();
        pageContext.verify();
    }

    public void testTopOfStack() {
        PropertyTag tag = new PropertyTag();

        Foo foo = new Foo();
        foo.setTitle("test");

        stack.push(foo);

        MockJspWriter jspWriter = new MockJspWriter();
        jspWriter.setExpectedData("Foo is: test");

        MockPageContext pageContext = new MockPageContext();
        pageContext.setJspWriter(jspWriter);
        pageContext.setRequest(request);

        tag.setPageContext(pageContext);

        try {
            tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        request.verify();
        jspWriter.verify();
        pageContext.verify();
    }

    protected void setUp() throws Exception {
        ActionContext.getContext().setValueStack(stack);
        request.setupGetAttribute(stack);
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

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
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() {
        PropertyTag tag = new PropertyTag();

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

        OgnlValueStack stack = new OgnlValueStack();
        stack.push(foo);

        MockHttpServletRequest request = new MockHttpServletRequest();
        ActionContext.getContext().setValueStack(stack);

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

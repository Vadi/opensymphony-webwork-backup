/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockHttpServletRequest;
import com.mockobjects.servlet.MockPageContext;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import junit.framework.TestCase;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * @author $Author$
 * @version $Revision$
 */
public class IfTagTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    IfTag tag;
    MockPageContext pageContext;
    OgnlValueStack stack;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testNonBooleanTest() {
        // set up the stack
        Foo foo = new Foo();
        foo.setNum(1);
        stack.push(foo);

        // set up the test
        tag.setTest("num");

        int result = 0;

        try {
            result = tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.EVAL_BODY_INCLUDE, result);

        try {
            result = tag.doEndTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testTestError() {
        // set up the stack
        Foo foo = new Foo();
        foo.setNum(2);
        stack.push(foo);

        // set up the test
        tag.setTest("nuuuuum == 2");

        int result = 0;

        try {
            result = tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.SKIP_BODY, result);

        try {
            result = tag.doEndTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testTestFalse() {
        // set up the stack
        Foo foo = new Foo();
        foo.setNum(2);
        stack.push(foo);

        // set up the test
        tag.setTest("num != 2");

        int result = 0;

        try {
            result = tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.SKIP_BODY, result);

        try {
            result = tag.doEndTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testTestTrue() {
        // set up the stack
        Foo foo = new Foo();
        foo.setNum(2);
        stack.push(foo);

        // set up the test
        tag.setTest("num == 2");

        int result = 0;

        try {
            result = tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.EVAL_BODY_INCLUDE, result);

        try {
            result = tag.doEndTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }
    }

    protected void setUp() throws Exception {
        // create the needed objects
        tag = new IfTag();
        stack = new OgnlValueStack();

        // create the mock http servlet request
        MockHttpServletRequest request = new MockHttpServletRequest();
        ActionContext.getContext().setValueStack(stack);
        request.setupGetAttribute(stack);

        // create the mock page context
        pageContext = new MockPageContext();
        pageContext.setRequest(request);

        // associate the tag with the mock page request
        tag.setPageContext(pageContext);
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    class Foo {
        int num;

        public void setNum(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }
}

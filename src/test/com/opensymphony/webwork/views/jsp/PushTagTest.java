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


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class PushTagTest extends TestCase {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() {
        PushTag tag = new PushTag();

        Foo foo = new Foo();
        foo.setTitle("test");

        OgnlValueStack stack = new OgnlValueStack();
        stack.push(foo);

        MockHttpServletRequest request = new MockHttpServletRequest();
        ActionContext.getContext().setValueStack(stack);

        MockPageContext pageContext = new MockPageContext();
        pageContext.setRequest(request);

        tag.setPageContext(pageContext);
        tag.setValue("title");

        try {
            assertEquals(1, stack.size());
            tag.doStartTag();
            assertEquals(2, stack.size());
            tag.doEndTag();
            assertEquals(1, stack.size());
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        request.verify();
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
    }
}

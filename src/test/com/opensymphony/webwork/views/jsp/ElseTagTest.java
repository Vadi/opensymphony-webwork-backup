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
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class ElseTagTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    ElseTag elseTag;
    MockPageContext pageContext;
    OgnlValueStack stack;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testTestFalse() {
        pageContext.setAttribute(IfTag.ANSWER, new Boolean(false));
        elseTag.setPageContext(pageContext);

        int result = 0;

        try {
            result = elseTag.doStartTag();
            elseTag.doEndTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.EVAL_BODY_INCLUDE, result);
    }

    public void testTestNull() {
        pageContext.setAttribute(IfTag.ANSWER, null);
        elseTag.setPageContext(pageContext);

        int result = 0;

        try {
            result = elseTag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.SKIP_BODY, result);
    }

    public void testTestTrue() {
        pageContext.setAttribute(IfTag.ANSWER, new Boolean(true));
        elseTag.setPageContext(pageContext);

        int result = 0;

        try {
            result = elseTag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.SKIP_BODY, result);
    }

    protected void setUp() throws Exception {
        // create the needed objects
        elseTag = new ElseTag();
        stack = new OgnlValueStack();

        // create the mock http servlet request
        MockHttpServletRequest request = new MockHttpServletRequest();
        ActionContext.getContext().setValueStack(stack);

        // create the mock page context
        pageContext = new WebWorkMockPageContext();
        pageContext.setRequest(request);
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

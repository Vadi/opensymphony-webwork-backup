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
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class BeanTagTest extends TestCase {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockPageContext pageContext = new MockPageContext();
        pageContext.setRequest(request);

        BeanTag tag = new BeanTag();
        tag.setPageContext(pageContext);
        tag.setName("com.opensymphony.webwork.TestAction");

        try {
            tag.doStartTag();
            tag.addParam("result", "success");

            OgnlValueStack stack = ActionContext.getContext().getValueStack();
            assertEquals("success", stack.findValue("result"));
            assertEquals(1, ActionContext.getContext().getValueStack().size());
            tag.doEndTag();
            assertEquals(0, ActionContext.getContext().getValueStack().size());
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }

        request.verify();
        pageContext.verify();
    }
}

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

import ognl.Ognl;

import javax.servlet.jsp.JspException;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class BeanTagTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() {
        BeanTag tag = new BeanTag();
        tag.setPageContext(pageContext);
        tag.setName("'com.opensymphony.webwork.TestAction'");

        try {
            tag.doStartTag();
            tag.addParameter("result", "success");

            assertEquals("success", stack.findValue("result"));
            assertEquals(2, stack.size());
            tag.doEndTag();
            assertEquals(1, stack.size());
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }

        request.verify();
        pageContext.verify();
    }
}

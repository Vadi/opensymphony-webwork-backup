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
public class PushTagTest extends AbstractJspTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() {
        PushTag tag = new PushTag();

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
    }
}

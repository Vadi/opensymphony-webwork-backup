/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;


/**
 * @author $Author$
 * @version $Revision$
 */
public class SetTagTest extends AbstractUITagTest {
    //~ Instance fields ////////////////////////////////////////////////////////

    Chewbacca chewie;
    SetTag tag;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testApplicationScope() throws JspException {
        Mock application = new Mock(ServletContext.class);
        application.expect("setAttribute", C.args(C.eq("foo"), C.eq("chewie")));
        pageContext.setServletContext((ServletContext) application.proxy());

        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("application");
        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        application.verify();
    }

    public void testPageScope() throws JspException {
        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("page");
        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
    }

    public void testRequestScope() throws JspException {
        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("request");
        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("chewie", request.getAttribute("foo"));
    }

    public void testSessionScope() throws JspException {
        Mock session = new Mock(HttpSession.class);
        session.expect("setAttribute", C.args(C.eq("foo"), C.eq("chewie")));
        pageContext.setSession((HttpSession) session.proxy());

        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("session");
        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        session.verify();
    }

    public void testWebWorkScope() throws JspException {
        tag.setName("foo");
        tag.setValue("name");
        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("chewie", context.get("foo"));
    }

    public void testWebWorkScope2() throws JspException {
        tag.setName("chewie");
        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        assertEquals(chewie, context.get("chewie"));
    }

    protected void setUp() throws Exception {
        super.setUp();

        tag = new SetTag();
        chewie = new Chewbacca("chewie", true);
        stack.push(chewie);
        tag.setPageContext(pageContext);
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    public class Chewbacca {
        String name;
        boolean furry;

        public Chewbacca(String name, boolean furry) {
            this.name = name;
            this.furry = furry;
        }

        public void setFurry(boolean furry) {
            this.furry = furry;
        }

        public boolean isFurry() {
            return furry;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

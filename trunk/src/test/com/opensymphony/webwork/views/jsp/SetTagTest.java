/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import com.mockobjects.servlet.MockPageContext;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class SetTagTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    Chewbacca chewie;
    MockPageContext pageContext;
    OgnlValueStack vs;
    SetTag tag;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testApplicationScope() throws JspException {
        Mock application = new Mock(ServletContext.class);
        application.expect("setAttribute", C.args(C.eq("foo"), C.eq("chewie")));
        pageContext.setServletContext((ServletContext) application.proxy());

        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("application");
        assertEquals(Tag.EVAL_PAGE, tag.doStartTag());
        application.verify();
    }

    public void testPageScope() throws JspException {
        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("page");
        assertEquals(Tag.EVAL_PAGE, tag.doStartTag());
    }

    public void testRequestScope() throws JspException {
        Mock request = new Mock(ServletRequest.class);
        request.expect("setAttribute", C.args(C.eq("foo"), C.eq("chewie")));
        pageContext.setRequest((ServletRequest) request.proxy());

        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("request");
        assertEquals(Tag.EVAL_PAGE, tag.doStartTag());
        request.verify();
    }

    public void testSessionScope() throws JspException {
        Mock session = new Mock(HttpSession.class);
        session.expect("setAttribute", C.args(C.eq("foo"), C.eq("chewie")));
        pageContext.setSession((HttpSession) session.proxy());

        tag.setName("foo");
        tag.setValue("name");
        tag.setScope("session");
        assertEquals(Tag.EVAL_PAGE, tag.doStartTag());
        session.verify();
    }

    public void testWebWorkScope() throws JspException {
        tag.setName("foo");
        tag.setValue("name");
        assertEquals(Tag.EVAL_PAGE, tag.doStartTag());
        assertEquals("chewie", ActionContext.getContext().get("foo"));
    }

    public void testWebWorkScope2() throws JspException {
        tag.setName("chewie");
        assertEquals(Tag.EVAL_PAGE, tag.doStartTag());
        assertEquals(chewie, ActionContext.getContext().get("chewie"));
    }

    protected void setUp() throws Exception {
        tag = new SetTag();

        chewie = new Chewbacca("chewie", true);

        vs = new OgnlValueStack();

        Map context = vs.getContext();
        vs.push(chewie);

        ActionContext ac = new ActionContext(context);
        ActionContext.setContext(ac);
        ActionContext.getContext().setValueStack(vs);

        pageContext = new MockPageContext();
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

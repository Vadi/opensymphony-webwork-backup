/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkStatics;

import com.opensymphony.xwork.ActionContext;

import junit.framework.TestCase;

import ognl.Ognl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class ServletRedirectResultTest extends TestCase implements WebWorkStatics {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected ServletRedirectResult view;
    private Mock requestMock;
    private Mock responseMock;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testAbsoluteRedirect() {
        view.setLocation("/bar/foo.jsp");
        responseMock.expect("sendRedirect", C.args(C.eq("/bar/foo.jsp")));

        try {
            view.execute(null);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testRelativeRedirect() {
        view.setLocation("foo.jsp");
        responseMock.expect("sendRedirect", C.args(C.eq("/context/foo.jsp")));

        try {
            view.execute(null);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    protected void setUp() {
        view = new ServletRedirectResult();

        responseMock = new Mock(HttpServletResponse.class);

        requestMock = new Mock(HttpServletRequest.class);
        requestMock.matchAndReturn("getContextPath", "/context/");

        ActionContext ac = new ActionContext(Ognl.createDefaultContext(null));
        ActionContext.setContext(ac);
        ServletActionContext.setResponse((HttpServletResponse) responseMock.proxy());
        ServletActionContext.setRequest((HttpServletRequest) requestMock.proxy());
    }

    protected void tearDown() {
        requestMock.verify();
        responseMock.verify();
    }
}

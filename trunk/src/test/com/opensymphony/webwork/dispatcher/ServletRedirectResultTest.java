/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.MockActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;
import ognl.Ognl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class ServletRedirectResultTest extends WebWorkTestCase implements WebWorkStatics {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected ServletRedirectResult view;
    private ActionContext oldContext;
    private Mock requestMock;
    private Mock responseMock;
    protected ActionInvocation ai;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testAbsoluteRedirect() {
        view.setLocation("/bar/foo.jsp");
        responseMock.expectAndReturn("encodeRedirectURL", "/context/bar/foo.jsp", "/context/bar/foo.jsp");
        responseMock.expect("sendRedirect", C.args(C.eq("/context/bar/foo.jsp")));

        try {
            view.execute(ai);
            requestMock.verify();
            responseMock.verify();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testPrependServletContextFalse() {
        view.setLocation("/bar/foo.jsp");
        view.setPrependServletContext(false);
        responseMock.expectAndReturn("encodeRedirectURL", "/bar/foo.jsp", "/bar/foo.jsp");
        responseMock.expect("sendRedirect", C.args(C.eq("/bar/foo.jsp")));

        try {
            view.execute(ai);
            requestMock.verify();
            responseMock.verify();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testRelativeRedirect() throws Exception {
        view.setLocation("foo.jsp");
        requestMock.expectAndReturn("getParameterMap", new HashMap());
        requestMock.expectAndReturn("getServletPath", "/namespace/some.action");
        requestMock.expectAndReturn("getAttribute", C.ANY_ARGS, null);
        responseMock.expectAndReturn("encodeRedirectURL", "/context/namespace/foo.jsp", "/context/namespace/foo.jsp");
        responseMock.expect("sendRedirect", C.args(C.eq("/context/namespace/foo.jsp")));

        view.execute(ai);

        requestMock.verify();
        responseMock.verify();
    }

    protected void setUp() throws Exception {
        super.setUp();

        view = new ServletRedirectResult();

        responseMock = new Mock(HttpServletResponse.class);

        requestMock = new Mock(HttpServletRequest.class);
        requestMock.matchAndReturn("getContextPath", "/context");

        ActionContext ac = new ActionContext(Ognl.createDefaultContext(null));
        ac.put(ServletActionContext.HTTP_REQUEST, requestMock.proxy());
        ac.put(ServletActionContext.HTTP_RESPONSE, responseMock.proxy());
        MockActionInvocation ai = new MockActionInvocation();
        ai.setInvocationContext(ac);
        this.ai = ai;
        ai.setStack(new OgnlValueStack());
    }
}

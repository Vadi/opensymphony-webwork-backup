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
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() {
        ServletRedirectResult view = new ServletRedirectResult();
        view.setLocation("foo.jsp");

        Mock responseMock = new Mock(HttpServletResponse.class);
        responseMock.expectVoid("sendRedirect", C.args(C.eq("foo.jsp")));

        Mock requestMock = new Mock(HttpServletRequest.class);

        ActionContext ac = new ActionContext(Ognl.createDefaultContext(null));
        ActionContext.setContext(ac);
        ServletActionContext.setResponse((HttpServletResponse) responseMock.proxy());
        ServletActionContext.setRequest((HttpServletRequest) requestMock.proxy());

        try {
            view.execute(null);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        requestMock.verify();
        responseMock.verify();
    }
}

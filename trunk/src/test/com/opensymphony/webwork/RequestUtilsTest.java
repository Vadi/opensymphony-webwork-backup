/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */

package com.opensymphony.webwork;

/**
 * <code>RequestUtilsTest</code>
 *
 * @author Rainer Hermanns
 * @version $Id$
 */
import junit.framework.TestCase;
import com.mockobjects.dynamic.Mock;

import javax.servlet.http.HttpServletRequest;

public class RequestUtilsTest extends TestCase {

    public void testGetServletPathWithServletPathSet() throws Exception {
        Mock requestMock = new Mock(HttpServletRequest.class);
        requestMock.expectAndReturn("getServletPath", "/mycontext/");
        assertEquals("/mycontext/", RequestUtils.getServletPath((HttpServletRequest) requestMock.proxy()));
    }

    public void testGetServletPathWithRequestURIAndEmptyContextPath() throws Exception {
        Mock requestMock = new Mock(HttpServletRequest.class);
        requestMock.expectAndReturn("getServletPath", null);
        requestMock.expectAndReturn("getRequestURI", "/mycontext/test.jsp");
        requestMock.expectAndReturn("getContextPath", "");
        requestMock.expectAndReturn("getContextPath", "");
        requestMock.expectAndReturn("getPathInfo", "test.jsp");
        requestMock.expectAndReturn("getPathInfo", "test.jsp");
        assertEquals("/mycontext/", RequestUtils.getServletPath((HttpServletRequest) requestMock.proxy()));
    }

    public void testGetServletPathWithRequestURIAndContextPathSet() throws Exception {
        Mock requestMock = new Mock(HttpServletRequest.class);
        requestMock.expectAndReturn("getServletPath", null);
        requestMock.expectAndReturn("getRequestURI", "/servlet/mycontext/test.jsp");
        requestMock.expectAndReturn("getContextPath", "/servlet");
        requestMock.expectAndReturn("getContextPath", "/servlet");
        requestMock.expectAndReturn("getPathInfo", "test.jsp");
        requestMock.expectAndReturn("getPathInfo", "test.jsp");
        assertEquals("/mycontext/", RequestUtils.getServletPath((HttpServletRequest) requestMock.proxy()));
    }

    public void testGetServletPathWithRequestURIAndContextPathSetButNoPatchInfo() throws Exception {
        Mock requestMock = new Mock(HttpServletRequest.class);
        requestMock.expectAndReturn("getServletPath", null);
        requestMock.expectAndReturn("getRequestURI", "/servlet/mycontext/");
        requestMock.expectAndReturn("getContextPath", "/servlet");
        requestMock.expectAndReturn("getContextPath", "/servlet");
        requestMock.expectAndReturn("getPathInfo", null);
        requestMock.expectAndReturn("getPathInfo", null);
        assertEquals("/mycontext/", RequestUtils.getServletPath((HttpServletRequest) requestMock.proxy()));
    }

}
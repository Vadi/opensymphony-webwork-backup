/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockHttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;


/**
 * WebWorkMockHttpServletResponse
 *
 * @author Jason Carreira
 *         Created May 21, 2003 10:46:53 AM
 */
public class WebWorkMockHttpServletResponse extends MockHttpServletResponse {
    private Locale locale;
    //~ Methods ////////////////////////////////////////////////////////////////

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(new ByteArrayOutputStream());
    }

    public String encodeURL(String s) {
        return s;
    }

    public String encodeUrl(String s) {
        return s;
    }
}

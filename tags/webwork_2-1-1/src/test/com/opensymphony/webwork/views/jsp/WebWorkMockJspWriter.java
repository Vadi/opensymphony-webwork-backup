/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockJspWriter;

import java.io.IOException;
import java.io.StringWriter;


/**
 * Unforunately, the MockJspWriter throws a NotImplementedException when any of the Writer methods are invoked and
 * as you might guess, Velocity uses the Writer methods.  I'velocityEngine subclassed the MockJspWriter for the time being so
 * that we can do testing on the results until MockJspWriter gets fully implemented.
 * <p/>
 * todo replace this once MockJspWriter implements Writer correctly (i.e. doesn't throw NotImplementException)
 */
public class WebWorkMockJspWriter extends MockJspWriter {
    //~ Instance fields ////////////////////////////////////////////////////////

    StringWriter writer;

    //~ Constructors ///////////////////////////////////////////////////////////

    public WebWorkMockJspWriter(StringWriter writer) {
        this.writer = writer;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void write(String str) throws IOException {
        writer.write(str);
    }

    public void write(int c) throws IOException {
        writer.write(c);
    }

    public void write(char[] cbuf) throws IOException {
        writer.write(cbuf);
    }

    public void write(String str, int off, int len) throws IOException {
        writer.write(str, off, len);
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;


/**
 * Created by IntelliJ IDEA.
 * User: matt
 * Date: May 9, 2003
 * Time: 2:32:27 AM
 * To change this template use Options | File Templates.
 */
public class WebWorkJspWriter extends JspWriter {
    //~ Instance fields ////////////////////////////////////////////////////////

    private PrintWriter pw;

    //~ Constructors ///////////////////////////////////////////////////////////

    public WebWorkJspWriter(Writer out) {
        super(0, false);

        if (!(out instanceof PrintWriter)) {
            this.pw = new PrintWriter(out);
        } else {
            this.pw = (PrintWriter) out;
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public int getRemaining() {
        throw new UnsupportedOperationException();
    }

    public void clear() throws IOException {
        throw new UnsupportedOperationException();
    }

    public void clearBuffer() throws IOException {
        throw new UnsupportedOperationException();
    }

    public void close() throws IOException {
        pw.close();
    }

    public void flush() throws IOException {
        pw.flush();
    }

    public void newLine() throws IOException {
        pw.println();
    }

    public void print(boolean b) throws IOException {
        pw.print(b);
    }

    public void print(char c) throws IOException {
        pw.print(c);
    }

    public void print(int i) throws IOException {
        pw.print(i);
    }

    public void print(long l) throws IOException {
        pw.print(l);
    }

    public void print(float v) throws IOException {
        pw.print(v);
    }

    public void print(double v) throws IOException {
        pw.print(v);
    }

    public void print(char[] chars) throws IOException {
        pw.print(chars);
    }

    public void print(String s) throws IOException {
        pw.print(s);
    }

    public void print(Object o) throws IOException {
        pw.print(o);
    }

    public void println() throws IOException {
        pw.println();
    }

    public void println(boolean b) throws IOException {
        pw.println(b);
    }

    public void println(char c) throws IOException {
        pw.print(c);
    }

    public void println(int i) throws IOException {
        pw.print(i);
    }

    public void println(long l) throws IOException {
        pw.print(l);
    }

    public void println(float v) throws IOException {
        pw.print(v);
    }

    public void println(double v) throws IOException {
        pw.print(v);
    }

    public void println(char[] chars) throws IOException {
        pw.print(chars);
    }

    public void println(String s) throws IOException {
        pw.print(s);
    }

    public void println(Object o) throws IOException {
        pw.print(o);
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        pw.write(cbuf, off, len);
    }
}

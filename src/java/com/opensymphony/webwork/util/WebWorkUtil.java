/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.opensymphony.util.TextUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.*;

import java.net.URLEncoder;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/**
 *        WebWork utility methods for Velocity templates
 *
 *        @author Rickard Öberg (rickard@dreambean.com)
 *        @version $Revision$
 */
public final class WebWorkUtil {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(WebWorkUtil.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    Map classes = new Hashtable();
    private Context ctx;

    //~ Constructors ///////////////////////////////////////////////////////////

    public WebWorkUtil(Context ctx) {
        this.ctx = ctx;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public Object bean(Object aName) throws Exception {
        String name = aName.toString();
        Class c = (Class) classes.get(name);

        if (c == null) {
            c = ClassLoaderUtils.loadClass(name, WebWorkUtil.class);
            classes.put(name, c);
        }

        return c.newInstance();
    }

    public String evaluate(String expression) throws IOException, ResourceNotFoundException, MethodInvocationException, ParseErrorException {
        CharArrayWriter writer = new CharArrayWriter();
        Velocity.evaluate(ctx, writer, "Error parsing " + expression, expression);

        return writer.toString();
    }

    public String htmlEncode(String s) {
        return TextUtils.htmlEncode(s);
    }

    public String include(Object aName, ServletRequest aRequest, ServletResponse aResponse) throws Exception {
        try {
            RequestDispatcher dispatcher = aRequest.getRequestDispatcher(aName.toString());

            if (dispatcher == null) {
                throw new IllegalArgumentException("Cannot find included file " + aName);
            }

            ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) aResponse);

            dispatcher.include(aRequest, responseWrapper);

            return responseWrapper.getData();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String textToHtml(String s) {
        return TextUtils.plainTextToHtml(s);
    }

    public int toInt(long aLong) {
        return (int) aLong;
    }

    public long toLong(int anInt) {
        return (long) anInt;
    }

    public long toLong(String aLong) {
        if (aLong == null) {
            return 0;
        }

        return Long.parseLong(aLong);
    }

    public String toString(long aLong) {
        return Long.toString(aLong);
    }

    public String toString(int anInt) {
        return Integer.toString(anInt);
    }

    public String urlEncode(String s) {
        return URLEncoder.encode(s);
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    static class ResponseWrapper extends HttpServletResponseWrapper {
        ByteArrayOutputStream bout;
        PrintWriter writer;
        ServletOutputStream sout;

        ResponseWrapper(HttpServletResponse aResponse) {
            super(aResponse);
            bout = new ByteArrayOutputStream();
            sout = new ServletOutputStreamWrapper(bout);
            writer = new PrintWriter(new OutputStreamWriter(bout));
        }

        public String getData() {
            writer.flush();

            return bout.toString();
        }

        public ServletOutputStream getOutputStream() {
            return sout;
        }

        public PrintWriter getWriter() throws IOException {
            return writer;
        }
    }

    static class ServletOutputStreamWrapper extends ServletOutputStream {
        ByteArrayOutputStream stream;

        ServletOutputStreamWrapper(ByteArrayOutputStream aStream) {
            stream = aStream;
        }

        public void write(int aByte) {
            stream.write(aByte);
        }
    }
}

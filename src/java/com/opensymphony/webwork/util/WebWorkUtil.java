/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.opensymphony.util.TextUtils;

import com.opensymphony.webwork.views.util.UrlHelper;

import com.opensymphony.xwork.Action;

import org.apache.commons.logging.*;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

            ServletResponseHandler responseHandler = new ServletResponseHandler(aResponse);
            Class[] interfaces = new Class[] {HttpServletResponse.class};
            HttpServletResponse response = (HttpServletResponse) Proxy.newProxyInstance(getClass().getClassLoader(), interfaces, responseHandler);

            dispatcher.include(aRequest, response);

            return responseHandler.getData();
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

    static class ServletOutputStreamWrapper extends ServletOutputStream {
        ByteArrayOutputStream stream;

        ServletOutputStreamWrapper(ByteArrayOutputStream aStream) {
            stream = aStream;
        }

        public void write(int aByte) {
            stream.write(aByte);
        }
    }

    static class ServletResponseHandler implements InvocationHandler {
        ByteArrayOutputStream bout;
        PrintWriter writer;
        ServletOutputStream sout;
        ServletResponse response;

        ServletResponseHandler(ServletResponse aResponse) {
            response = aResponse;
            bout = new ByteArrayOutputStream();
            sout = new ServletOutputStreamWrapper(bout);
            writer = new PrintWriter(new OutputStreamWriter(bout));
        }

        public String getData() {
            writer.flush();

            return bout.toString();
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("getOutputStream")) {
                return getOutputStream();
            } else if (method.getName().equals("getWriter")) {
                return writer;
            } else {
                return method.invoke(response, args);
            }
        }

        ServletOutputStream getOutputStream() {
            return sout;
        }
    }
}

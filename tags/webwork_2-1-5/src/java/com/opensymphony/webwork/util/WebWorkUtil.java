/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.opensymphony.util.TextUtils;
import com.opensymphony.webwork.views.jsp.ui.OgnlTool;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;


/**
 * WebWork base utility class, for use in Velocity and Freemarker templates
 *
 * @author Rickard Ã–berg (rickard@dreambean.com)
 * @author Cameron Braid
 * @version $Revision$
 */
public class WebWorkUtil {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected static final Log log = LogFactory.getLog(WebWorkUtil.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Map classes = new Hashtable();
    protected OgnlTool ognl = OgnlTool.getInstance();
    protected OgnlValueStack stack;

    //~ Constructors ///////////////////////////////////////////////////////////

    public WebWorkUtil(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        this.stack = stack;
        this.request = request;
        this.response = response;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public Object bean(Object aName) throws Exception {
        String name = aName.toString();
        Class c = (Class) classes.get(name);

        if (c == null) {
            c = ClassLoaderUtils.loadClass(name, WebWorkUtil.class);
            classes.put(name, c);
        }

        return ObjectFactory.getObjectFactory().buildBean(c);
    }

    public Object findString(String name) {
        return stack.findValue(name, String.class);
    }

    public String include(Object aName) throws Exception {
        return include(aName, request, response);
    }

    /**
     * @deprecated the request and response are stored in this util class, please use include(string)
     */
    public String include(Object aName, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        try {
            RequestDispatcher dispatcher = aRequest.getRequestDispatcher(aName.toString());

            if (dispatcher == null) {
                throw new IllegalArgumentException("Cannot find included file " + aName);
            }

            ResponseWrapper responseWrapper = new ResponseWrapper(aResponse);

            dispatcher.include(aRequest, responseWrapper);

            return responseWrapper.getData();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String textToHtml(String s) {
        return TextUtils.plainTextToHtml(s);
    }

    public String urlEncode(String s) {
        return URLEncoder.encode(s);
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    static class ResponseWrapper extends HttpServletResponseWrapper {
        StringWriter strout;
        PrintWriter writer;
        ServletOutputStream sout;

        ResponseWrapper(HttpServletResponse aResponse) {
            super(aResponse);
            strout = new StringWriter();
            sout = new ServletOutputStreamWrapper(strout);
            writer = new PrintWriter(strout);
        }

        public String getData() {
            writer.flush();

            return strout.toString();
        }

        public ServletOutputStream getOutputStream() {
            return sout;
        }

        public PrintWriter getWriter() throws IOException {
            return writer;
        }
    }

    static class ServletOutputStreamWrapper extends ServletOutputStream {
        StringWriter writer;

        ServletOutputStreamWrapper(StringWriter aWriter) {
            writer = aWriter;
        }

        public void write(int aByte) {
            writer.write(aByte);
        }
    }
}

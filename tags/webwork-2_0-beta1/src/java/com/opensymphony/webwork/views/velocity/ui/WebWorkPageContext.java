/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.Writer;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;


/**
 * @author Matt Ho <matt@indigoegg.com>
 */
public class WebWorkPageContext extends PageContext {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static Log log = LogFactory.getLog(WebWorkPageContext.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    private Map attributes;
    private Servlet servlet;
    private ServletConfig config;
    private ServletRequest request;
    private ServletResponse response;
    private Writer out;

    //~ Constructors ///////////////////////////////////////////////////////////

    public WebWorkPageContext(ServletConfig config, ServletRequest request, ServletResponse response, Writer out) throws IOException {
        this.attributes = new HashMap();
        this.out = out;
        this.config = config;
        this.request = request;
        this.response = response;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public void setAttribute(String key, Object value, int index) {
        throw new UnsupportedOperationException();
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public Object getAttribute(String s, int i) {
        throw new UnsupportedOperationException();
    }

    public Enumeration getAttributeNamesInScope(int i) {
        throw new UnsupportedOperationException();
    }

    public int getAttributesScope(String s) {
        throw new UnsupportedOperationException();
    }

    public Exception getException() {
        throw new UnsupportedOperationException();
    }

    public JspWriter getOut() {
        return new WebWorkJspWriter(this.out);
    }

    public Object getPage() {
        throw new UnsupportedOperationException();
    }

    public ServletRequest getRequest() {
        return request;
    }

    public ServletResponse getResponse() {
        return response;
    }

    public ServletConfig getServletConfig() {
        return config;
    }

    public ServletContext getServletContext() {
        return config.getServletContext();
    }

    public HttpSession getSession() {
        return ((HttpServletRequest) request).getSession();
    }

    public Object findAttribute(String s) {
        return this.attributes.get(s);
    }

    public void forward(String s) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(s);
        dispatcher.forward(request, response);
    }

    public void handlePageException(Exception e) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    public void handlePageException(Throwable throwable) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    public void include(String s) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(s);
        dispatcher.include(request, response);
    }

    /**
     * @todo fill this thingy in
     * @param servlet
     * @param request
     * @param response
     * @param s
     * @param b
     * @param i
     * @param b1
     * @throws IOException
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     */
    public void initialize(Servlet servlet, ServletRequest request, ServletResponse response, String s, boolean b, int i, boolean b1) throws IOException, IllegalStateException, IllegalArgumentException {
        this.servlet = servlet;
        this.request = request;
        this.response = response;
    }

    public void release() {
        throw new UnsupportedOperationException();
    }

    public void removeAttribute(String s) {
        this.attributes.remove(s);
    }

    public void removeAttribute(String s, int i) {
        throw new UnsupportedOperationException();
    }
}

/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Factory;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.RequestConstants;
import com.opensymphony.module.sitemesh.filter.PageResponseWrapper;
import com.opensymphony.module.sitemesh.util.Container;
import com.opensymphony.module.sitemesh.util.OutputConverter;
import com.opensymphony.util.TextUtils;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.portlet.util.FileUtils;
import com.opensymphony.webwork.portlet.util.GeneralUtil;
import com.opensymphony.webwork.views.velocity.VelocityManager;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn"> Henry Hu </a>
 * @since 2005-7-6
 */
public abstract class WebWorkAbstractServlet extends HttpServlet implements RequestConstants {
    // ////////////////////////////////////////////////////////

    private VelocityManager velocityManager;

    private Factory factory = null;

    private ServletConfig servletConfig = null;

    private static final TextUtils TEXT_UTILS = new TextUtils();

    private static final FileUtils FILE_UTILS = new FileUtils();

    private static final GeneralUtil GENERAL_UTIL = new GeneralUtil();

    // ///////////////////////////////////////////////////////////

    public WebWorkAbstractServlet() {
        velocityManager = VelocityManager.getInstance();
    }

    // ////////////////////////////////////////////////////////////////

    protected abstract void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        this.servletConfig = servletConfig;
        factory = Factory.getInstance(new Config(servletConfig));

        servletConfig.getServletContext().setAttribute("webwork.servlet", this);

        //Initialize VelocityEngine@VelocityManager
        VelocityManager.getInstance().init(getServletContext());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doFilter(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doFilter(request, response);
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doFilter(request, response);
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Prepare the SiteMesh factory for ApplyDecoratorDirective to use.
        com.opensymphony.webwork.portlet.context.PortletContext.getContext().setSiteMeshFactory(factory);

        OgnlValueStack stack = ActionContext.getContext().getValueStack();

        request.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, stack);
        stack.getContext().put(ServletActionContext.HTTP_REQUEST, request);
        stack.getContext().put(ServletActionContext.HTTP_RESPONSE, response);
        stack.getContext().put(ServletActionContext.SERVLET_CONTEXT, getServletContext());

        //if (request.getAttribute(FILTER_APPLIED) != null ||
        // factory.isPathExcluded(extractRequestPath(request))) {
        if (factory.isPathExcluded(extractRequestPath(request))) {
            // ensure that filter is only applied once per request
            doRequest(request, response);
        } else {
            ////////////////////Fix for Jetspeed/Tomcat ///////////////////////
            //request.setAttribute(FILTER_APPLIED, Boolean.TRUE);

            // force creation of the session now because Tomcat 4 had problems
            // with
            // creating sessions after the response had been committed
            if (Container.get() == Container.TOMCAT) {
                request.getSession(true);
            }

            // parse data into Page object (or continue as normal if Page not
            // parseable)
            Page page = parsePage(request, response);

            if (page != null) {
                page.setRequest(request);

                Decorator decorator = factory.getDecoratorMapper().getDecorator(request, page);
                if (decorator != null && decorator.getPage() != null) {
                    applyDecorator(page, decorator, request, response);
                    page = null;
                    return;
                }

                // if we got here, an exception occured or the decorator was
                // null,
                // what we don't want is an exception printed to the user, so
                // we write the original page
                writeOriginal(request, response, page);
                page = null;
            }

        }
    }

    protected void applyDecorator(Page page, Decorator decorator, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String decoratorPageName = decorator.getPage();

        if (decoratorPageName.lastIndexOf(".jsp") >= 0) {
            applyDecoratorUsingJSP(page, decorator, request, response);
        } else if (decoratorPageName.lastIndexOf(".vm") >= 0) {
            applyDecoratorUsingVelocity(request, page, response, decorator);
        }
    }

    private String extractRequestPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String query = request.getQueryString();
        return (servletPath == null ? "" : servletPath) + (pathInfo == null ? "" : pathInfo) + (query == null ? "" : ("?" + query));
    }

    /**
     * Continue in filter-chain, writing all content to buffer and parsing into
     * returned {@link com.opensymphony.module.sitemesh.Page}object. If
     * {@link com.opensymphony.module.sitemesh.Page}is not parseable, null is
     * returned.
     */
    protected Page parsePage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            PageResponseWrapper pageResponse = new PageResponseWrapper(response, factory);
            doRequest(request, pageResponse);
            // check if another servlet or filter put a page object to the
            // request
            Page result = (Page) request.getAttribute(PAGE);
            if (result == null) {
                // parse the page
                result = pageResponse.getPage();
            }
            request.setAttribute(USING_STREAM, new Boolean(pageResponse.isUsingStream()));
            return result;
        } catch (IllegalStateException e) {
            // weblogic throws an IllegalStateException when an error page is
            // served.
            // it's ok to ignore this, however for all other containers it
            // should be thrown
            // properly.
            if (Container.get() != Container.WEBLOGIC)
                throw e;
            return null;
        }
    }

    /**
     * Write the original page data to the response.
     */
    private void writeOriginal(HttpServletRequest request, HttpServletResponse response, Page page) throws IOException {
        response.setContentLength(page.getContentLength());
        if (request.getAttribute(USING_STREAM).equals(Boolean.TRUE)) {
            PrintWriter writer = new PrintWriter(response.getOutputStream());
            page.writePage(writer);
            //flush writer to underlying outputStream
            writer.flush();
            response.getOutputStream().flush();
        } else {
            page.writePage(response.getWriter());
            response.getWriter().flush();
        }
    }

    /**
     * Apply {@link com.opensymphony.module.sitemesh.Decorator}to
     * {@link com.opensymphony.module.sitemesh.Page}and write to the response.
     */
    protected void applyDecoratorUsingJSP(Page page, Decorator decorator, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute(PAGE, page);
            ServletContext context = getServletContext();
            // see if the URI path (webapp) is set
            if (decorator.getURIPath() != null) {
                // in a security conscious environment, the servlet container
                // may return null for a given URL
                if (context.getContext(decorator.getURIPath()) != null) {
                    context = context.getContext(decorator.getURIPath());
                }
            }
            // get the dispatcher for the decorator
            RequestDispatcher dispatcher = context.getRequestDispatcher(decorator.getPage());
            // create a wrapper around the response
            dispatcher.include(request, response);

            // set the headers specified as decorator init params
            while (decorator.getInitParameterNames().hasNext()) {
                String initParam = (String) decorator.getInitParameterNames().next();
                if (initParam.startsWith("header.")) {
                    response.setHeader(initParam.substring(initParam.indexOf('.')), decorator.getInitParameter(initParam));
                }
            }

            request.removeAttribute(PAGE);
        } catch (RuntimeException e) {
            // added a print message here because otherwise Tomcat swallows
            // the error and you never see it = bad!
            if (Container.get() == Container.TOMCAT)
                e.printStackTrace();

            throw e;
        }
    }

    protected void applyDecoratorUsingVelocity(HttpServletRequest request, Page page, HttpServletResponse response, Decorator decorator)
            throws IOException {
        try {
            request.setAttribute(PAGE, page);
            Context context = VelocityManager.getInstance().createContext(ActionContext.getContext().getValueStack(), request, response);

            context.put("textUtils", TEXT_UTILS);
            context.put("fileUtil", FILE_UTILS);
            context.put("generalUtil", GENERAL_UTIL);

            context.put("page", page);
            context.put("title", page.getTitle());

            StringWriter buffer = new StringWriter();
            page.writeBody(OutputConverter.getWriter(buffer));
            context.put("body", buffer.toString());

            if (page instanceof HTMLPage) {
                HTMLPage htmlPage = (HTMLPage) page;
                buffer = new StringWriter();
                htmlPage.writeHead(OutputConverter.getWriter(buffer));
                context.put("head", buffer.toString());
            }

            //Rendering the content
            VelocityEngine velocityEngine = VelocityManager.getInstance().getVelocityEngine();

            Template template = velocityEngine.getTemplate(decorator.getPage());
            StringWriter tempWriter = new StringWriter();
            template.merge(context, tempWriter);
            String renderResult = tempWriter.toString();

            response.getWriter().print(renderResult);

            while (decorator.getInitParameterNames().hasNext()) {
                String initParam = (String) decorator.getInitParameterNames().next();
                if (initParam.startsWith("header."))
                    response.setHeader(initParam.substring(initParam.indexOf('.')), decorator.getInitParameter(initParam));
            }

            request.removeAttribute(PAGE);
        } catch (RuntimeException e) {
            if (Container.get() == 1)
                e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
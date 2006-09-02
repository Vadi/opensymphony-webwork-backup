/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapperFactory;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main dispatcher servlet in WebWork2 which acts as the controller in the MVC paradigm. <p>
 * <p/>
 * When a request enters the servlet the following things will happen: <ol>
 * <p/>
 * <li>The action name is parsed from the servlet path (i.e., /foo/bar/MyAction.action -> MyAction).</li>
 * <li>A context consisting of the request, response, parameters, session and application
 * properties is created.</li>
 * <li>An XWork <tt>ActionProxy</tt> object is instantiated (wraps an <tt>Action</tt>) using the action name, path,
 * and context then executed.</li>
 * <li>Action output will channel back through the response to the user.</li></ol>
 * <p/>
 * Any errors occurring during the action execution will result in a
 * {@link javax.servlet.http.HttpServletResponse#SC_INTERNAL_SERVER_ERROR} error and any resource errors
 * (i.e., invalid action name or missing JSP page) will result in a
 * {@link javax.servlet.http.HttpServletResponse#SC_NOT_FOUND} error. <p>
 * <p/>
 * Instead of traditional servlet init params this servlet will initialize itself using WebWork2 properties.
 * The following properties are used upon initialization: <ul>
 * <p/>
 * <li><tt>webwork.configuration.xml.reload</tt>: if and only if set to <tt>true</tt> then the xml configuration
 * files (action definitions, interceptor definitions, etc) will be reloaded for each request. This is
 * useful for development but should be disabled for production deployment.</li>
 * <li><tt>webwork.multipart.saveDir</tt>: The path used for temporarily uploaded files. Defaults to the
 * temp path specified by the app server.</li>
 * <li><tt>webwork.multipart.maxSize</tt>: sets the maximum allowable multipart request size
 * in bytes. If the size was not specified then {@link java.lang.Integer#MAX_VALUE} will be used
 * (essentially unlimited so be careful).</li></ul>
 * <p/>
 *
 * @author <a href="mailto:rickard@middleware-company.com">Rickard �berg</a>
 * @author <a href="mailto:matt@smallleap.com">Matt Baldree</a>
 * @author Jason Carreira
 * @author <a href="mailto:cameron@datacodex.net">Cameron Braid</a>
 * @author Bill Lynch
 * @see ServletDispatcherResult
 * @deprecated use {@link FilterDispatcher} instead
 */
public class ServletDispatcher extends HttpServlet implements WebWorkStatics {

    /**
     * Logger for this class.
     */
    protected static final Log LOG = LogFactory.getLog(ServletDispatcher.class);

    /**
     * Initalizes the servlet. Please read the {@link ServletDispatcher class documentation} for more
     * detail. <p>
     *
     * @param servletConfig the ServletConfig object.
     * @throws ServletException if an error occurs during initialization.
     */
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        DispatcherUtils.initialize(getServletContext());
    }

    /**
     * Services the request by determining the desired action to load, building the action context and
     * then executing the action. This handles all servlet requests including GETs and POSTs. <p>
     * <p/>
     * This method also transparently handles multipart requests.
     *
     * @param request  the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @throws ServletException if an error occurs while loading or executing the action.
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        // prepare the request no matter what - this ensures that the proper character encoding
        // is used before invoking the mapper (see WW-9127)
        DispatcherUtils du = DispatcherUtils.getInstance();
        du.prepare(request, response);

        try {
            request = du.wrapRequest(request, getServletContext());
        } catch (IOException e) {
            String message = "Could not wrap servlet request with MultipartRequestWrapper!";
            LOG.error(message, e);
            throw new ServletException(message, e);
        }
        
        ActionMapping mapping = ActionMapperFactory.getMapper().getMapping(request);
        if (mapping == null) {
            try {
                response.sendError(404);
            } catch (IOException e) {
                LOG.error("Could not send 404 after not finding any ActionMapping", e);
            }
            return;
        }

        du.serviceAction(request, response, getServletContext(), mapping);
    }
}

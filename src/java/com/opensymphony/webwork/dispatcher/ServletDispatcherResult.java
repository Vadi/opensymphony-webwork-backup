/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;


/**
 * Pulls the HttpServletRequest and HttpServletResponse objects from the
 * action context and creates a RequestDispatcher to the location
 * specified as the parameter "location" and then formwards it. The
 * following params are required:
 * <ul>
 * <li>location - the URL to use when creating the RequestDispatcher</li>
 * </ul>
 *
 * @author $Author$
 * @version $Revision$
 */
public class ServletDispatcherResult extends WebWorkResultSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(ServletDispatcherResult.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    public void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Forwarding to location " + finalLocation);
        }

        PageContext pageContext = (PageContext) invocation.getInvocationContext().get(PAGE_CONTEXT);

        if (pageContext != null) {
            pageContext.include(finalLocation);
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            RequestDispatcher dispatcher = request.getRequestDispatcher(finalLocation);

            // if the view doesn't exist, let's do a 404
            if (dispatcher == null) {
                response.sendError(404, "result '" + finalLocation + "' not found");
                return;
            }

            // If we're included, then include the view
            // Otherwise do forward 
            // This allow the page to, for example, set content type 
            if (!response.isCommitted() && (request.getAttribute("javax.servlet.include.servlet_path") == null)) {
                request.setAttribute("webwork.view_uri", finalLocation);
                request.setAttribute("webwork.request_uri", request.getRequestURI());

                dispatcher.forward(request, response);
            } else {
                dispatcher.include(request, response);
            }
        }
    }
}

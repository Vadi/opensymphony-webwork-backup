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
 *  <li>location - the URL to use when creating the RequestDispatcher</li>
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

        // todo check back on this as this the only reference to PageContext in this manner
        PageContext pageContext = (PageContext) ServletActionContext.getContext().get("javax.servlet.jsp.PageContext");

        if (pageContext != null) {
            pageContext.include(finalLocation);
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            RequestDispatcher dispatcher = request.getRequestDispatcher(finalLocation);

            // If we're included, then include the view 
            // Otherwise do forward 
            // This allow the page to, for example, set content type 
            if (!response.isCommitted() && (request.getAttribute("javax.servlet.include.servlet_path") == null)) {
                dispatcher.forward(request, response);
            } else {
                dispatcher.include(request, response);
            }
        }
    }
}

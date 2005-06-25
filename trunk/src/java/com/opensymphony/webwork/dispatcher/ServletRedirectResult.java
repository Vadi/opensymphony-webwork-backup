/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapperFactory;
import com.opensymphony.xwork.ActionInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Calls the {@link HttpServletResponse#sendRedirect(String) sendRedirect} method to the location specified. <p>
 * <p/>
 * This result follows the same rules from {@link WebWorkResultSupport}.
 *
 * @author Patrick Lightbody
 */
public class ServletRedirectResult extends WebWorkResultSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(ServletRedirectResult.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected boolean prependServletContext = true;

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Sets whether or not to prepend the servlet context path to the redirected URL.
     *
     * @param prependServletContext <tt>true</tt> to prepend the location with the servlet context path,
     *                              <tt>false</tt> otherwise.
     */
    public void setPrependServletContext(boolean prependServletContext) {
        this.prependServletContext = prependServletContext;
    }

    /**
     * Redirects to the location specified by calling {@link HttpServletResponse#sendRedirect(String)}.
     *
     * @param finalLocation the location to redirect to.
     * @param invocation    an encapsulation of the action execution state.
     * @throws Exception if an error occurs when redirecting.
     */
    protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        if (isPathUrl(finalLocation)) {
            if (!finalLocation.startsWith("/")) {
                String namespace = ActionMapperFactory.getMapper().getMapping(request).getNamespace();

                if ((namespace != null) && (namespace.length() > 0)) {
                    finalLocation = namespace + "/" + finalLocation;
                } else {
                    finalLocation = "/" + finalLocation;
                }
            }

            // if the URL's are relative to the servlet context, append the servlet context path
            if (prependServletContext && (request.getContextPath() != null) && (request.getContextPath().length() > 0)) {
                finalLocation = request.getContextPath() + finalLocation;
            }

            finalLocation = response.encodeRedirectURL(finalLocation);
        }

        if (log.isDebugEnabled()) {
            log.debug("Redirecting to finalLocation " + finalLocation);
        }

        response.sendRedirect(finalLocation);
    }

    private static boolean isPathUrl(String url) {
        // filter out "http:", "https:", "mailto:", "file:", "ftp:"
        // since the only valid places for : in URL's is before the path specification
        // either before the port, or after the protocol
        return (url.indexOf(':') == -1);
    }
}

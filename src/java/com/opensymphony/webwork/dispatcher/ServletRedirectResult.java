/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkStatics;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Pulls the HttpServletResponse object from the action context,
 * and calls sendRedirect() using the location specified as the
 * parameter "location". The following params are required:
 * <ul>
 *  <li>location - the URL to use when calling sendRedirect()</li>
 * </ul>
 *
 * @author $Author$
 * @version $Revision$
 */
public class ServletRedirectResult implements Result, WebWorkStatics {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(ServletRedirectResult.class);
    public static final String DEFAULT_PARAM = "location";

    //~ Instance fields ////////////////////////////////////////////////////////

    private String location;
    private boolean prependServletContext = true;
    private boolean parse = true;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setPrependServletContext(boolean prependServletContext) {
        this.prependServletContext = prependServletContext;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setParse(boolean parse) {
        this.parse = parse;
    }

    public void execute(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        if (parse) {
            OgnlValueStack stack = ActionContext.getContext().getValueStack();
            location = TextParseUtil.translateVariables(location, stack);
        }

        String redirectLocation = location;

        if (isPathUrl(redirectLocation))
    	{
    		
	        if (!redirectLocation.startsWith("/")) {
	
	            String actionPath = request.getServletPath();
	            String namespace = ServletDispatcher.getNamespaceFromServletPath(actionPath);
	
	            if ((namespace != null) && (namespace.length() > 0)) {
	                redirectLocation = namespace + "/" + redirectLocation;
	            }
	            else {
	            	redirectLocation = "/" + redirectLocation;
	            }
	
	        }
	
			// if the URL's are relative to the servlet context, append the servlet context path 
			if (prependServletContext && request.getContextPath() != null && request.getContextPath().length() > 0)
			{
				redirectLocation = request.getContextPath() + redirectLocation; 
			}

			redirectLocation = response.encodeRedirectURL(redirectLocation);
		}

        if (log.isDebugEnabled()) {
            log.debug("Redirecting to location " + redirectLocation);
        }

        response.sendRedirect(redirectLocation);
    }

	private static boolean isPathUrl(String url)
	{
		// filter out "http:", "https:", "mailto:", "file:", "ftp:"
		// since the only valid places for : in URL's is before the path specification
		// either before the port, or after the protocol
		return (url.indexOf(':') == -1);
	}
}

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

    //~ Instance fields ////////////////////////////////////////////////////////

    private String location;
    private boolean parse = true;

    //~ Methods ////////////////////////////////////////////////////////////////

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

        if (log.isDebugEnabled()) {
            log.debug("Redirecting to location " + location);
        }

        if (location.startsWith("/")) {
            response.sendRedirect(location);
        } else {
            response.sendRedirect(request.getContextPath() + location);
        }
    }
}

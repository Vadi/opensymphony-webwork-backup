/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.components.Include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;


/**
 * Include a servlet's output (result of servlet or a JSP page).
 *
 * @author Rickard Oberg (rickard@dreambean.com)
 * @author <a href="mailto:scott@atlassian.com">Scott Farquhar</a>
 * @version $Revision$
 */
public class IncludeTag extends ParametereizedBodyTagSupport {
    protected Include include;
    protected String value;

    public int doEndTag() throws JspException {
        include.addAllParameters(getParameters());
        include.end(pageContext.getOut());

        return EVAL_PAGE;
    }

    public int doStartTag() throws JspException {
        include = new Include(getStack(),
                (HttpServletRequest) pageContext.getRequest(),
                (HttpServletResponse) pageContext.getResponse());
        include.setValue(value);
        include.start(pageContext.getOut());

        return EVAL_BODY_BUFFERED;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

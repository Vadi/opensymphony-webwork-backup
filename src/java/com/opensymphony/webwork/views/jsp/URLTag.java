/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.views.util.UrlHelper;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;


/**
 * This tag is used to create a URL. You can use the "param" tag inside the
 * body to provide additional request params.
 *
 * @author Rickard Öberg (rickard@dreambean.com)
 * @author Brock Bulger (brockman_bulger@hotmail.com)
 * @author Jason Carreira (jason@zenfrog.com)
 * @version $Revision$
 */
public class URLTag extends ParameterizedTagSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(URLTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String page;
    protected String value;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setPage(String page) {
        this.page = page;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        String result = UrlHelper.buildUrl(page, request, response, params);

        String id = getId();

        if (id != null) {
            pageContext.setAttribute(id, value);
            pageContext.setAttribute(id, value, PageContext.REQUEST_SCOPE);
        } else {
            try {
                pageContext.getOut().print(result);
            } catch (IOException ex) {
                throw new JspException("IOError: " + ex.getMessage());
            }
        }

        return SKIP_BODY;
    }

    public int doStartTag() throws JspException {
        OgnlValueStack vs = ActionContext.getContext().getValueStack();

        if ((vs != null) && (value != null)) {
            Object o = vs.findValue(value);

            if (o != null) {
                value = o.toString();
            }
        }

        params = null;

        if (value != null) {
            page = value;
        }

        // if no value was given, we can assume we're going to the same page,
        // ... so we better get all the request params so we can re-set them.
        if (page == null) {
            HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();

            try {
                Map requestParams = req.getParameterMap();

                if (requestParams != null) {
                    params = new HashMap(requestParams);
                }
            } catch (Exception ex) {
                log.warn("Unable to put request params (" + req.getQueryString() + ") into parameter map.", ex);
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        this.params = null;
        this.value = null;
        this.page = null;
    }
}

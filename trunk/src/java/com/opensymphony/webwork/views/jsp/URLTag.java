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

import com.opensymphony.webwork.components.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;


/**
 * This tag is used to create a URL.
 * You can use the "param" tag inside the body to provide
 * additional request parameters.
 *
 * @author Rickard Öberg (rickard@dreambean.com)
 * @version $Revision$
 * @see com.opensymphony.webwork.views.jsp.ParamTag
 */
public class URLTag extends ParametereizedBodyTagSupport {
    protected URL url;

    protected String includeParams;
    protected String scheme;
    protected String value;
    protected String encode;
    protected String includeContext;

    public int doEndTag() throws JspException {
        url.addAllParameters(getParameters());
        url.end(pageContext.getOut());

        return EVAL_PAGE;
    }

    public int doStartTag() throws JspException {
        url = new URL(getStack(),
                (HttpServletRequest) pageContext.getRequest(),
                (HttpServletResponse) pageContext.getResponse());
        url.setIncludeParams(includeParams);
        url.setScheme(scheme);
        url.setValue(value);
        if (encode != null) {
            url.setEncode(Boolean.valueOf(encode).booleanValue());
        }
        if (includeContext != null) {
            url.setIncludeContext(Boolean.valueOf(includeContext).booleanValue());
        }
        url.start(pageContext.getOut());

        return EVAL_BODY_BUFFERED;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public void setIncludeContext(String includeContext) {
        this.includeContext = includeContext;
    }

    public void setIncludeParams(String aName) {
        includeParams = aName;
    }

    public void setScheme(String aScheme) {
        scheme = aScheme;
    }

    public void setValue(String aName) {
        value = aName;
    }
}

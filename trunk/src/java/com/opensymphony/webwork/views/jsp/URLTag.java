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

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.URL;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @jsp.tag name="url" bodycontent="JSP"
 * @see URL
 */
public class URLTag extends ComponentTagSupport {
    protected String includeParams;
    protected String scheme;
    protected String value;
    protected String encode;
    protected String includeContext;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new URL(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        URL url = (URL) component;
        url.setIncludeParams(includeParams);
        url.setScheme(scheme);
        url.setValue(value);
        if (encode != null) {
            url.setEncode(Boolean.valueOf(encode).booleanValue());
        }
        if (includeContext != null) {
            url.setIncludeContext(Boolean.valueOf(includeContext).booleanValue());
        }
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setEncode(String encode) {
        this.encode = encode;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setIncludeContext(String includeContext) {
        this.includeContext = includeContext;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setIncludeParams(String name) {
        includeParams = name;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setValue(String value) {
        this.value = value;
    }
}

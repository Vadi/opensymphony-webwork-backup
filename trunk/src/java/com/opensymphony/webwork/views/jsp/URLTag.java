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

import com.opensymphony.webwork.views.util.UrlHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;


/**
 * This tag is used to create a URL.
 * You can use the "param" tag inside the body to provide
 * additional request parameters.
 *
 * @see com.opensymphony.webwork.views.jsp.ParamTag
 * @author Rickard Öberg (rickard@dreambean.com)
 * @version $Revision$
 */
public class URLTag extends ParametereizedBodyTagSupport {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log LOG = LogFactory.getLog(URLTag.class);

    // Public --------------------------------------------------------

    /**
     * The includeParams attribute may have the value 'none', 'get' or 'all'.
     * It is used when the url tag is used without a value or page attribute.
     * Its value is looked up on the ValueStack
     * If no includeParams is specified then 'get' is used.
     * none - include no parameters in the URL
     * get  - include only GET parameters in the URL (default)
     * all  - include both GET and POST parameters in the URL
     */
    public static final String NONE = "none";
    public static final String GET = "get";
    public static final String ALL = "all";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String includeParamsAttr;
    protected String value;

    // Attributes ----------------------------------------------------
    protected String valueAttr;
    protected String scheme = "http";
    protected boolean includeContext = true;
    protected boolean encodeResult = true;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setIncludeParams(String aName) {
        includeParamsAttr = aName;
    }

    public void setValue(String aName) {
        valueAttr = aName;
    }

    public void setScheme(String aScheme) {
        scheme = aScheme;
    }

    public void setIncludeContext(boolean includeContext) {
        this.includeContext = includeContext;
    }

    public void setEncodeResult(boolean encodeResult) {
        this.encodeResult = encodeResult;
    }

    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        String result = UrlHelper.buildUrl(value, request, response, params, scheme, includeContext, encodeResult);

        String id = getId();

        if (id != null) {
            pageContext.setAttribute(id, result);
            pageContext.setAttribute(id, result, PageContext.REQUEST_SCOPE);
        } else {
            try {
                pageContext.getOut().write(result);
            } catch (IOException _ioe) {
                throw new JspException("IOError: " + _ioe.getMessage());
            }
        }

        return EVAL_PAGE;
    }

    // BodyTag implementation ----------------------------------------
    public int doStartTag() throws JspException {
        if (valueAttr != null) {
            value = findString(valueAttr);
        }

        // Clear the params map if it has been instantiated before
        if (params != null) {
            params.clear();
        }

        //no explicit url set so attach params from current url, do
        //this at start so body params can override any of these they wish.
        if (value == null) {
            try {
                if (params == null) {
                    params = new HashMap();
                }

                String includeParams = null;

                if (includeParamsAttr != null) {
                    includeParams = findString(includeParamsAttr);
                }

                if ((includeParams == null) || includeParams.equals(GET)) {
                    // Parse the query string to make sure that the parameters come from the query, and not some posted data
                    HttpServletRequest req = ((HttpServletRequest) pageContext.getRequest());
                    String query = req.getQueryString();

                    if (query != null) {
                        // Remove possible #foobar suffix
                        int idx = query.lastIndexOf('#');

                        if (idx != -1) {
                            query = query.substring(0, idx - 1);
                        }

                        params.putAll(HttpUtils.parseQueryString(query));
                    }
                } else if (includeParams.equals(ALL)) {
                    params.putAll(pageContext.getRequest().getParameterMap());
                } else if (!includeParams.equals(NONE)) {
                    LOG.warn("Unknown value for includeParams parameter to URL tag: " + includeParams);
                }
            } catch (Exception e) {
                LOG.warn("Unable to put request parameters (" + ((HttpServletRequest) pageContext.getRequest()).getQueryString() + ") into parameter map.", e);
            }
        }

        return EVAL_BODY_BUFFERED;
    }
}

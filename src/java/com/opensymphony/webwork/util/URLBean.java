package com.opensymphony.webwork.util;

import com.opensymphony.webwork.views.util.UrlHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * User: plightbo
 * Date: Nov 16, 2003
 * Time: 4:13:11 PM
 */
public class URLBean {
    String page;
    HttpServletRequest request;
    HttpServletResponse response;
    HashMap params;

    public void setPage(String page) {
        this.page = page;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public URLBean addParameter(String name, Object value) {
        if (params == null) {
            params = new HashMap();
        }

        if (value == null) {
            params.remove(name);
        } else {
            params.put(name, value.toString());
        }

        return this;
    }

    public String getURL() {
        if (page == null)
        {
           // No particular page requested, so go to "same page"
           // Add query params to parameters
           params.putAll(request.getParameterMap());
        }

        return UrlHelper.buildUrl(page, request, response, params);
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.views.util.UrlHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * FormTag
 * @author Jason Carreira
 * Created Apr 1, 2003 8:19:47 PM
 */
public class FormTag extends AbstractClosingUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    final public static String OPEN_TEMPLATE = "form.vm";
    final public static String TEMPLATE = "form-close.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    String action;
    String enctype;
    String method;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAction(String action) {
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String result = UrlHelper.buildUrl(action, request, response, null);

        this.action = result;
    }

    public String getAction() {
        return action;
    }

    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }

    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }

    public String getEnctype() {
        return enctype;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    /**
    * Clears all the instance variables to allow this instance to be reused.
    */
    public void release() {
        super.release();
        method = null;
        enctype = null;
        action = null;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

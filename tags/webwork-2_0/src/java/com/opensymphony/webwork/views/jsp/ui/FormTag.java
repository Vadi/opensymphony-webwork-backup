/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.views.util.UrlHelper;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

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

    String actionAttr;
    String enctypeAttr;
    String methodAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAction(String action) {
        this.actionAttr = action;
    }

    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }

    public void setEnctype(String enctype) {
        this.enctypeAttr = enctype;
    }

    public void setMethod(String method) {
        this.methodAttr = method;
    }

    public void evaluateExtraParams(OgnlValueStack stack) {
        super.evaluateExtraParams(stack);

        if (actionAttr != null) {
            /**
             * If called from a JSP, pageContext will not be null.  otherwise, we'll get request and response from the
             * ServletActionContext.
             *
             * todo - determine if there's any reason we can't just always use ServletActionContext
             */
            HttpServletResponse response = null;
            HttpServletRequest request = null;

            if (pageContext != null) {
                response = (HttpServletResponse) pageContext.getResponse();
                request = (HttpServletRequest) pageContext.getRequest();
            } else {
                request = ServletActionContext.getRequest();
                response = ServletActionContext.getResponse();
            }

            Object actionObj = findValue(actionAttr, String.class);

            if (actionObj != null) {
                String result = UrlHelper.buildUrl(actionObj.toString(), request, response, null);
                addParam("action", result);
            }
        }

        if (enctypeAttr != null) {
            addParam("enctype", findValue(enctypeAttr, String.class));
        }

        if (methodAttr != null) {
            addParam("method", findValue(methodAttr, String.class));
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

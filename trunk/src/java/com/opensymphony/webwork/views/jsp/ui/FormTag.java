/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.validators.ScriptValidationAware;
import com.opensymphony.webwork.views.util.UrlHelper;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.validator.FieldValidator;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

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
    String validateAttr;
    Map fieldValidators;
    Map fieldParameters;

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

    public void setValidate(String validate) {
        this.validateAttr = validate;
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
                addParameter("action", result);
            }
        }

        if (enctypeAttr != null) {
            addParameter("enctype", findValue(enctypeAttr, String.class));
        }

        if (methodAttr != null) {
            addParameter("method", findValue(methodAttr, String.class));
        }

        if (validateAttr != null) {
            addParameter("validate", findValue(validateAttr, Boolean.class));
        }

        if (fieldValidators != null) {
            StringBuffer js = new StringBuffer();
            js.append("form = document.forms['" + getParameters().get("name") + "'];\n");
            for (Iterator iterator = fieldValidators.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry entry = (Map.Entry) iterator.next();
                if (entry.getValue() instanceof ScriptValidationAware) {
                    ScriptValidationAware jsa = (ScriptValidationAware) entry.getValue();
                    Map params = (Map) fieldParameters.get(entry.getKey());
                    js.append(jsa.validationScript(params));
                    js.append('\n');
                }
            }

            addParameter("javascriptValidation", js.toString());
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void registerValidator(Object name, FieldValidator fieldValidator, Map params) {
        if (fieldValidators == null) {
            fieldValidators = new HashMap();
        }

        if (fieldParameters == null) {
            fieldParameters = new HashMap();
        }

        fieldValidators.put(name, fieldValidator);
        fieldParameters.put(name, params);
    }

    public Map getFieldValidators() {
        return fieldValidators;
    }

    public Map getFieldParameters() {
        return fieldParameters;
    }
}

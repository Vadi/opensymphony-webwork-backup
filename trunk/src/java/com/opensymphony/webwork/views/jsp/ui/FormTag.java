/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.validators.ScriptValidationAware;
import com.opensymphony.webwork.views.util.UrlHelper;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.validator.FieldValidator;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;

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
    String namespaceAttr;
    String enctypeAttr;
    String methodAttr;
    String validateAttr;
    List fieldValidators;
    List fieldParameters;
    Class actionClass;
    String actionName;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAction(String action) {
        this.actionAttr = action;
    }

    public void setNamespace(String namespace) {
        this.namespaceAttr = namespace;
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

            String action = (String) findValue(actionAttr, String.class);
            String namespace = (String) findValue(namespaceAttr, String.class);
            if (namespace == null) {
                namespace = "";
            }

            ActionConfig actionConfig = ConfigurationManager.getConfiguration().getRuntimeConfiguration().getActionConfig(namespace, action);
            if (actionConfig != null) {
                actionClass = actionConfig.getClazz();
                actionName = action;
                String result = UrlHelper.buildUrl(namespace + "/" + action + "." + Configuration.get("webwork.action.extension"), request, response, null);
                addParameter("action", result);
            } else if (action != null) {
                String result = UrlHelper.buildUrl(action, request, response, null);
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
            // loop backwards so that the first elements are validated first
            for (int i = 0; i < fieldValidators.size(); i++) {
                ScriptValidationAware sva = (ScriptValidationAware) fieldValidators.get(i);
                Map params = (Map) fieldParameters.get(i);
                js.append(sva.validationScript(params));
                js.append('\n');
            }
            addParameter("javascriptValidation", js.toString());
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void registerValidator(Object name, ScriptValidationAware sva, Map params) {
        if (fieldValidators == null) {
            fieldValidators = new ArrayList();
            fieldParameters = new ArrayList();
        }

        fieldValidators.add(sva);
        fieldParameters.add(params);
    }

    public Class getActionClass() {
        return actionClass;
    }

    public String getActionName() {
        return actionName;
    }
}

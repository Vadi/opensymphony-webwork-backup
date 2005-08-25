/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Form;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * FormTag
 *
 * @author Jason Carreira
 *         Created Apr 1, 2003 8:19:47 PM
 */
public class FormTag extends AbstractUITag {
    String action;
    String target;
    String enctype;
    String method;
    String namespace;
    String validate;
    String onsubmit;

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Form(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        Form form = ((Form) bean);
        form.setAction(action);
        form.setTarget(target);
        form.setEnctype(enctype);
        form.setMethod(method);
        form.setNamespace(namespace);
        form.setValidate(validate);
        form.setOnselect(onsubmit);
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public void setOnsubmit(String onsubmit) {
        this.onsubmit = onsubmit;
    }
}

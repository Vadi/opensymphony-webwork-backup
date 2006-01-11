/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Form;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @see Form
 */
public class FormTag extends AbstractClosingTag {
    protected String action;
    protected String target;
    protected String enctype;
    protected String method;
    protected String namespace;
    protected String validate;
    protected String onsubmit;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Form(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        Form form = ((Form) component);
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

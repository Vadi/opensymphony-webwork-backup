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
 * @jsp.tag name="form" bodycontent="JSP"
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

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setValidate(String validate) {
        this.validate = validate;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnsubmit(String onsubmit) {
        this.onsubmit = onsubmit;
    }
}

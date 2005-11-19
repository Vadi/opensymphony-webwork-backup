/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.TextField;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @jsp.tag name="textfield" bodycontent="JSP"
 * @see TextField
 */
public class TextFieldTag extends AbstractUITag {
    protected String maxlength;
    protected String readonly;
    protected String size;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new TextField(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        TextField textField = ((TextField) component);
        textField.setMaxLength(maxlength);
        textField.setReadonly(readonly);
        textField.setSize(size);
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setSize(String size) {
        this.size = size;
    }
}

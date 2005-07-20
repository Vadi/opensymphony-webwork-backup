/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.TextField;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author $Author$
 * @version $Revision$
 */
public class TextFieldTag extends AbstractUITag {

    protected String maxLength;
    protected String readonly;
    protected String size;

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new TextField(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        TextField textField = ((TextField) bean);
        textField.setMaxLength(maxLength);
        textField.setReadonly(readonly);
        textField.setSize(size);
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public void setSize(String size) {
        this.size = size;
    }
}

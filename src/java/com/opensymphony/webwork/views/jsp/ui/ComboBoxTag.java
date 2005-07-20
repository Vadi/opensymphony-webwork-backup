/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.ComboBox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author $Author$
 * @version $Revision$
 */
public class ComboBoxTag extends TextFieldTag {
    protected String list;

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new ComboBox(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        ((ComboBox) bean).setList(list);
    }

    public void setList(String list) {
        this.list = list;
    }
}

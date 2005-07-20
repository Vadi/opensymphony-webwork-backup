/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.Checkbox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class CheckboxTag extends AbstractUITag {
    protected String fieldValue;

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Checkbox(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        ((Checkbox) bean).setFieldValue(fieldValue);
    }

    public void setFieldValue(String aValue) {
        this.fieldValue = aValue;
    }
}

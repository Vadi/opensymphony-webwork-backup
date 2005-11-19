/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.DoubleSelect;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @jsp.tag name="doubleselect" bodycontent="JSP"
 * @see DoubleSelect
 */
public class DoubleSelectTag extends AbstractDoubleListTag {
    protected String emptyOption;
    protected String headerKey;
    protected String headerValue;
    protected String multiple;
    protected String size;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new DoubleSelect(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        DoubleSelect doubleSelect = ((DoubleSelect) component);
        doubleSelect.setEmptyOption(emptyOption);
        doubleSelect.setHeaderKey(headerKey);
        doubleSelect.setHeaderValue(headerValue);
        doubleSelect.setMultiple(multiple);
        doubleSelect.setSize(size);
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setEmptyOption(String emptyOption) {
        this.emptyOption = emptyOption;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setSize(String size) {
        this.size = size;
    }
}

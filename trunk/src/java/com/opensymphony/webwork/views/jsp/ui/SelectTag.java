/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.Select;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class SelectTag extends AbstractListTag {
    protected String emptyOption;
    protected String headerKey;
    protected String headerValue;
    protected String multiple;
    protected String size;

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Select(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        Select select = ((Select) bean);
        select.setEmptyOption(emptyOption);
        select.setHeaderKey(headerKey);
        select.setHeaderValue(headerValue);
        select.setMultiple(multiple);
        select.setSize(size);
    }

    public void setEmptyOption(String emptyOption) {
        this.emptyOption = emptyOption;
    }

    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public void setSize(String size) {
        this.size = size;
    }

}

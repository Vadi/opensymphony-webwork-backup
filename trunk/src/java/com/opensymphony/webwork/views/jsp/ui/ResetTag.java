/*
 * The IT-neering.net Commercial Software License, Version 1.0
 *
 * Copyright (c) 2005 Rene Gielen. All rights reserved.
 *
 * IT-NEERING.NET PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * For more information on IT-neering.net, please see <http://it-neering.net/>.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Reset;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @see com.opensymphony.webwork.components.Reset
 */
public class ResetTag extends AbstractUITag {
    protected String action;
    protected String method;
    protected String align;
    protected String type;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Reset(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        Reset reset = ((Reset) component);
        reset.setAction(action);
        reset.setMethod(method);
        reset.setAlign(align);
        reset.setType(type);
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setType(String type) {
        this.type = type;
    }

}

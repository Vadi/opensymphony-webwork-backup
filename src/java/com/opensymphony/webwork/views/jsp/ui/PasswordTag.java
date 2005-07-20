/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.Password;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class PasswordTag extends TextFieldTag {

    protected String showPassword;

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Password(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        ((Password) bean).setShowPassword(showPassword);
    }

    /**
     * @deprecated use showPassword()
     * @param aShowPasswordAttr
     */
    public void setShow(String aShowPasswordAttr) {
        this.showPassword = aShowPasswordAttr;
    }

    public void setShowPassword(String showPassword) {
        this.showPassword = showPassword;
    }
}

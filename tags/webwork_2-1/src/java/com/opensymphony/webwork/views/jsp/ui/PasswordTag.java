/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class PasswordTag extends TextFieldTag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the PasswordTag
     */
    final public static String TEMPLATE = "password.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String showPasswordAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setShow(String aShowPasswordAttr) {
        this.showPasswordAttr = aShowPasswordAttr;
    }

    public void evaluateExtraParams(OgnlValueStack stack) {
        super.evaluateExtraParams(stack);

        if (showPasswordAttr != null) {
            addParameter("showPassword", findValue(showPasswordAttr, Boolean.class));
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

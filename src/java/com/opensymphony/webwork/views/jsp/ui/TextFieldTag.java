/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.jsp.JspException;


/**
 * @author $Author$
 * @version $Revision$
 */
public class TextFieldTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the TextFieldTag
     */
    final public static String TEMPLATE = "textfield.vm";

    //~ Methods ////////////////////////////////////////////////////////////////

    protected String sizeAttr;

    public void setSize(String size) {
        this.sizeAttr = size;
    }

    void evaluateParams(OgnlValueStack stack) {
        if (sizeAttr != null) {
            addParam("size", stack.findValue(sizeAttr, String.class));
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

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
    final public static String TEMPLATE = "text.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String sizeAttr;
    protected String maxLengthAttr;
    protected String readonlyAttr;
    protected String onkeyupAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setSize(String size) {
        this.sizeAttr = size;
    }

    public void setMaxlength(String aMaxLength) {
        this.maxLengthAttr = aMaxLength;
    }

    public void setReadonly(String readonly) {
        this.readonlyAttr = readonly;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyupAttr = onkeyup;
    }

    public void evaluateExtraParams(OgnlValueStack stack) {
        if (sizeAttr != null) {
            addParam("size", findValue(sizeAttr, String.class));
        }

        if (maxLengthAttr != null) {
            addParam("maxlength", findValue(maxLengthAttr, String.class));
        }

        if (readonlyAttr!= null) {
            addParam("readonly", findValue(readonlyAttr, Boolean.class));
        }

        if (onkeyupAttr!= null) {
            addParam("onkeyup", findValue(onkeyupAttr, String.class));
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

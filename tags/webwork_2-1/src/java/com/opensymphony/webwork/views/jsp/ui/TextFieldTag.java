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

    protected String maxLengthAttr;
    protected String onkeyupAttr;
    protected String readonlyAttr;
    protected String sizeAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setMaxlength(String aMaxLength) {
        this.maxLengthAttr = aMaxLength;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyupAttr = onkeyup;
    }

    public void setReadonly(String readonly) {
        this.readonlyAttr = readonly;
    }

    public void setSize(String size) {
        this.sizeAttr = size;
    }

    public void evaluateExtraParams(OgnlValueStack stack) {
        super.evaluateExtraParams(stack);

        if (sizeAttr != null) {
            addParameter("size", findValue(sizeAttr, String.class));
        }

        if (maxLengthAttr != null) {
            addParameter("maxlength", findValue(maxLengthAttr, String.class));
        }

        if (readonlyAttr != null) {
            addParameter("readonly", findValue(readonlyAttr, Boolean.class));
        }

        if (onkeyupAttr != null) {
            addParameter("onkeyup", findValue(onkeyupAttr, String.class));
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

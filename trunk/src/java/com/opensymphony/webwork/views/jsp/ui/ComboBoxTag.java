/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * User: plightbo
 * Date: Nov 3, 2003
 * Time: 9:31:30 PM
 */
public class ComboBoxTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the CheckboxTag
     */
    final public static String TEMPLATE = "combobox.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String list;
    protected String maxlengthAttr;
    protected String onkeyupAttr;
    protected String sizeAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setList(String aList) {
        this.list = aList;
    }

    public void setMaxlength(String aMaxlength) {
        this.maxlengthAttr = aMaxlength;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyupAttr = onkeyup;
    }

    public void setSize(String aSize) {
        this.sizeAttr = aSize;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    protected Class getValueClassType() {
        return Boolean.class; // for checkboxes, everything needs to end up as a Boolean
    }

    protected void evaluateExtraParams(OgnlValueStack stack) {
        if (list != null) {
            addParameter("list", findValue(list));
        }

        if (sizeAttr != null) {
            addParameter("size", findValue(sizeAttr));
        }

        if (maxlengthAttr != null) {
            addParameter("maxlength", findValue(maxlengthAttr));
        }

        if (onkeyupAttr != null) {
            addParameter("onkeyup", findValue(onkeyupAttr));
        }
    }
}

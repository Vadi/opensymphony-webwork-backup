/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author $Author$
 * @version $Revision$
 */
public class TextFieldTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the TextFieldTag
     */
    final public static String TEMPLATE = "text";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String maxLengthAttr;
    protected String readonlyAttr;
    protected String sizeAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setMaxlength(String aMaxLength) {
        this.maxLengthAttr = aMaxLength;
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
            addParameter("size", findString(sizeAttr));
        }

        if (maxLengthAttr != null) {
            addParameter("maxlength", findString(maxLengthAttr));
        }

        if (readonlyAttr != null) {
            addParameter("readonly", findValue(readonlyAttr, Boolean.class));
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

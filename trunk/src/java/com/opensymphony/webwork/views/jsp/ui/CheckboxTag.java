/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class CheckboxTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the CheckboxTag
     */
    final public static String TEMPLATE = "checkbox.vm";

    protected String fieldValueAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setFieldValue(String aValue) {
        this.fieldValueAttr = aValue;
    }

    protected void evaluateExtraParams(OgnlValueStack stack) {
        if (fieldValueAttr != null) {
            addParam("fieldValue", stack.findValue(fieldValueAttr, String.class));
        }

        // this is a duplicate of the super class, but we care about booleans with checkbox
        // ... the duplication might want to be removed someday
        Object name = getParams().get("name");
        if (valueAttr != null) {
            addParam("nameValue", stack.findValue(valueAttr, Boolean.class));
        } else if (name != null) {
            addParam("nameValue", stack.findValue(name.toString(), Boolean.class));
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

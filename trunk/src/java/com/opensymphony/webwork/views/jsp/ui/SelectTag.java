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
public class SelectTag extends AbstractListTag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the SelectTag
     */
    final public static String TEMPLATE = "select.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String emptyOptionAttr;
    protected String multipleAttr;
    protected String sizeAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setEmptyOption(String emptyOption) {
        this.emptyOptionAttr = emptyOption;
    }

    public void setMultiple(String multiple) {
        this.multipleAttr = multiple;
    }

    public void setSize(String size) {
        this.sizeAttr = size;
    }

    public void evaluateExtraParams(OgnlValueStack stack) {
        super.evaluateExtraParams(stack);

        if (emptyOptionAttr != null) {
            addParam("emptyOption", stack.findValue(emptyOptionAttr, String.class));
        }

        if (multipleAttr != null) {
            addParam("defaultKey", stack.findValue(multipleAttr, String.class));
        }

        if (sizeAttr != null) {
            addParam("size", stack.findValue(sizeAttr, String.class));
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

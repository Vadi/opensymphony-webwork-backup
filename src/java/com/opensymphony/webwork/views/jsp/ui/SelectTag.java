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
    protected String headerKeyAttr;
    protected String headerValueAttr;
    protected String multipleAttr;
    protected String sizeAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setEmptyOption(String emptyOption) {
        this.emptyOptionAttr = emptyOption;
    }

    public void setHeaderKey(String headerKey) {
        this.headerKeyAttr = headerKey;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValueAttr = headerValue;
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
            addParam("emptyOption", findValue(emptyOptionAttr, Boolean.class));
        }

        if (multipleAttr != null) {
            addParam("multiple", findValue(multipleAttr, Boolean.class));
        }

        if (sizeAttr != null) {
            addParam("size", findValue(sizeAttr, String.class));
        }

        if ((headerKeyAttr != null) && (headerValueAttr != null)) {
            addParam("headerKey", findValue(headerKeyAttr, String.class));
            addParam("headerValue", findValue(headerValueAttr, String.class));
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * @author <a href="mailto:m.bogaert@intrasoft.be">Mathias Bogaert</a>
 * @version $Id$
 */
public class DoubleSelectTag extends AbstractDoubleListTag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the DoubleSelectTag.
     */
    final public static String TEMPLATE = "doubleselect";

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
            addParameter("emptyOption", findValue(emptyOptionAttr, Boolean.class));
        }

        if (multipleAttr != null) {
            addParameter("multiple", findValue(multipleAttr, Boolean.class));
        }

        if (sizeAttr != null) {
            addParameter("size", findValue(sizeAttr, String.class));
        }

        if ((headerKeyAttr != null) && (headerValueAttr != null)) {
            addParameter("headerKey", findValue(headerKeyAttr, String.class));
            addParameter("headerValue", findValue(headerValueAttr, String.class));
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

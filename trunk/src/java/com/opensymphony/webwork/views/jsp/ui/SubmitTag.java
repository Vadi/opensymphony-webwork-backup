/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 * @version $Id$
 */
public class SubmitTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the LabelTag
     */
    final public static String TEMPLATE = "submit";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String alignAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAlign(String align) {
        this.alignAttr = align;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    protected void evaluateParams(OgnlValueStack stack) {
        if (alignAttr == null) {
            alignAttr = "'right'";
        }

        if (valueAttr == null) {
            valueAttr = "'Submit'";
        }

        super.evaluateParams(stack);

        addParameter("align", findValue(alignAttr, String.class));
    }
}

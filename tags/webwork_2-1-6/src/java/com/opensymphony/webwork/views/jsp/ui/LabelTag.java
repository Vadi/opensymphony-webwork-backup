/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class LabelTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the LabelTag
     */
    final public static String TEMPLATE = "label";

    //~ Methods ////////////////////////////////////////////////////////////////

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    protected void evaluateExtraParams(OgnlValueStack stack) {
        super.evaluateExtraParams(stack);

        // try value first, then name (this overrides the default behavior in the superclass)
        if (valueAttr != null) {
            addParameter("nameValue", findString(valueAttr));
        } else if (nameAttr != null) {
            String expr = nameAttr;
            if (ALT_SYNTAX) {
                expr = "%{" + expr + "}";
            }

            addParameter("nameValue", findString(expr));
        }
    }
}

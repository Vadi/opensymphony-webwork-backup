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
public class ComboBoxTag extends TextFieldTag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the CheckboxTag
     */
    final public static String TEMPLATE = "combobox";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String list;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setList(String list) {
        this.list = list;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateExtraParams(OgnlValueStack stack) {
        super.evaluateExtraParams(stack);

        if (list != null) {
            addParameter("list", findValue(list));
        }
    }
}

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
public class TextareaTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the TextareaTag
     */
    final public static String TEMPLATE = "textarea.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String colsAttr;
    protected String rowsAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setCols(String cols) {
        this.colsAttr = cols;
    }

    public void setRows(String rows) {
        this.rowsAttr = rows;
    }

    public void evaluateExtraParams(OgnlValueStack stack) {
        if (colsAttr != null) {
            addParam("cols", stack.findValue(colsAttr, String.class));
        }

        if (rowsAttr != null) {
            addParam("rows", stack.findValue(rowsAttr, String.class));
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


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

    private int cols;
    private int rows;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getCols() {
        return cols;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return rows;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

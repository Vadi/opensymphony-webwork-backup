/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


/**
 * @author $Author$
 * @version $Revision$
 */
public class TextFieldTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the TextFieldTag
     */
    final public static String TEMPLATE = "textfield.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    private int size;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        this.size = 0;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

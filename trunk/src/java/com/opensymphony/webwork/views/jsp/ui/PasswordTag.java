/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class PasswordTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the PasswordTag
     */
    final public static String TEMPLATE = "password.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    private int size;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Object Size(int size) {
        this.size = size;

        return this;
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

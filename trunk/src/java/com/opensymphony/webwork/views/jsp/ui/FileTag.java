/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


/**
 * @author $Author$
 * @version $Revision$
 */
public class FileTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the FileTag
     */
    final public static String TEMPLATE = "file.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    /**
     * Some browsers support the ability to restrict the kinds of files
     * (that can be attached to the contents of a form) using an ACCEPT
     * attribute.
     */
    private String accept;
    private int size;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAccept() {
        return accept;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Object Accept(String accept) {
        this.accept = accept;

        return this;
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
        this.accept = null;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


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

    private boolean emptyOption;
    private boolean multiple;
    private int size;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setEmptyOption(boolean emptyOption) {
        this.emptyOption = emptyOption;
    }

    public boolean isEmptyOption() {
        return emptyOption;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Object EmptyOption(boolean emptyOption) {
        setEmptyOption(emptyOption);

        return this;
    }

    public Object Multiple(boolean multiple) {
        setMultiple(multiple);

        return this;
    }

    public Object Size(int size) {
        setSize(size);

        return this;
    }

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        this.emptyOption = false;
        this.multiple = false;
        this.size = 0;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

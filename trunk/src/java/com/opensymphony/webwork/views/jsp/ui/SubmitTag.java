/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


/**
 *
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a> */
public class SubmitTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the LabelTag
     */
    final public static String TEMPLATE = "submit.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    private String align;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAlign(String align) {
        this.align = align;
    }

    public String getAlign() {
        return align;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

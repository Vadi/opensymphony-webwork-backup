/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


/**
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 * @version $Id$
 */
public class HiddenTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the LabelTag
     */
    final public static String TEMPLATE = "hidden";

    //~ Methods ////////////////////////////////////////////////////////////////

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

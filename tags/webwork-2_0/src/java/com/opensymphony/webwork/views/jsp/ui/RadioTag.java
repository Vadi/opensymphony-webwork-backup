/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class RadioTag extends AbstractListTag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the RadioTag
     */
    final public static String TEMPLATE = "radiomap.vm";

    //~ Methods ////////////////////////////////////////////////////////////////

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

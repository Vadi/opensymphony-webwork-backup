/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


/**
 * @author <a href="mailto:pathos@pandora.be">Mathias Bogaert</a>
 * @version $Id$
 */
public class CheckboxListTag extends AbstractListTag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the RadioTag
     */
    final public static String TEMPLATE = "checkboxlist";

    //~ Methods ////////////////////////////////////////////////////////////////

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

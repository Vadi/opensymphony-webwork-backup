/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;


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
    protected String acceptAttr;
    protected String sizeAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAccept(String accept) {
        this.acceptAttr = accept;
    }

    public void setSize(String size) {
        this.sizeAttr = size;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    protected void evaluateParams(OgnlValueStack stack) {
        super.evaluateParams(stack);

        if (acceptAttr != null) {
            addParam("accept", findValue(acceptAttr, String.class));
        }

        if (sizeAttr != null) {
            addParam("size", findValue(sizeAttr, String.class));
        }
    }
}

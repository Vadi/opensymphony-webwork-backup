/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.File;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @jsp.tag name="file" bodycontent="JSP"
 * @see File
 */
public class FileTag extends AbstractUITag {
    protected String accept;
    protected String size;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new File(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        File file = ((File) component);
        file.setAccept(accept);
        file.setSize(size);
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setAccept(String accept) {
        this.accept = accept;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setSize(String size) {
        this.size = size;
    }
}

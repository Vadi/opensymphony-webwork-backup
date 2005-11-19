/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Param;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @jsp.tag name="param" bodycontent="JSP"
 * @see Param
 */
public class ParamTag extends ComponentTagSupport {
    protected String name;
    protected String value;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Param(stack);
    }

    protected void populateParams() {
        super.populateParams();

        Param param = (Param) component;
        param.setName(name);
        param.setValue(value);
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setValue(String value) {
        this.value = value;
    }
}

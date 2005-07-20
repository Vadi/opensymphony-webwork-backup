/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.CheckboxList;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author <a href="mailto:pathos@pandora.be">Mathias Bogaert</a>
 * @version $Id$
 */
public class CheckboxListTag extends AbstractListTag {
    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new CheckboxList(stack, req, res);
    }
}

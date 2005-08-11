/**
 * Copyright:	Copyright (c) From Down & Around, Inc.
 */

package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Div;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ian Roughley
 * @version $Id$
 */
public class DivModel extends TagModel {
    public DivModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack, req, res);
    }

    protected Component getBean() {
        return new Div(stack, req, res);
    }
}

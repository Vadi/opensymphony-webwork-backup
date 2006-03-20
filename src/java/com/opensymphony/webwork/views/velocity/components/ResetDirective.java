/*
 * The IT-neering.net Commercial Software License, Version 1.0
 *
 * Copyright (c) 2005 Rene Gielen. All rights reserved.
 *
 * IT-NEERING.NET PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * For more information on IT-neering.net, please see <http://it-neering.net/>.
 */
package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Reset;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @see com.opensymphony.webwork.components.Reset
 */
public class ResetDirective extends AbstractDirective {
    public String getBeanName() {
        return "reset";
    }

    protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Reset(stack, req, res);
    }
}

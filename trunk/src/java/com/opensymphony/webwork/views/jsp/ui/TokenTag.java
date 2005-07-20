/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.util.TokenHelper;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.Token;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * TokenTag
 *
 * @author Jason Carreira
 *         Created Apr 2, 2003 10:44:58 PM
 */
public class TokenTag extends AbstractUITag {
    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Token(stack, req, res);
    }
}

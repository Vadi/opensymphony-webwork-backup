/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.FieldError;
import com.opensymphony.webwork.views.jsp.ui.AbstractUITag;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * FieldError Tag.
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class FieldErrorTag extends AbstractUITag {

	public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new FieldError(stack, req, res);
	}
}


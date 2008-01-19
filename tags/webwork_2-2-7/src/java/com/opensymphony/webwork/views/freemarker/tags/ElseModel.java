/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.freemarker.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Else;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class ElseModel extends TagModel {

	public ElseModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	protected Component getBean() {
		return new Else(stack);
	}

}

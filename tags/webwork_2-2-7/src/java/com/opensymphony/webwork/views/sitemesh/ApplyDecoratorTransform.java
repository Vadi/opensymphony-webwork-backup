/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.sitemesh;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.views.freemarker.tags.TagModel;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class ApplyDecoratorTransform extends TagModel {

	/**
	 * @param stack
	 * @param req
	 * @param res
	 */
	public ApplyDecoratorTransform(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	/**
	 * @see com.opensymphony.webwork.views.freemarker.tags.TagModel#getBean()
	 */
	protected Component getBean() {
		return new ApplyDecoratorBean(stack, req, res);
	}
	
	
}

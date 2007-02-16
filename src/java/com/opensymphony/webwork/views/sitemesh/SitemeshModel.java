/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.sitemesh;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class SitemeshModel {

	private ApplyDecoratorTransform applyDecoratorTransform;
	
	private OgnlValueStack stack;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public SitemeshModel(OgnlValueStack stack, HttpServletRequest request, 
			HttpServletResponse response) {
		this.stack = stack;
		this.request = request;
		this.response = response;
	}
	
	public ApplyDecoratorTransform getApplydecorator() {
		if (applyDecoratorTransform == null) {
			applyDecoratorTransform = new ApplyDecoratorTransform(stack, request, response);
		}
		return applyDecoratorTransform;
	}
}

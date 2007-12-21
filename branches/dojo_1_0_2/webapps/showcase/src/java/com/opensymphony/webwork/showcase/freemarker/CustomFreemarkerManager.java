/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.freemarker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.views.freemarker.FreemarkerManager;
import com.opensymphony.webwork.views.freemarker.ScopesHashModel;

import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * This is an example of a custom FreemarkerManager, mean to be 
 * instantiated through Spring. 
 * <p/>
 * 
 * It will add into Freemarker's model
 * an utility class called {@link CustomFreemarkerManagerUtil} as a simple 
 * example demonstrating how to extends FreemarkerManager.
 * <p/>
 * 
 * The {@link CustomFreemarkerManagerUtil} will be created by Spring and 
 * injected through constructor injection.
 * <p/>
 */
public class CustomFreemarkerManager extends FreemarkerManager {
	
	private CustomFreemarkerManagerUtil util;
	
	public CustomFreemarkerManager(CustomFreemarkerManagerUtil util) {
		this.util = util;
	}
	
	public void populateContext(ScopesHashModel model, OgnlValueStack stack, Object action, HttpServletRequest request, HttpServletResponse response) {
		super.populateContext(model, stack, action, request, response);
		model.put("customFreemarkerManagerUtil", util);
	}
}

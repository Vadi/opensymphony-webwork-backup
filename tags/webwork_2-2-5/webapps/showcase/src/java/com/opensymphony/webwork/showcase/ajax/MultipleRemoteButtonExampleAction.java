/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.ajax;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class MultipleRemoteButtonExampleAction extends ActionSupport {

	private static final long serialVersionUID = -838186476463481758L;
	
	private String buttonName;
	
	public String getButtonName() { return this.buttonName; }
	
	public String executeButton1() throws Exception {
		buttonName = "Button 1";
		return SUCCESS;
	}
	
	public String executeButton2() throws Exception {
		buttonName = "Button 2";
		return SUCCESS;
	}
	
	public String executeButton3() throws Exception {
		buttonName = "Button 3";
		return SUCCESS;
	}
	
}

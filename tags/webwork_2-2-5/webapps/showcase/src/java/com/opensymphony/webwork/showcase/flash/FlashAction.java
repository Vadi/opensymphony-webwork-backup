/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.flash;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class FlashAction extends ActionSupport {

	private static final long serialVersionUID = 1038520295857693969L;
	
	private String name;
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String flashUsingInterceptor() throws Exception {
		return SUCCESS;
	}
	
	public String flashUsingResult() throws Exception {
		return SUCCESS;
	}
	
	public String afterRedirect() throws Exception {
		return SUCCESS;
	}
}

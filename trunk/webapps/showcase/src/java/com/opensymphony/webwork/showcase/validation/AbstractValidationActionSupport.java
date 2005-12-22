/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.validation;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author tm_jee
 * @version $Date$ $Id$
 */
public abstract class AbstractValidationActionSupport extends ActionSupport {
	
	public String submit() throws Exception {
		System.out.println("************************* HIT SUCCESS");
		return "success";
	}
	
	public String input() throws Exception {
		System.out.println("************************* HIT SHOW");
		return "input";
	}
}

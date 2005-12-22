/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.validation;

/**
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class VisitorValidatorsExampleAction extends AbstractValidationActionSupport {

	private User user;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}

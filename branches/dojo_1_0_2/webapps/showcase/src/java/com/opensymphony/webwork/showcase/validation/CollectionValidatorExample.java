/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.validation;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class CollectionValidatorExample extends ActionSupport {

	private static final long serialVersionUID = -5732571364553178339L;

	private List persons = new ArrayList();
	
	public void setPersons(List persons) { this.persons = persons; }
	public List getPersons() { return this.persons; }
	
	
	public String input() throws Exception {
		return "success";
	}
	
	public String submit() throws Exception {
		return "success";
	}
	
}

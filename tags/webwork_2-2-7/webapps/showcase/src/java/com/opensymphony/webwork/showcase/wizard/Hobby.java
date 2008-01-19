/*
 * Copyright (c) 2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.wizard;

import java.io.Serializable;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class Hobby implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private String name;
	private String description;
	
	public String getName() { return name; }
	public void setName(String name) { this.name=name; }
	
	public String getDescription() { return description; }
	public void setDescription(String description)  { this.description = description; }
}


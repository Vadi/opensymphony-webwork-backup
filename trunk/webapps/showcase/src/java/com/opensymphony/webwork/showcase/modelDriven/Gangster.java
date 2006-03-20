/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.modelDriven;

import java.io.Serializable;

/**
 * A model class to be used by the simple Model-Driven example. 
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class Gangster implements Serializable {

	private String name;
	private int age;
	private String description;
	private boolean bustedBefore;
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isBustedBefore() {
		return bustedBefore;
	}
	public void setBustedBefore(boolean bustedBefore) {
		this.bustedBefore = bustedBefore;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

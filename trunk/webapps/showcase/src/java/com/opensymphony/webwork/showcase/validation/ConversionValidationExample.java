/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.validation;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class ConversionValidationExample extends ActionSupport {

	private static final long serialVersionUID = 6447418678858685013L;
	
	private Bean bean;
	private Integer age;
	
	public Integer getAge() { return this.age; }
	public void setAge(Integer age) { this.age = age; }
	
	public Bean getBean() { return this.bean; }
	public void setBean(Bean bean) { this.bean = bean; }
	
	
	public String input() throws Exception {
		return "input";
	}
	
	public String submit() throws Exception {
		return "success";
	}

	
	public static class Bean {
		private Integer age;
		
		public Integer getAge() { return this.age; }
		public void setAge(Integer age) { this.age = age; }
	}
}

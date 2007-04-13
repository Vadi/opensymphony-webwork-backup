/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.tag.nonui.iteratortag;

import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.Validateable;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class SubsetIteratorTagDemo extends ActionSupport implements Validateable {

	private String iteratorValue;
	private Integer count;
	private Integer start;
	
	
	public void validate() {
		if (iteratorValue == null || iteratorValue.trim().length() <= 0 ) {
			addFieldError("iteratorValue1", "iterator value 1 cannot be empty");
		}
		else if (iteratorValue.trim().indexOf(",") <= 0) {
			addFieldError("iteratorValue1", "iterator value 1 needs to be comma separated");
		}
	}
	
	
	
	public String getIteratorValue() {
		return this.iteratorValue;
	}
	public void setIteratorValue(String iteratorValue) {
		this.iteratorValue = iteratorValue;
	}
	
	
	
	public Integer getCount() {
		return this.count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	
	public Integer getStart() {
		return this.start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	
	
	
	
	
	public String input() throws Exception {
		return SUCCESS;
	}
	
	public String submit() throws Exception {
		return SUCCESS;
	}
	
	
	
}

/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.ajax;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.opensymphony.xwork.ActionSupport;

/**
 * @version $Date$ $Id$
 */
public class Example4ShowPanelAction extends ActionSupport {

	private String name;
	private String gender;
	
	private static final long serialVersionUID = 7751976335066456596L;

	public String panel1() throws Exception {
		return SUCCESS;
	}
	
	public String panel2() throws Exception {
		return SUCCESS;
	}
	
	public String panel3() throws Exception {
		return SUCCESS;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTodayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
		return sdf.format(new Date());
	}
	
	public String getTodayTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
		return sdf.format(new Date());
	}
}

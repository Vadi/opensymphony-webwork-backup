/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.OptGroup;
import com.opensymphony.webwork.views.jsp.ComponentTagSupport;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class OptGroupTag extends ComponentTagSupport {

	private static final long serialVersionUID = 7367401003498678762L;

	protected String list;
	protected String label;
	protected String disabled;
	
	public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new OptGroup(stack, req, res);
	}

	protected void populateParams() {
		super.populateParams();
		
		OptGroup optGroup = (OptGroup) component;
		optGroup.setList(list);
		optGroup.setLabel(label);
		optGroup.setDisabled(disabled);
	}
	
	public void setList(String list) {
		this.list = list;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}

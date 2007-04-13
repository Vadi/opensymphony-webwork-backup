/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.actionchaining;

import com.opensymphony.xwork.ActionSupport;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class ActionChain1 extends ActionSupport {

	private String actionChain1Property1 = "Property Set In Action Chain 1";

	public String getActionChain1Property1() { 
		return actionChain1Property1;
	}
	public void setActionChain1Property1(String actionChain1Property1) {
		this.actionChain1Property1 = actionChain1Property1;
	}
	
	
	public String input() throws Exception {
		return SUCCESS;
	}
}

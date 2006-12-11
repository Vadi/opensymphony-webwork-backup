/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.sessionInvalidation;

import java.util.Map;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class SessionInvalidationAction extends ActionSupport implements SessionAware {

	private Map sessionMap;
	
	private static final long serialVersionUID = -2634727929107900849L;

	public String start() throws Exception {
		// sessionMap.remove("___invalidateSession");
		sessionMap.put("someKey1", "someValue1");
		sessionMap.put("someKey2",	"someValue2");
		return SUCCESS;
	}
	
	public String invalidateNow() throws Exception {
		return SUCCESS;
	}
	
	public String invalidateOnNextRequest() throws Exception {
		return SUCCESS;
	}
	
	public String issueAnotherRequest() throws Exception {
		return SUCCESS;
	}

	public void setSession(Map session) {
		this.sessionMap = session;
	}
	
}


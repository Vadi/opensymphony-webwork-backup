/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.sessionInvalidation;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class SessionInvalidationAction extends ActionSupport /*implements SessionAware*/ {
// NOTE: SessionAware was not being used due to quickstart not being able to compile, 
//       complaining that the abstract method setSession(Map) method from SessionAware
//       interface is not being implemented whereas in fact it is. It seems to be ok 
//       if the war file was deployed standalone in Tomcat, just that in quickstart
//       it seems to be an issue. Since quickstart is doing hot deployment, it might
//       have something to do with that, or how quickstart hot deploy java class file
//       (not sure). (WW-1437)
	
	private Map sessionMap;
	
	private static final long serialVersionUID = -2634727929107900849L;

	public String start() throws Exception {
		
		HttpSession session = ServletActionContext.getRequest().getSession(true);
		
		//sessionMap.put("someKey1", "someValue1");
		//sessionMap.put("someKey2",	"someValue2");
		
		session.setAttribute("someKey1", "someValue1");
		session.setAttribute("someKey2", "someValue2");
		
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

	/*public void setSession(Map session) {
		this.sessionMap = session;
	}*/
	
}


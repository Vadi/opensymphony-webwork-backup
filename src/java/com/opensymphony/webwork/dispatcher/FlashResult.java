/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import java.util.Map;

import com.opensymphony.webwork.interceptor.FlashInterceptor;
import com.opensymphony.xwork.ActionInvocation;

/**
 *  A flash result, that save the current action into the http session before 
 *  invoking <code>super.doExecute(...)</code>, which actually just do 
 *  a redirect to a specific location just as a normal {@link ServletRedirectResult}
 *  would.
 * 
 * @author Patrick Lightbody
 * @version $Date$ $Id$
 */
public class FlashResult extends ServletRedirectResult {

	private static final long serialVersionUID = -8956841683709714038L;

	private String key = FlashInterceptor.DEFAULT_KEY;
	
	/**
	 * A flash result, that save the current action into the http session before 
	 * invoking <code>super.doExecute(...)</code>.
	 * 
	 * @see com.opensymphony.webwork.dispatcher.ServletRedirectResult#doExecute(java.lang.String, com.opensymphony.xwork.ActionInvocation)
	 */
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		
		// before we redirect, let's save the state in to the session
        Object action = invocation.getAction();
        Map session = invocation.getInvocationContext().getSession();
        session.put(FlashInterceptor.DEFAULT_KEY, action);

		super.doExecute(finalLocation, invocation);
	}
	
	/**
	 * Set the key used to store the current action in http session.
	 * @param key
	 */
	public void setKey(String key) { 
		this.key = key;
	}
	
	/**
	 * Get the key used to store the current action in http session.
	 * @return String
	 */
	public String getKey() {
		return key;
	}
	
}

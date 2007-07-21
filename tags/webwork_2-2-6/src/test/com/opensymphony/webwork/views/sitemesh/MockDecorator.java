/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.sitemesh;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.opensymphony.module.sitemesh.Decorator;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class MockDecorator implements Decorator {

	private String uriPath;
	private String role;
	private String page;
	private String name;
	private Map params = new LinkedHashMap();
	
	public String getInitParameter(String name) {
		return (String) params.get(name);
	}

	public Iterator getInitParameterNames() {
		return params.entrySet().iterator();
	}

	public void setName(String name) { this.name = name; }
	public String getName() {
		return this.name;
	}

	public void setPage(String page) { this.page = page; }
	public String getPage() {
		return this.page;
	}

	public void setRole(String role) { this.role = role; }
	public String getRole() {
		return this.role;
	}

	public void setUriPath(String uriPath) { this.uriPath = uriPath; }
	public String getURIPath() {
		return uriPath;
	}

}

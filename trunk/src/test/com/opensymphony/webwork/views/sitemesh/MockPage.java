/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.sitemesh;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.module.sitemesh.HTMLPage;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class MockPage implements HTMLPage  {

	private String body;
	private int contentLength;
	private String page;
	private String title;
	private String head;
	private HttpServletRequest request;
	private Map properties = new LinkedHashMap();
	
	private boolean isFrameset = false;
	
	
	public void addProperty(String name, String value) {
		properties.put(name, value);
	}

	public void setBody(String body) { this.body = body; }
	public String getBody() {
		return body;
	}

	public void setBooleanProperty(String name, boolean value) { properties.put(name, Boolean.valueOf(value)); }
	public boolean getBooleanProperty(String name) {
		return ((Boolean)properties.get(name)).booleanValue();
	}

	public void setContentLength(int contentLength) { this.contentLength = contentLength; }
	public int getContentLength() {
		return contentLength;
	}

	public void setIntProperty(String name, int value) { this.properties.put(name, Integer.valueOf(value)); }
	public int getIntProperty(String name) {
		return ((Integer)properties.get(name)).intValue();
	}

	public void setLongProperty(String name, long value) { this.properties.put(name, Long.valueOf(value)); }
	public long getLongProperty(String name) {
		return ((Long)properties.get(name)).longValue();
	}

	public void setPage(String page) { this.page = page; }
	public String getPage() {
		return this.page;
	}

	public void setProperties(Map properties) { this.properties = properties; }
	public Map getProperties() {
		return this.properties;
	}

	public void setProperty(String name, String value) { this.properties.put(name, value); }
	public String getProperty(String name) {
		return (String) this.properties.get(name);
	}

	public String[] getPropertyKeys() {
		return (String[]) properties.keySet().toArray(new String[0]);
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public void setTitle(String title) { this.title = title; }
	public String getTitle() {
		return title;
	}

	public boolean isPropertySet(String name) {
		return properties.containsKey(name);
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void writeBody(Writer writer) throws IOException {
		if (body != null)
			writer.write(body);
	}

	public void writePage(Writer writer) throws IOException {
		if (page != null)
			writer.write(page);
	}

	public void setHead(String head) {
		this.head = head;
	}
	public String getHead() {
		return head;
	}

	public boolean isFrameSet() {
		return isFrameset;
	}

	public void setFrameSet(boolean isFrameset) {
		this.isFrameset = isFrameset;
	}

	public void writeHead(Writer writer) throws IOException {
	}
}

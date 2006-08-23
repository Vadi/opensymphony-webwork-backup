/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.freemarker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is just a simple util that gets injected into 
 * {@link CustomFreemarkerManager} through Spring's constructor
 * injection, serving as a simple example in SAF2's Showcase. 
 */
public class CustomFreemarkerManagerUtil {

	public String getTodayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(new Date());
	}
	
	public String getTimeNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		return sdf.format(new Date());
	}
}

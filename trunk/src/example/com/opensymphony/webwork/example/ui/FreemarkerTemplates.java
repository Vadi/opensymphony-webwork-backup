/*
 * Created on 4/03/2005
 *
 */
package com.opensymphony.webwork.example.ui;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionSupport;

/**
 * @author CameronBraid
 *
 */
public class FreemarkerTemplates extends ActionSupport {

	public void setTest(String test) {
		this.test = test;
		ServletActionContext.getRequest().setAttribute("testRequestAttrib", "attrib " + test);
	}
	protected String test;
	public String getTest() {
		return test;
	}
	
}

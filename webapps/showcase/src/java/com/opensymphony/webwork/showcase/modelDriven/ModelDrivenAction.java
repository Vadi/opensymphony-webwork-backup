package com.opensymphony.webwork.showcase.modelDriven;

import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.ModelDriven;

public class ModelDrivenAction extends ActionSupport implements ModelDriven {

	public String input() throws Exception {
		return SUCCESS;
	}
	
	public String execute() throws Exception {
		return SUCCESS;
	}

	public Object getModel() {
		return new Gangster();
	}
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.validation;

/**
 * @author tm_jee
 * @version $Date$ $Id$
 */

// START SNIPPET: nonFieldValidatorsExample

public class NonFieldValidatorsExampleAction extends AbstractValidationActionSupport {
	
	private String someText;
	private String someTextRetype;
	private String someTextRetypeAgain;
	
	public String getSomeText() {
		return someText;
	}
	public void setSomeText(String someText) {
		this.someText = someText;
	}
	public String getSomeTextRetype() {
		return someTextRetype;
	}
	public void setSomeTextRetype(String someTextRetype) {
		this.someTextRetype = someTextRetype;
	}
	public String getSomeTextRetypeAgain() {
		return someTextRetypeAgain;
	}
	public void setSomeTextRetypeAgain(String someTextRetypeAgain) {
		this.someTextRetypeAgain = someTextRetypeAgain;
	}
}


// END SNIPPET: nonFieldValidatorsExample




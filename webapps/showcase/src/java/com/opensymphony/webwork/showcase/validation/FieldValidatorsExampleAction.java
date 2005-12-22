/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.validation;

import java.sql.Date;
import java.util.Random;

/**
 * @author tm_jee
 * @version $Date$ $Id$
 */

// START SNIPPET: fieldValidatorsExample

public class FieldValidatorsExampleAction extends AbstractValidationActionSupport {
	
	private String requiredValidatorField = null;
	private String requiredStringValidatorField = null;
	private Integer integerValidatorField = null;
	private Date dateValidatorField = null;
	private String emailValidatorField = null;
	private String stringLengthValidatorField = null;
	private String regexValidatorField = null;
	private String fieldExpressionValidatorField = null;
	
	
	private String randomNumber = null;
	
	
	public Date getDateValidatorField() {
		return dateValidatorField;
	}
	public void setDateValidatorField(Date dateValidatorField) {
		this.dateValidatorField = dateValidatorField;
	}
	public String getEmailValidatorField() {
		return emailValidatorField;
	}
	public void setEmailValidatorField(String emailValidatorField) {
		this.emailValidatorField = emailValidatorField;
	}
	public Integer getIntegerValidatorField() {
		return integerValidatorField;
	}
	public void setIntegerValidatorField(Integer integerValidatorField) {
		this.integerValidatorField = integerValidatorField;
	}
	public String getRegexValidatorField() {
		return regexValidatorField;
	}
	public void setRegexValidatorField(String regexValidatorField) {
		this.regexValidatorField = regexValidatorField;
	}
	public String getRequiredStringValidatorField() {
		return requiredStringValidatorField;
	}
	public void setRequiredStringValidatorField(String requiredStringValidatorField) {
		this.requiredStringValidatorField = requiredStringValidatorField;
	}
	public String getRequiredValidatorField() {
		return requiredValidatorField;
	}
	public void setRequiredValidatorField(String requiredValidatorField) {
		this.requiredValidatorField = requiredValidatorField;
	}
	public String getStringLengthValidatorField() {
		return stringLengthValidatorField;
	}
	public void setStringLengthValidatorField(String stringLengthValidatorField) {
		this.stringLengthValidatorField = stringLengthValidatorField;
	}
	public String getFieldExpressionValidatorField() {
		return fieldExpressionValidatorField;
	}
	public void setFieldExpressionValidatorField(
			String fieldExpressionValidatorField) {
		this.fieldExpressionValidatorField = fieldExpressionValidatorField;
	}
}


// END SNIPPET: fieldValidatorsExample


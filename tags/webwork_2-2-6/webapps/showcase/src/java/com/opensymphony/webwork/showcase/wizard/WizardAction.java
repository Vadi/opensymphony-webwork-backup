/*
 * Copyright (c) 2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.wizard;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class WizardAction extends ActionSupport {
	
	// step1 
	private String name;
	private Integer age;
	private String gender;
	private Integer telephone;
	
	
	// step2 
	private String programmingLanguage;
	private String preferredLanguage;
	private String interest;
	
	
	// step 3
	private Hobby hobby = new Hobby();
	
	
	private static final long serialVersionUID = -8402905048868422544L;

	

	
	
	public String forwardToStep2() throws Exception {
		return SUCCESS;
	}
	
	public String forwardToStep3() throws Exception {
		return SUCCESS;
	}
	
	public String forwardToStep4() throws Exception {
		return SUCCESS;
	}
	
	public String confirm() throws Exception {
		return SUCCESS;
	}
	
	public String backToStep3() throws Exception {
		return SUCCESS;
	}
	
	public String backToStep2() throws Exception {
		return SUCCESS;
	}
	
	public String backToStep1() throws Exception {
		return SUCCESS;
	}

	
	
	
	public Hobby getHobby() {
		return hobby;
	}
	
	public void setHobby(Hobby hobby) {
		this.hobby = hobby;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTelephone() {
		return telephone;
	}

	public void setTelephone(Integer telephone) {
		this.telephone = telephone;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public String getProgrammingLanguage() {
		return programmingLanguage;
	}

	public void setProgrammingLanguage(String programmingLanguage) {
		this.programmingLanguage = programmingLanguage;
	}
	
}

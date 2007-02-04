/**
 * 
 */
package com.opensymphony.webwork.showcase.wizard;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author tmjee
 *
 */
public class TabbedWizardAction extends ActionSupport {

	private String name;
	private String age;
	private String gender;
	private String favouriteColor;
	
	public String input() throws Exception {
		print();
		return SUCCESS;
	}
	
	public String saveTabOne() throws Exception {
		print();
		return SUCCESS;
	}
	
	public String saveTabTwo() throws Exception {
		print();
		return SUCCESS;
	}
	
	public String end() throws Exception {
		print();
		return SUCCESS;
	}
	
	void print() {
		System.out.println("*** name="+name);
		System.out.println("*** age="+age);
		System.out.println("*** gender="+gender);
		System.out.println("*** favouriteColor="+favouriteColor);
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFavouriteColor() {
		return favouriteColor;
	}

	public void setFavouriteColor(String favouriteColor) {
		this.favouriteColor = favouriteColor;
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
	
}

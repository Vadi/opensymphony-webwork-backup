/**
 * 
 */
package com.opensymphony.webwork.showcase.messageStore;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author tmjee
 *
 */
public class ShowFormAction extends ActionSupport {
	
	private static final long serialVersionUID = -7795475629528823265L;
	
	private String name;
	private Integer age;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public Integer getAge() { return age; }
	public void setAge(Integer age) { this.age = age; }
	
	public String execute() throws Exception {
		return SUCCESS;
	}
}

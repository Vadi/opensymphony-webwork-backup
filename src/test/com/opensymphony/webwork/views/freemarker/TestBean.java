/*
 * Created on 1/10/2003
 *
 */
package com.opensymphony.webwork.views.freemarker;

/**
 * @author CameronBraid
 *
 */
public class TestBean
{

	/**
	 * 
	 */
	public TestBean(String name, String value)
	{
		super();
		setName(name);
		setValue(value);
	}

	/**
	 *
	 * Bean Property String Name
	 *
	 */
	public String getName()
	{
		return pName;
	}
	private String pName = null;
	public void setName(String aName)
	{
		pName = aName;
	}
	
	/**
	 *
	 * Bean Property String Value
	 *
	 */
	public String getValue()
	{
		return pValue;
	}
	private String pValue = null;
	public void setValue(String aValue)
	{
		pValue = aValue;
	}
}

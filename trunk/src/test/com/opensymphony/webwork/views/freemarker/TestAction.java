/*
 * Created on 1/10/2003
 *
 */
package com.opensymphony.webwork.views.freemarker;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author CameronBraid
 *
 */
public class TestAction extends ActionSupport
{

	/**
	 * 
	 */
	public TestAction()
	{
		super();
	}
	
	public List getStringList()
	{
		List list = new ArrayList();
		list.add("one");
		list.add("two");
		list.add("three");
		return list;
	}

	
	public List getBeanList()
	{
		List list = new ArrayList();
		list.add(new TestBean("one", "1"));
		list.add(new TestBean("two", "2"));
		list.add(new TestBean("three", "3"));
		return list;
	}

}

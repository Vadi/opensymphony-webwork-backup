/*
 * Created on 11/08/2003
 *
 */
package com.opensymphony.webwork.views.freemarker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.views.jsp.ui.OgnlTool;
import com.opensymphony.webwork.views.util.UrlHelper;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author CameronBraid
 *
 */
public class FreemarkerUtil
{

	private OgnlValueStack valueStack;
	private OgnlTool ognlTool = OgnlTool.getInstance();
	
	/**
	 * 
	 */
	public FreemarkerUtil()
	{
		super();
		valueStack = ServletActionContext.getContext().getValueStack();
	}

	public Object findValue(String expression)
	{
		return valueStack.findValue(expression);		
	}

	public Object findValue(String expression, String className) throws ClassNotFoundException
	{
		return valueStack.findValue(expression, Class.forName(className));		
	}
	
	public Object findValue(String expression, Object obj) throws ClassNotFoundException
	{
		return ognlTool.findValue(expression, obj);		
	}
	
	public String buildUrl(String url)
	{
		return UrlHelper.buildUrl(url, ServletActionContext.getRequest(), ServletActionContext.getResponse(), null);
	}
	
	public List makeSelectList(String list, String listKey, String listValue)
	{
		List selectList = new ArrayList();
		Collection items = (Collection)valueStack.findValue(list);
		 
		if (items != null)
			for (Iterator iter = items.iterator(); iter.hasNext();)
			{
				Object element = (Object) iter.next();
				Object key = null;
				if (listKey == null || listKey.length() == 0)
					key = element;
				else
					key = ognlTool.findValue(listKey, element);
				
				Object value = null;
				if (listValue == null || listValue.length() == 0)
					value = element;
				else
					value = ognlTool.findValue(listValue, element);
				
				selectList.add(new ListEntry(key, value));
			}
		return selectList;
	}

}

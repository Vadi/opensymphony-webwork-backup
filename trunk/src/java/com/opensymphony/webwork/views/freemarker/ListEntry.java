/*
 * Created on 6/09/2003
 *
 */
package com.opensymphony.webwork.views.freemarker;

/**
 * @author CameronBraid
 *
 */
public class ListEntry
{

	final private Object key,value;
	public ListEntry(Object key, Object value)
	{
		this.key = key;
		this.value = value;
	}
	public Object getKey()
	{
		return key;
	}
	public Object getValue()
	{
		return value;
	}
}

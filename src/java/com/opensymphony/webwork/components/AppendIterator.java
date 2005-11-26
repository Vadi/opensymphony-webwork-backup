/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.components.Param.UnnamedParametric;
import com.opensymphony.webwork.util.AppendIteratorFilter;
import com.opensymphony.webwork.util.MakeIterator;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * <!-- START SNIPPET: javadoc -->
 * <p>Component for AppendIteratorTag, which jobs is to append iterators to form an
 * appended iterator whereby entries goes from one iterator to another after each
 * respective iterator is exhausted of entries.</p>
 * 
 * <p>For example, if there are 3 iterator appended (each iterator has 3 entries), 
 * the following will be how the appended iterator entries will be arranged:</p>
 * 
 * <ol>
 * 		<li>First Entry of the First Iterator</li>
 * 		<li>Second Entry of the First Iterator</li>
 * 		<li>Third Entry of the First Iterator</li>
 *      <li>First Entry of the Second Iterator</li>
 *      <li>Second Entry of the Second Iterator</li>
 *      <li>Third Entry of the Second Iterator</li>
 *      <li>First Entry of the Third Iterator</li>
 *      <li>Second Entry of the Third Iterator</li>
 *      <li>Third Entry of the Third ITerator</li>
 * </ol>
 * <!-- END SNIPPET: javadoc -->
 * 
 * <!-- START SNIPPET: params -->
 * <ul>
 * 		<li>id (String) - the id of which if supplied will have the resultant 
 *                        appended iterator stored under in the stack's context</li>
 * </ul>
 * <!-- END SNIPPET: params -->
 * 
 * 
 * <!-- START SNIPPET: code -->
 * public class AppendIteratorTagAction extends ActionSupport {
 *
 *	private List myList1;
 *	private List myList2;
 *	private List myList3;
 *	
 *	
 *	public String execute() throws Exception {
 *		
 *		myList1 = new ArrayList();
 *		myList1.add("1");
 *		myList1.add("2");
 *		myList1.add("3");
 *		
 *		myList2 = new ArrayList();
 *		myList2.add("a");
 *		myList2.add("b");
 *		myList2.add("c");
 *		
 *		myList3 = new ArrayList();
 *		myList3.add("A");
 *		myList3.add("B");
 *		myList3.add("C");
 *		
 *		return "done";
 *	}
 *	
 *	public List getMyList1() { return myList1; }
 *	public List getMyList2() { return myList2; }
 *	public List getMyList3() { return myList3; }
 *}
 * <!-- END SNIPPET: code -->
 * 
 * <!-- START SNIPPET: example -->
 * &lt;ww:append id="myAppendIterator"&gt;
 *		&lt;ww:param value="%{myList1}" /&gt;
 *		&lt;ww:param value="%{myList2}" /&gt;
 *		&lt;ww:param value="%{myList3}" /&gt;
 * &lt;/ww:append&gt;
 * &lt;ww:iterator value="%{#myAppendIterator}"&gt;
 *		&lt;ww:property /&gt;
 * &lt;/ww:iterator&gt;
 * <!-- END SNIPPET: example -->
 * 
 * 
 * @author tm_jee ( tm_jee (at) yahoo.co.uk )
 * @version $Date$ $Id$
 * @see com.opensymphony.webwork.util.AppendIteratorFilter
 * @see com.opensymphony.webwork.views.jsp.iterator.AppendIteratorTag
 */
public class AppendIterator extends Component implements UnnamedParametric {

	private static final Log _log = LogFactory.getLog(AppendIterator.class);
	
	private AppendIteratorFilter appendIteratorFilter= null;
	private List _parameters;
	
	public AppendIterator(OgnlValueStack stack) {
		super(stack);
	}
	
	public void start(Writer writer) {
		_parameters = new ArrayList();
		appendIteratorFilter = new AppendIteratorFilter();
	}
	
	public void end(Writer writer, String body) {
		
		for (Iterator paramEntries = _parameters.iterator(); paramEntries.hasNext(); ) {
				
			Object iteratorEntryObj = paramEntries.next();
			if (! MakeIterator.isIterable(iteratorEntryObj)) {
				_log.warn("param with value resolved as "+iteratorEntryObj+" cannot be make as iterator, it will be ignored and hence will not appear in the merged iterator");
				continue;
			}
			appendIteratorFilter.setSource(MakeIterator.convert(iteratorEntryObj));
		}
		
		appendIteratorFilter.execute();
		
		if (getId() != null && getId().length() > 0) {
			getStack().getContext().put(getId(), appendIteratorFilter);
		}
		
		appendIteratorFilter = null;
	}

	// UnnamedParametric implementation --------------------------------------
	public void addParameter(Object value) {
		_parameters.add(value);
	}
}



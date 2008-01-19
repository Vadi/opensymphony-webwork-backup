/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.TagSupport;

import com.mockobjects.servlet.MockJspWriter;
import com.mockobjects.servlet.MockPageContext;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.components.If;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class ElseIfTagTest extends TestCase {

	protected MockPageContext pageContext;
	protected MockJspWriter jspWriter;
	protected OgnlValueStack stack;
	
	
	public void testIfIsFalseElseIfIsTrue() throws Exception {
		stack.getContext().put(If.ANSWER, Boolean.FALSE);
		
		ElseIfTag tag = new ElseIfTag();
		tag.setPageContext(pageContext);
		tag.setTest("true");
		
		int result = tag.doStartTag();
		tag.doEndTag();
		
		assertEquals(result, TagSupport.EVAL_BODY_INCLUDE);
	}
	
	public void testIfIsFalseElseIfIsFalse() throws Exception {
		stack.getContext().put(If.ANSWER, Boolean.FALSE);
		
		ElseIfTag tag = new ElseIfTag();
		tag.setPageContext(pageContext);
		tag.setTest("false");
		
		int result = tag.doStartTag();
		tag.doEndTag();
		
		assertEquals(result, TagSupport.SKIP_BODY);
	}
	
	public void testIfIsTrueElseIfIsTrue() throws Exception {
		stack.getContext().put(If.ANSWER, Boolean.TRUE);
		
		ElseIfTag tag = new ElseIfTag();
		tag.setPageContext(pageContext);
		tag.setTest("true");
		
		int result = tag.doStartTag();
		tag.doEndTag();
		
		assertEquals(result, TagSupport.SKIP_BODY);
	}
	
	public void testIfIsTrueElseIfIsFalse() throws Exception {
		stack.getContext().put(If.ANSWER, Boolean.TRUE);
		
		ElseIfTag tag = new ElseIfTag();
		tag.setPageContext(pageContext);
		tag.setTest("false");
		
		int result = tag.doStartTag();
		tag.doEndTag();
		
		assertEquals(result, TagSupport.SKIP_BODY);
	}
	
	
	protected void setUp() throws Exception {
		stack = new OgnlValueStack();
		
		jspWriter = new MockJspWriter();
		
		WebWorkMockHttpServletRequest request = new WebWorkMockHttpServletRequest();
		
		WebWorkMockServletContext servletContext = new WebWorkMockServletContext();
		servletContext.setServletInfo("not-weblogic");
		
		pageContext = new MockPageContext();
		pageContext.setJspWriter(jspWriter);
		pageContext.setRequest(request);
		pageContext.setServletContext(servletContext);
		
		request.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, stack);
	}
	
	
}

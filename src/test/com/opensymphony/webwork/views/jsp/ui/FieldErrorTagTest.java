/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.webwork.views.jsp.ParamTag;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionSupport;

/**
 * FieldError Tag Test Case.
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class FieldErrorTagTest extends AbstractUITagTest {

	
	public void testWithoutParamsWithFieldErrors() throws Exception {
		FieldErrorTag tag = new FieldErrorTag();
		((InternalAction)action).setHaveFieldErrors(true);
		tag.setPageContext(pageContext);
		tag.doStartTag();
		tag.doEndTag();
		
		verify(FieldErrorTagTest.class.getResource("fielderror-1.txt"));
	}
	
	public void testWithoutParamsWithoutFieldErrors() throws Exception {
		FieldErrorTag tag = new FieldErrorTag();
		((InternalAction)action).setHaveFieldErrors(false);
		tag.setPageContext(pageContext);
		tag.doStartTag();
		tag.doEndTag();
		
		verify(FieldErrorTagTest.class.getResource("fielderror-2.txt"));
	}
	
	public void testWithParamsWithFieldErrors1() throws Exception {
		FieldErrorTag tag = new FieldErrorTag();
		((InternalAction)action).setHaveFieldErrors(true);
		tag.setPageContext(pageContext);
		tag.doStartTag();
			ParamTag pTag1 = new ParamTag();
			pTag1.setPageContext(pageContext);
			pTag1.setValue("%{'field1'}");
			pTag1.doStartTag();
			pTag1.doEndTag();
			
			ParamTag pTag2 = new ParamTag();
			pTag2.setPageContext(pageContext);
			pTag2.setValue("%{'field3'}");
			pTag2.doStartTag();
			pTag2.doEndTag();
			
		tag.doEndTag();
		
		verify(FieldErrorTagTest.class.getResource("fielderror-3.txt"));
	}
	
	public void testWithParamsWithFieldErrors2() throws Exception {
		FieldErrorTag tag = new FieldErrorTag();
		((InternalAction)action).setHaveFieldErrors(true);
		tag.setPageContext(pageContext);
		tag.doStartTag();
			ParamTag pTag1 = new ParamTag();
			pTag1.setPageContext(pageContext);
			pTag1.setValue("%{'field1'}");
			pTag1.doStartTag();
			pTag1.doEndTag();
			
			ParamTag pTag2 = new ParamTag();
			pTag2.setPageContext(pageContext);
			pTag2.setValue("%{'field2'}");
			pTag2.doStartTag();
			pTag2.doEndTag();
			
		tag.doEndTag();
		
		verify(FieldErrorTagTest.class.getResource("fielderror-4.txt"));
	}
	
	
	public void testWithParamsWithFieldErrors3() throws Exception {
		FieldErrorTag tag = new FieldErrorTag();
		((InternalAction)action).setHaveFieldErrors(true);
		tag.setPageContext(pageContext);
		tag.doStartTag();
			ParamTag pTag1 = new ParamTag();
			pTag1.setPageContext(pageContext);
			pTag1.setValue("%{'field2'}");
			pTag1.doStartTag();
			pTag1.doEndTag();
			
		tag.doEndTag();
		
		verify(FieldErrorTagTest.class.getResource("fielderror-5.txt"));
	}
	
	public void testWithParamsWithoutFieldErrors1() throws Exception {
		FieldErrorTag tag = new FieldErrorTag();
		((InternalAction)action).setHaveFieldErrors(false);
		tag.setPageContext(pageContext);
		tag.doStartTag();
			ParamTag pTag1 = new ParamTag();
			pTag1.setPageContext(pageContext);
			pTag1.setValue("%{'field1'}");
			pTag1.doStartTag();
			pTag1.doEndTag();
			
			ParamTag pTag2 = new ParamTag();
			pTag2.setPageContext(pageContext);
			pTag2.setValue("%{'field3'}");
			pTag2.doStartTag();
			pTag2.doEndTag();
		tag.doEndTag();
		
		verify(FieldErrorTagTest.class.getResource("fielderror-2.txt"));
	}
	
	public void testWithParamsWithoutFieldErrors2() throws Exception {
		FieldErrorTag tag = new FieldErrorTag();
		((InternalAction)action).setHaveFieldErrors(false);
		tag.setPageContext(pageContext);
		tag.doStartTag();
			ParamTag pTag1 = new ParamTag();
			pTag1.setPageContext(pageContext);
			pTag1.setValue("%{'field1'}");
			pTag1.doStartTag();
			pTag1.doEndTag();
			
			ParamTag pTag2 = new ParamTag();
			pTag2.setPageContext(pageContext);
			pTag2.setValue("%{'field3'}");
			pTag2.doStartTag();
			pTag2.doEndTag();
		tag.doEndTag();
		
		verify(FieldErrorTagTest.class.getResource("fielderror-2.txt"));
	}
	
	
	public void testWithParamsWithoutFieldErrors3() throws Exception {
		FieldErrorTag tag = new FieldErrorTag();
		((InternalAction)action).setHaveFieldErrors(false);
		tag.setPageContext(pageContext);
		tag.doStartTag();
			ParamTag pTag1 = new ParamTag();
			pTag1.setPageContext(pageContext);
			pTag1.setValue("%{'field2'}");
			pTag1.doStartTag();
			pTag1.doEndTag();
			
		tag.doEndTag();
		
		verify(FieldErrorTagTest.class.getResource("fielderror-2.txt"));
	}
	
	
	public Action getAction() {
		return new InternalAction();
	}
	
	
	public class InternalAction extends ActionSupport {
		
		private boolean haveFieldErrors = false;
		
		public void setHaveFieldErrors(boolean haveFieldErrors) {
			this.haveFieldErrors = haveFieldErrors;
		}
		
		public Map getFieldErrors() {
			if (haveFieldErrors) {
				List err1 = new ArrayList();
				err1.add("field error message number 1");
				List err2 = new ArrayList();
				err2.add("field error message number 2");
				List err3 = new ArrayList();
				err3.add("field error message number 3");
				Map fieldErrors = new LinkedHashMap();
				fieldErrors.put("field1", err1);
				fieldErrors.put("field2", err2);
				fieldErrors.put("field3", err3);
				return fieldErrors;
			}
			else {
				return Collections.EMPTY_MAP;
			}
		}
		
		public boolean hasFieldErrors() {
			return haveFieldErrors;
		}
	}
}


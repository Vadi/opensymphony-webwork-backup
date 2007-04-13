/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import com.opensymphony.webwork.components.Form;
import com.opensymphony.webwork.components.Submit;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class FormButtonTest extends WebWorkTestCase {
	public void testPopulateComponentHtmlId1() throws Exception {
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();
		OgnlValueStack stack = new OgnlValueStack();
		
		Form form = new Form(stack, req, res);
		form.getParameters().put("id", "formId");
		
		Submit submit = new Submit(stack, req, res);
		submit.setId("submitId");
		
		submit.populateComponentHtmlId(form);
		
		assertEquals("submitId", submit.getParameters().get("id"));
	}
	
	public void testPopulateComponentHtmlId2() throws Exception {
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();
		OgnlValueStack stack = new OgnlValueStack();
		
		Form form = new Form(stack, req, res);
		form.getParameters().put("id", "formId");
		
		Submit submit = new Submit(stack, req, res);
		submit.setName("submitName");
		
		submit.populateComponentHtmlId(form);
		
		assertEquals("formId_submitName", submit.getParameters().get("id"));
	}
	
	public void testPopulateComponentHtmlId3() throws Exception {
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();
		OgnlValueStack stack = new OgnlValueStack();
		
		Form form = new Form(stack, req, res);
		form.getParameters().put("id", "formId");
		
		Submit submit = new Submit(stack, req, res);
		submit.setAction("submitAction");
		submit.setMethod("submitMethod");
		
		submit.populateComponentHtmlId(form);
		
		assertEquals("formId_submitAction_submitMethod", submit.getParameters().get("id"));
	}
	
	public void testPopulateComponentHtmlId4() throws Exception {
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();
		OgnlValueStack stack = new OgnlValueStack();
		
		Submit submit = new Submit(stack, req, res);
		submit.setId("submitId");
		
		submit.populateComponentHtmlId(null);
		
		assertEquals("submitId", submit.getParameters().get("id"));
	}
	
	public void testPopulateComponentHtmlId5() throws Exception {
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();
		OgnlValueStack stack = new OgnlValueStack();
		
		Submit submit = new Submit(stack, req, res);
		submit.setName("submitName");
		
		submit.populateComponentHtmlId(null);
		
		assertEquals("submitName", submit.getParameters().get("id"));
	}
	
	public void testPopulateComponentHtmlId6() throws Exception {
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();
		OgnlValueStack stack = new OgnlValueStack();
		
		Submit submit = new Submit(stack, req, res);
		submit.setAction("submitAction");
		submit.setMethod("submitMethod");
		
		submit.populateComponentHtmlId(null);
		
		assertEquals("submitAction_submitMethod", submit.getParameters().get("id"));
	}
}

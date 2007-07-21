/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.xwork.util.LocalizedTextUtil;

/**
 * Test case for DispatcherUtils.
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class DispatcherUtilsTest extends WebWorkTestCase {

	public void testDefaultResurceBundlePropertyLoaded() throws Exception {
        Locale.setDefault(Locale.US); // force to US locale as we also have _de and _da properties
        DispatcherUtils.setInstance(null);
        DispatcherUtils.initialize(new MockServletContext());
		
		// some i18n messages from xwork-messages.properties
		assertEquals(
				LocalizedTextUtil.findDefaultText("xwork.error.action.execution", Locale.US), 
				"Error during Action invocation");
		
		// some i18n messages from webwork-messages.properties
		assertEquals(
				LocalizedTextUtil.findDefaultText("webwork.messages.error.uploading", Locale.US, 
						new Object[] { "some error messages" }), 
				"Error uploading: some error messages");
	}
	
	public void testPrepareSetEncodingProperly() throws Exception {
		HttpServletRequest req = new MockHttpServletRequest();
		HttpServletResponse res = new MockHttpServletResponse();
		
		Configuration.set(WebWorkConstants.WEBWORK_I18N_ENCODING, "utf-8");
		
		DispatcherUtils.setInstance(null);
		DispatcherUtils.initialize(new MockServletContext());
		DispatcherUtils du = DispatcherUtils.getInstance();
		du.prepare(req, res);
		
		assertEquals(req.getCharacterEncoding(), "utf-8");
	}
	
	public void testPrepareSetEncodingPropertyWithMultipartRequest() throws Exception {
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();
		
		req.setContentType("multipart/form-data");
		Configuration.set(WebWorkConstants.WEBWORK_I18N_ENCODING, "utf-8");
		
		DispatcherUtils.setInstance(null);
		DispatcherUtils.initialize(new MockServletContext());
		DispatcherUtils du = DispatcherUtils.getInstance();
		du.prepare(req, res);
		
		assertEquals(req.getCharacterEncoding(), "utf-8");
	}
}

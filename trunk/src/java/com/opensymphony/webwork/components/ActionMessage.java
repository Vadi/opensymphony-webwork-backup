/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * <!-- START SNIPPET: javadoc -->
 * 
 * Create a list ( &lt;ul&gt; and &lt;li&gt; ) of action messages if they exists.
 * 
 * <!-- END SNIPPET: javadoc -->
 * 
 * <p/> <b>Examples</b>
 * 
 * <pre>
 * <!-- START SNIPPET: example -->
 * 
 *    &lt;actionmessage /&gt;
 * 
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 * @ww.tag name="actionmessage" tld-body-content="empty" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.ActionMessageTag"
 * description="Render action messages if they exists"
 * @since 2.2
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class ActionMessage extends UIBean {

	private static final String TEMPLATE = "actionmessage";
	
	public ActionMessage(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	protected String getDefaultTemplate() {
		return TEMPLATE;
	}
}

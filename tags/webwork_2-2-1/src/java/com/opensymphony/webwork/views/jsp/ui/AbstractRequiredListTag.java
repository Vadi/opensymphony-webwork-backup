/*
 * Copyright (c) 2002-2005 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


import com.opensymphony.webwork.components.ListUIBean;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public abstract class AbstractRequiredListTag extends AbstractListTag {

	protected void populateParams() {
		super.populateParams();
		
		ListUIBean listUIBean = (ListUIBean) component;
		listUIBean.setThrowExceptionOnNullValueAttribute(true);
	}

}

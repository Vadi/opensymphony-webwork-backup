/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class OptGroup extends Component {
	
	public static final String INTERNAL_LIST_UI_BEAN_LIST_PARAMETER_KEY = "optGroupInternalListUiBeanList";
	
	private static Log _log = LogFactory.getLog(OptGroup.class);
	
	protected HttpServletRequest req;
	protected HttpServletResponse res;
	
	protected ListUIBean internalUiBean;

	public OptGroup(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack);
		this.req = req;
		this.res = res;
		internalUiBean = new ListUIBean(stack, req, res) {
			protected String getDefaultTemplate() {
				return "empty";
			}
		};
	}
	
	public boolean end(Writer writer, String body) {
		Select select = (Select) findAncestor(Select.class);
		if (select == null) {
			_log.error("incorrect use of OptGroup component, this component must be used within a Select component", 
					new IllegalStateException("incorrect use of OptGroup component, this component must be used within a Select component"));
			return false;
		}
		internalUiBean.start(writer);
		internalUiBean.end(writer, body);
		
		List listUiBeans = (List) select.getParameters().get(INTERNAL_LIST_UI_BEAN_LIST_PARAMETER_KEY);
		if (listUiBeans == null) {
			listUiBeans = new ArrayList();
		}
		listUiBeans.add(internalUiBean);
		select.addParameter(INTERNAL_LIST_UI_BEAN_LIST_PARAMETER_KEY, listUiBeans);
		
		return false;
	}
	
	
	public void setLabel(String label) {
		internalUiBean.setLabel(label);
	}
	
	public void setDisabled(String disabled) {
		internalUiBean.setDisabled(disabled);
	}
	
	public void setList(String list) {
		internalUiBean.setList(list);
	}
	
	public void setListKey(String listKey) {
		internalUiBean.setListKey(listKey);
	}
	
	public void setListValue(String listValue) {
		internalUiBean.setListValue(listValue);
	}
}

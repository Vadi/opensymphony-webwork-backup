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
 * <!-- START SNIPPET: javadoc -->
 * 
 * Create a optgroup component which needs to resides within a select tag.
 * 
 * <!-- END SNIPPET: javadoc -->
 * 
 * <p/>
 * 
 * <!-- START SNIPPET: notice -->
 * 
 * This component is to be used within a  Select component.
 * 
 * <!-- END SNIPPET: notice -->
 * 
 * <p/>
 *
 * <pre> 
 * <!-- START SNIPPET: example -->
 * 
 * &lt;ww:select label="My Selection" 
 *            name="mySelection" 
 *            value="%{'POPEYE'}"
 *            list="%{#{'SUPERMAN':'Superman', 'SPIDERMAN':'spiderman'}}"&gt;
 *    &lt;ww:optgroup label="Adult"
 *                 list="%{#{'SOUTH_PARK':'South Park'}}" /&gt;
 *    &lt;ww:optgroup label="Japanese"
 *                 list="%{#{'POKEMON':'pokemon','DIGIMON':'digimon','SAILORMOON':'Sailormoon'}}" /&gt;
 * &lt;/ww:select&gt;
 * 
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 * @ww.tag name="optgroup" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.OptGroupTag"
 * description="Renders a Select Tag's OptGroup Tag"
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
	
	/**
	 * Set the label attribute.
	 * @ww.tagattribute required="false"
	 */
	public void setLabel(String label) {
		internalUiBean.setLabel(label);
	}
	
	/**
	 * Set the disable attribute.
	 * @ww.tagattribute required="false"
	 */
	public void setDisabled(String disabled) {
		internalUiBean.setDisabled(disabled);
	}
	
	/**
	 * Set the list attribute.
	 * @ww.tagattribute required="false"
	 */
	public void setList(String list) {
		internalUiBean.setList(list);
	}
	
	/**
	 * Set the listKey attribute.
	 * @ww.tagattribute required="false"
	 */
	public void setListKey(String listKey) {
		internalUiBean.setListKey(listKey);
	}
	
	/**
	 * Set the listValue attribute.
	 * @ww.tagattribute required="false"
	 */
	public void setListValue(String listValue) {
		internalUiBean.setListValue(listValue);
	}
}

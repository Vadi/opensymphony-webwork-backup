/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 * 
 * @ww.tag name="updownselect" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.UpDownSelectTag"
 * description="Render a up down select element"
 */
public class UpDownSelect extends Select {
	
	private static final Log _log = LogFactory.getLog(UpDownSelect.class);
	

	final public static String TEMPLATE = "updownselect";
	
	protected String allowMoveUp;
	protected String allowMoveDown;
	protected String allowSelectAll;
	
	protected String moveUpLabel;
	protected String moveDownLabel;
	protected String selectAllLabel;
	
	
	public String getDefaultTemplate() {
		return TEMPLATE;
	}
	
	public UpDownSelect(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}
	
	public void evaluateParams() {
		super.evaluateParams();
		
		
		// override Select's default
		if (size == null || size.trim().length() <= 0) {
			addParameter("size", "5");
		}
		if (multiple == null || multiple.trim().length() <= 0) {
			addParameter("multiple", "true");
		}
		
		
		
		if (allowMoveUp != null) {
			addParameter("allowMoveUp", findValue(allowMoveUp, Boolean.class));
		}
		if (allowMoveDown != null) {
			addParameter("allowMoveDown", findValue(allowMoveDown, Boolean.class));
		}
		if (allowSelectAll != null) {
			addParameter("allowSelectAll", findValue(allowSelectAll, Boolean.class));
		}
		
		if (moveUpLabel != null) {
			addParameter("moveUpLabel", findString(moveUpLabel));
		}
		if (moveDownLabel != null) {
			addParameter("moveDownLabel", findString(moveDownLabel));
		}
		if (selectAllLabel != null) {
			addParameter("selectAllLabel", findString(selectAllLabel));
		}
		
		
		// inform our form ancestor about this UpDownSelect so the form knows how to 
		// auto select all options upon it submission
		Form ancestorForm = (Form) findAncestor(Form.class);
		if (ancestorForm != null) {
			Map m = (Map) ancestorForm.getParameters().get("updownselectIds");
			if (m == null) {
				// map with key -> id ,  value -> headerKey
				m = new LinkedHashMap();
			}
			m.put(getParameters().get("id"), getParameters().get("headerKey"));
			ancestorForm.getParameters().put("updownselectIds", m);
		}
		else {
			_log.warn("no ancestor form found for updownselect "+this+", therefore autoselect of all elements unpon form submission will not work ");
		}
	}

	
	public String getAllowMoveUp() { 
		return allowMoveUp;
	}
	/**
	 * @ww.tagattribute required="false" type="Boolean" default="true"
     * description="Whether move up button should be displayed"
	 */
	public void setAllowMoveUp(String allowMoveUp) {
		this.allowMoveUp = allowMoveUp;
	}
	
	
	
	public String getAllowMoveDown() {
		return allowMoveDown;
	}
	/**
	 * @ww.tagattribute required="false" type="Boolean" default="true"
     * description="Whether move down button should be displayed"
	 */
	public void setAllowMoveDown(String allowMoveDown) {
		this.allowMoveDown = allowMoveDown;
	}
	
	
	
	public String getAllowSelectAll() {
		return allowSelectAll;
	}
	/**
	 * @ww.tagattribute required="false" type="Boolean" default="true"
     * description="Whether or not select all button should be displayed"
	 */
	public void setAllowSelectAll(String allowSelectAll) {
		this.allowSelectAll = allowSelectAll;
	}
	
	
	public String getMoveUpLabel() {
		return moveUpLabel;
	}
	/**
	 * @ww.tagattribute required="false" type="String" default="^"
     * description="Text to display on the move up button"
	 */
	public void setMoveUpLabel(String moveUpLabel) {
		this.moveUpLabel = moveUpLabel;
	}
	
	
	
	public String getMoveDownLabel() {
		return moveDownLabel;
	}
	/**
	 * @ww.tagattribute required="false" type="String" default="v"
     * description="Text to display on the move down button"
	 */
	public void setMoveDownLabel(String moveDownLabel) {
		this.moveDownLabel = moveDownLabel;
	}
	

	
	public String getSelectAllLabel() {
		return selectAllLabel;
	}
	/**
	 * @ww.tagattribute required="false" type="String" default="*"
     * description="Text to display on the select all button"
	 */
	public void setSelectAllLabel(String selectAllLabel) {
		this.selectAllLabel = selectAllLabel;
	}
}

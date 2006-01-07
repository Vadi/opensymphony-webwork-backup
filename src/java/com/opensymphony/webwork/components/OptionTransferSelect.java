/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 * @ww.tag name="optiontransferselect" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.OptionTransferSelectTag"
 * description="Renders an input form"
 */
public class OptionTransferSelect extends DoubleListUIBean {

	private static final String TEMPLATE = "optiontransferselect";
	
	protected String allowAddToLeft;
	protected String allowAddToRight;
	protected String allowAddAllToLeft;
	protected String allowAddAllToRight;
	protected String allowSelectAll;
	
	protected String leftTitle;
	protected String rightTitle;
	
	protected String buttonCssClass;
	protected String buttonCssStyle;

	protected String addToLeftLabel;
	protected String addToRightLabel;
	protected String addAllToLeftLabel;
	protected String addAllToRightLabel;
	protected String selectAllLabel;
	
	
	public OptionTransferSelect(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	protected String getDefaultTemplate() {
		return TEMPLATE;
	}
	
	
	public void evaluateExtraParams() {
		super.evaluateExtraParams();
		
		Object doubleValue = null;
		
		// override DoubleListUIBean's 
        if (doubleList != null) {
            doubleValue = findValue(doubleList);
            addParameter("doubleList", doubleValue);
        }
        if (size == null || size.trim().length() <= 0) {
        	addParameter("size", "15");
        }
        if (doubleSize == null || doubleSize.trim().length() <= 0) {
        	addParameter("doubleSize", "15");
        }
        if (multiple == null || multiple.trim().length() <= 0) {
        	addParameter("multiple", "multiple");
        }
        if (doubleMultiple == null || doubleMultiple.trim().length() <= 0) {
        	addParameter("doubleMultiple", "multiple");
        }
        
        
        
        
        
        // buttonCssClass 
        if (buttonCssClass != null && buttonCssClass.trim().length() > 0) {
        	addParameter("buttonCssClass", buttonCssClass);
        }
        
        // buttonCssStyle
        if (buttonCssStyle != null && buttonCssStyle.trim().length() > 0) {
        	addParameter("buttonCssStyle", buttonCssStyle);
        }
        
        

        // allowSelectAll
        addParameter("allowSelectAll", 
        		allowSelectAll != null ? findValue(allowSelectAll, Boolean.class) : Boolean.TRUE);
		
		// allowAddToLeft
		addParameter("allowAddToLeft", 
				allowAddToLeft != null ? findValue(allowAddToLeft, Boolean.class) : Boolean.TRUE);
		
		// allowAddToRight
		addParameter("allowAddToRight",
				allowAddToRight != null ? findValue(allowAddToRight, Boolean.class) : Boolean.TRUE);
		
		// allowAddAllToLeft
		addParameter("allowAddAllToLeft",
				allowAddAllToLeft != null ? findValue(allowAddAllToLeft, Boolean.class) : Boolean.TRUE);
		
		// allowAddAllToRight
		addParameter("allowAddAllToRight", 
				allowAddAllToRight != null ? findValue(allowAddAllToRight, Boolean.class) : Boolean.TRUE);
		
		
		
		// leftTitle
		if (leftTitle != null) {
			addParameter("leftTitle", findValue(leftTitle, String.class));
		}
		
		// rightTitle
		if (rightTitle != null) {
			addParameter("rightTitle", findValue(rightTitle, String.class));
		}
		
		
		// addToLeftLabel
		addParameter("addToLeftLabel", 
				addToLeftLabel != null ? findValue(addToLeftLabel, String.class) : "<-" );
		
		// addToRightLabel
		addParameter("addToRightLabel", 
				addToRightLabel != null ? findValue(addToRightLabel, String.class) : "->");
		
		// addAllToLeftLabel
		addParameter("addAllToLeftLabel", 
				addAllToLeftLabel != null ? findValue(addAllToLeftLabel, String.class) : "<<--");
		
		// addAllToRightLabel
		addParameter("addAllToRightLabel", 
				addAllToRightLabel != null ? findValue(addAllToRightLabel, String.class) : "-->>");
		
		// selectAllLabel
		addParameter("selectAllLabel",
				selectAllLabel != null ? findValue(selectAllLabel, String.class) : "<*>");
	}
	
	
	
	public String getAddAllToLeftLabel() {
		return addAllToLeftLabel;
	}

	/**
	 * @ww.tagattribute required="false"
     * description="set Add To Left button label"
	 */
	public void setAddAllToLeftLabel(String addAllToLeftLabel) {
		this.addAllToLeftLabel = addAllToLeftLabel;
	}

	public String getAddAllToRightLabel() {
		return addAllToRightLabel;
	}

	/**
	 * @ww.tagattribute required="false"
     * description="set Add All To Right button label"
	 */
	public void setAddAllToRightLabel(String addAllToRightLabel) {
		this.addAllToRightLabel = addAllToRightLabel;
	}

	public String getAddToLeftLabel() {
		return addToLeftLabel;
	}

	/**
	 * @ww.tagattribute required="false"
     * description="set Add To Left button label"
	 */
	public void setAddToLeftLabel(String addToLeftLabel) {
		this.addToLeftLabel = addToLeftLabel;
	}

	public String getAddToRightLabel() {
		return addToRightLabel;
	}

	/**
	 * @ww.tagattribute required="false"
     * description="set Add To Right button label"
	 */
	public void setAddToRightLabel(String addToRightLabel) {
		this.addToRightLabel = addToRightLabel;
	}

	public String getAllowAddAllToLeft() {
		return allowAddAllToLeft;
	}

	/**
	 * @ww.tagattribute required="false"
     * description="enable Add All To Left button"
	 */
	public void setAllowAddAllToLeft(String allowAddAllToLeft) {
		this.allowAddAllToLeft = allowAddAllToLeft;
	}

	public String getAllowAddAllToRight() {
		return allowAddAllToRight;
	}

	/**
	 * @ww.tagattribute required="false"
     * description="enable Add All To Right button"
	 */
	public void setAllowAddAllToRight(String allowAddAllToRight) {
		this.allowAddAllToRight = allowAddAllToRight;
	}

	public String getAllowAddToLeft() {
		return allowAddToLeft;
	}

	/**
	 * @ww.tagattribute required="false"
     * description="enable Add To Left button"
	 */
	public void setAllowAddToLeft(String allowAddToLeft) {
		this.allowAddToLeft = allowAddToLeft;
	}

	public String getAllowAddToRight() {
		return allowAddToRight;
	}

	/**
	 * @ww.tagattribute required="false"
     * description="enable Add To Right button"
	 * @param allowAddToRight
	 */
	public void setAllowAddToRight(String allowAddToRight) {
		this.allowAddToRight = allowAddToRight;
	}

	public String getLeftTitle() {
		return leftTitle;
	}

	/**
	 * @ww.tagattribute required="false"
     * description="set Left title"
	 */
	public void setLeftTitle(String leftTitle) {
		this.leftTitle = leftTitle;
	}

	public String getRightTitle() {
		return rightTitle;
	}

	/**
	 * @ww.tagattribute required="false"
     * description="set Right title"
	 */
	public void setRightTitle(String rightTitle) {
		this.rightTitle = rightTitle;
	}
	
	
	/**
	 * @ww.tagattribute required="false"
     * description="enable Select All button"
	 */
	public void setAllowSelectAll(String allowSelectAll) {
		this.allowSelectAll = allowSelectAll;
	}
	public String getAllowSelectAll() {
		return this.allowSelectAll;
	}
	
	
	/**
	 * @ww.tagattribute required="false"
     * description="set Select All button label"
	 */
	public void setSelectAllLabel(String selectAllLabel) {
		this.selectAllLabel = selectAllLabel;
	}
	public String getSelectAllLabel() { 
		return this.selectAllLabel;
	}
	
	
	/**
	 * @ww.tagattribute required="false"
     * description="set buttons css class"
	 */
	public void setButtonCssClass(String buttonCssClass) {
		this.buttonCssClass = buttonCssClass;
	}
	public String getButtonCssClass() {
		return buttonCssClass;
	}
	
	
	/**
	 * @ww.tagattribute required="false"
     * description="set button css style"
	 */
	public void setButtonCssStyle(String buttonCssStyle) {
		this.buttonCssStyle = buttonCssStyle;
	}
	public String getButtonCssStyle() {
		return this.buttonCssStyle;
	}
}

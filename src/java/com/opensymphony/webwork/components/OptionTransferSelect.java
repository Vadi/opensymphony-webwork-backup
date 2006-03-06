/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * <!-- START SNIPPET: javadoc -->
 * 
 * Create a option transfer select component which is basically two &lt;select ...&gt;
 * tag with buttons in the middle of them allowing options in each of the 
 * &lt;select ...&gt; to be moved between themselves. Will auto-select all its 
 * elements upon its containing form submision.
 * 
 * <!-- END SNIPPET: javadoc -->
 * 
 * <p/>
 * 
 * 
 * <!-- START SNIPPET: notice -->
 * 
 * NOTE: The id and doubleId need not be supplied as they will generated provided
 * that the optiontransferselect tag is being used in a form tag. The generated id 
 * and doubleId will be &lt;form_id&gt;_&lt;optiontransferselect_doubleName&gt; and 
 * &lt;form_id&gt;_&lt;optiontransferselect_doubleName&gt; respectively.
 * 
 * <!-- END SNIPPET: notice -->
 * 
 * <p/>
 * 
 * <pre>
 * <!-- START SNIPPET: example -->
 * 
 * &lt;-- minimum configuration --&gt;
 * &lt;ww:optiontransferselect 
 *   	label="Favourite Cartoons Characters"
 *		name="leftSideCartoonCharacters" 
 *		list="{'Popeye', 'He-Man', 'Spiderman'}" 
 *		doubleName="rightSideCartoonCharacters"
 *		doubleList="{'Superman', 'Mickey Mouse', 'Donald Duck'}" 
 *	/&gt;
 *
 *  &lt;-- possible configuration --&gt;
 *  &lt;ww:optiontransferselect 
 *   	label="Favourite Cartoons Characters"
 *		name="leftSideCartoonCharacters" 
 *		leftTitle="Left Title"
 *		rightTitle="Right Title"
 *		list="{'Popeye', 'He-Man', 'Spiderman'}" 
 *		multiple="true"
 *		headerKey="headerKey"
 *		headerValue="--- Please Select ---"
 *		emptyOption="true"
 *		doubleList="{'Superman', 'Mickey Mouse', 'Donald Duck'}" 
 *		doubleName="rightSideCartoonCharacters"
 *		doubleHeaderKey="doubleHeaderKey"
 *		doubleHeaderValue="--- Please Select ---" 
 *		doubleEmptyOption="true"
 *		doubleMultiple="true"
 *	/&gt;
 * 
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 * @ww.tag name="optiontransferselect" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.OptionTransferSelectTag"
 * description="Renders an input form"
 */
public class OptionTransferSelect extends DoubleListUIBean {
	
	private static final Log _log = LogFactory.getLog(OptionTransferSelect.class);

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
        	addParameter("multiple", Boolean.TRUE);
        }
        if (doubleMultiple == null || doubleMultiple.trim().length() <= 0) {
        	addParameter("doubleMultiple", Boolean.TRUE);
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
		
		
		
		
		// inform the form component our select tag infos, so they know how to select 
		// its elements upon onsubmit
		Form formAncestor = (Form) findAncestor(Form.class);
		if (formAncestor != null) {
			
			// inform ancestor form that we are having a customOnsubmit (see form-close.ftl [simple theme])
			enableAncestorFormCustomOnsubmit();
			
			
			// key -> select tag id, value -> headerKey (if exists)
			Map formOptiontransferselectIds = (Map) formAncestor.getParameters().get("optiontransferselectIds");
			Map formOptiontransferselectDoubleIds = (Map) formAncestor.getParameters().get("optiontransferselectDoubleIds");

			// init lists
			if (formOptiontransferselectIds == null) {
				formOptiontransferselectIds = new LinkedHashMap();
			}
			if (formOptiontransferselectDoubleIds == null) {
				formOptiontransferselectDoubleIds = new LinkedHashMap();
			}
			
			
			// id
			String tmpId = (String) getParameters().get("id");
			String tmpHeaderKey = (String) getParameters().get("headerKey");
			if (tmpId != null && (! formOptiontransferselectIds.containsKey(tmpId))) {
				formOptiontransferselectIds.put(tmpId, tmpHeaderKey);
			}
			
			// doubleId
			String tmpDoubleId = (String) getParameters().get("doubleId");
			String tmpDoubleHeaderKey = (String) getParameters().get("doubleHeaderKey");
			if (tmpDoubleId != null && (! formOptiontransferselectDoubleIds.containsKey(tmpDoubleId))) {
				formOptiontransferselectDoubleIds.put(tmpDoubleId, tmpDoubleHeaderKey);
			}
			
			formAncestor.getParameters().put("optiontransferselectIds", formOptiontransferselectIds);
			formAncestor.getParameters().put("optiontransferselectDoubleIds", formOptiontransferselectDoubleIds);
			
		}
		else {
			_log.warn("form enclosing optiontransferselect "+this+" not found, auto select upon form submit of optiontransferselect will not work");
		}
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

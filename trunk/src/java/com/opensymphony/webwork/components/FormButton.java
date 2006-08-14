/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FormButton.
 *
 * @author <a href="mailto:gielen@it-neering.net">Rene Gielen</a>
 * @author tm_jee
 */

public abstract class FormButton extends UIBean {

    static final String BUTTONTYPE_INPUT = "input";
    static final String BUTTONTYPE_BUTTON = "button";
    static final String BUTTONTYPE_IMAGE = "image";

    protected String action;
    protected String method;
    protected String align;
    protected String type;

    public FormButton(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    //public void evaluateParams() {
    public void evaluateExtraParams() {
    	super.evaluateExtraParams();
        if (align == null) {
            align = "right";
        }

        String submitType = BUTTONTYPE_INPUT;
        if (type != null && (BUTTONTYPE_BUTTON.equalsIgnoreCase(type) || (supportsImageType() && BUTTONTYPE_IMAGE.equalsIgnoreCase(type))))
        {
            submitType = type;
        }

        //super.evaluateParams();

        addParameter("type", submitType);

        if (!BUTTONTYPE_INPUT.equals(submitType) && (label == null)) {
            addParameter("label", getParameters().get("nameValue"));
        }

        if (action != null || method != null) {
            String name;

            if (action != null) {
                name = "action:" + findString(action);

                if (method != null) {
                    name += "!" + findString(method);
                }
            } else {
                name = "method:" + findString(method);
            }

            addParameter("name", name);
        }

        addParameter("align", findString(align));
    }
    
    /**
     * Override UIBean's implementation, such that component Html id is determined
     * in the following order :-
     * <ol>
     * 	 <li>This component id attribute</li>
     *   <li>[containing_form_id]_[this_component_name]</li>
     *   <li>[containing_form_id]_[this_component_action]_[this_component_method]</li>
     *   <li>[containing_form_id]_[this_component_method]</li>
     *   <li>[this_component_name]</li>
     *   <li>[this_component_action]_[this_component_method]</li>
     *   <li>[this_component_method]</li>
     *   <li>[an increasing sequential number unique to the form starting with 0]</li>
     * </ol>
     */
    protected void populateComponentHtmlId(Form form) {
        String _tmp_id = "";
        if (id != null) {
            // this check is needed for backwards compatibility with 2.1.x
            if (altSyntax()) {
            	_tmp_id = findString(id);
            } else {
            	_tmp_id = id;
            }
        }
        else {
        	if (form != null && form.getParameters().get("id") != null) {
				_tmp_id = _tmp_id + form.getParameters().get("id").toString() + "_";
        	}
			if (name != null) {
				_tmp_id = _tmp_id + escape(name);
			} else if (action != null || method != null){
				if (action != null) {
					_tmp_id = _tmp_id + escape(action);
				}
				if (method != null) {
					_tmp_id = _tmp_id + "_" + escape(method);
				}
			} else {
				// if form is null, this component is used, without a form, i guess
				// there's not much we could do then.
				if (form != null) {
					_tmp_id = _tmp_id + form.getSequence();
				}
			}
        }
		addParameter("id", _tmp_id);
    }

    /**
     * Indicate whether the concrete button supports the type "image".
     *
     * @return <tt>true</tt> if type image is supported.
     */
    protected abstract boolean supportsImageType();

    /**
     * Set action attribute.
     *
     * @ww.tagattribute required="false" type="String"
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Set method attribute.
     *
     * @ww.tagattribute required="false" type="String"
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * HTML align attribute.
     *
     * @ww.tagattribute required="false" type="String"
     */
    public void setAlign(String align) {
        this.align = align;
    }

    /**
     * The type of submit to use. Valid values are <i>input</i>, <i>button</i> and <i>image</i>.
     *
     * @ww.tagattribute required="false" type="String" default="input"
     */
    public void setType(String type) {
        this.type = type;
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.views.jsp.ParameterizedTag;
import com.opensymphony.webwork.views.jsp.WebWorkBodyTagSupport;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * Access a i18n-ized message. The message must be in a resource bundle
 * with the same name as the action that it is associated with. In practice
 * this means that you should create a properties file in the same package
 * as your Java class with the same name as your class, but with .properties
 * extension.
 *
 * See examples for further info on how to use.
 *
 * If the named message is not found, then the body of the tag will be used as default message.
 * If no body is used, then the name of the message will be used.
 *
 * @author Jason Carreira
 */
public class TextTag extends WebWorkBodyTagSupport implements ParameterizedTag {
    //~ Instance fields ////////////////////////////////////////////////////////

    List values;
    String nameAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setName(String name) {
        this.nameAttr = name;
    }

    public Map getParams() {
        return null;
    }

    public void addParam(String key, Object value) {
        addParam(value);
    }

    public void addParam(Object value) {
        if (value == null) {
            return;
        }

        if (values == null) {
            values = new ArrayList();
        }

        values.add(value);
    }

    // BodyTag implementation ----------------------------------------
    public int doEndTag() throws JspException {
        OgnlValueStack stack = getValueStack();

        String defaultMessage;

        if ((bodyContent != null) && (bodyContent.getString().trim().length() > 0)) {
            defaultMessage = bodyContent.getString().trim();
        } else {
            defaultMessage = nameAttr;
        }

        String expression = "text(" + nameAttr + ", " + defaultMessage;
        boolean pushed = false;

        if (values != null) {
            ListValueHolder listValueHolder = new ListValueHolder(values);
            stack.push(listValueHolder);
            pushed = true;
            expression = expression + ", textTagListValueHolderList";
        }

        expression = expression + ")";

        String msg = (String) stack.findValue(expression, String.class);

        if (pushed) {
            stack.pop();
        }

        try {
            pageContext.getOut().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
            throw new JspException(e);
        }

        return EVAL_PAGE;
    }

    public int doStartTag() throws JspException {
        values = null;

        return super.doStartTag();
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    private class ListValueHolder {
        // try to give it an uncommon name
        private List textTagListValueHolderList;

        public ListValueHolder(List textTagListValueHolderList) {
            this.textTagListValueHolderList = textTagListValueHolderList;
        }

        public List getTextTagListValueHolderList() {
            return textTagListValueHolderList;
        }
    }
}

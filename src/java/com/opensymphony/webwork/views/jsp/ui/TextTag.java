/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.views.jsp.WebWorkBodyTagSupport;
import com.opensymphony.webwork.views.jsp.ParamTag;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.TextProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import javax.servlet.jsp.JspException;


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
public class TextTag extends WebWorkBodyTagSupport implements ParamTag.Parametric {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log LOG = LogFactory.getLog(TextTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String value0Attr;
    protected String value1Attr;
    protected String value2Attr;
    protected String value3Attr;
    List values;
    String actualName;
    String nameAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setName(String name) {
        this.nameAttr = name;
    }

    public Map getParameters() {
        return null;
    }

    public void setValue0(String aName) {
        LOG.warn("The value attributes of TextTag are deprecated.");
        value0Attr = aName;
    }

    public void setValue1(String aName) {
        LOG.warn("The value attributes of TextTag are deprecated.");
        value1Attr = aName;
    }

    public void setValue2(String aName) {
        LOG.warn("The value attributes of TextTag are deprecated.");
        value2Attr = aName;
    }

    public void setValue3(String aName) {
        LOG.warn("The value attributes of TextTag are deprecated.");
        value3Attr = aName;
    }

    public void addParameter(String key, Object value) {
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
        OgnlValueStack stack = getStack();

        actualName = (String) findValue(nameAttr, String.class);

        // Add tag attribute values
        // These can be used to parameterize the i18n-ized message
        if (value0Attr != null) {
            addParam(findValue(value0Attr));
        }

        if (value1Attr != null) {
            addParam(findValue(value1Attr));
        }

        if (value2Attr != null) {
            addParam(findValue(value2Attr));
        }

        if (value3Attr != null) {
            addParam(findValue(value3Attr));
        }

        String defaultMessage;

        if ((bodyContent != null) && (bodyContent.getString().trim().length() > 0)) {
            defaultMessage = bodyContent.getString().trim();
        } else {
            defaultMessage = actualName;
        }

        String msg = null;
        for (Iterator iterator = getStack().getRoot().iterator(); iterator.hasNext();) {
            Object o = iterator.next();
            if (o instanceof TextProvider) {
                TextProvider tp = (TextProvider) o;
                msg = tp.getText(actualName, defaultMessage, values);
                break;
            }
        }

        if (msg != null) {
            try {
                pageContext.getOut().write(msg);
            } catch (IOException e) {
                throw new JspException(e);
            }
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

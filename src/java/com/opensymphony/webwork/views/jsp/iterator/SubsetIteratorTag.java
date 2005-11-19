/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.iterator;

import com.opensymphony.webwork.util.SubsetIteratorFilter;
import com.opensymphony.webwork.views.jsp.ActionTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;


/**
 * Instantiate a JavaBean.
 *
 * <p/> The bean may be an action, in which it is executed before used. It is lazily executed, which means that you can
 * set parameters by using the "param" tag.
 *
 * @author Rickard Öberg (rickard@dreambean.com)
 * @jsp.tag name="subset" bodycontent="JSP"
 */
public class SubsetIteratorTag extends ActionTag {
    String countAttr;

    String sourceAttr;
    String startAttr;

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setCount(String count) {
        countAttr = count;
    }

    public void setParent(Tag t) {
        super.setParent(t);
        setName("'" + SubsetIteratorFilter.class.getName() + "'");
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setSource(String source) {
        sourceAttr = source;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setStart(String start) {
        startAttr = start;
    }

    public int doStartTag() throws JspException {
        int returnVal = super.doStartTag();

        if (sourceAttr == null) {
            addParameter("source", findValue("top"));
        } else {
            addParameter("source", findValue(sourceAttr));
        }

        if (countAttr != null) {
            addParameter("count", findValue(countAttr));
        }

        if (startAttr != null) {
            addParameter("start", findValue(startAttr));
        }

        return returnVal;
    }
}

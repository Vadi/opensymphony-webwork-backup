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
 *        Instantiate a JavaBean.
 *
 * The bean may be an action, in which it is executed before used.
 * It is lazily executed, which means that you can set parameters
 * by using the "param" tag.
 *
 *        @author Rickard Öberg (rickard@dreambean.com)
 *        @version $Revision$
 */
public class SubsetIteratorTag extends ActionTag {
    //~ Instance fields ////////////////////////////////////////////////////////

    String countAttr;

    // Attributes ----------------------------------------------------
    String sourceAttr;
    String startAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setCount(String aCount) {
        countAttr = aCount;
    }

    // Constructor ---------------------------------------------------
    public void setParent(Tag t) {
        super.setParent(t);
        setName("'" + SubsetIteratorFilter.class.getName() + "'");
    }

    // Public --------------------------------------------------------
    public void setSource(String aName) {
        sourceAttr = aName;
    }

    public void setStart(String aStart) {
        startAttr = aStart;
    }

    public int doStartTag() throws JspException {
        int returnVal = super.doStartTag();

        if (sourceAttr == null) {
            addParam("source", findValue("top"));
        } else {
            addParam("source", findValue(sourceAttr));
        }

        if (countAttr != null) {
            addParam("count", findValue(countAttr));
        }

        if (startAttr != null) {
            addParam("start", findValue(startAttr));
        }

        return returnVal;
    }
}

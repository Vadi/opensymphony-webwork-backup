/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.iterator;

import com.opensymphony.webwork.util.IteratorGenerator;
import com.opensymphony.webwork.views.jsp.ActionTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;


/**
 *        Generate an iterator
 *
 *        @author Rickard Öberg (rickard@dreambean.com)
 *        @version $Revision$
 */
public class IteratorGeneratorTag extends ActionTag {
    //~ Instance fields ////////////////////////////////////////////////////////

    String countAttr;
    String separatorAttr;

    // Attributes ----------------------------------------------------
    String valueAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setCount(String aCount) {
        countAttr = aCount;
    }

    // Constructor ---------------------------------------------------
    public void setParent(Tag t) {
        super.setParent(t);
        setName("'" + IteratorGenerator.class.getName() + "'");
    }

    public void setSeparator(String aChar) {
        separatorAttr = aChar;
    }

    // Public --------------------------------------------------------
    public void setVal(String aValue) {
        valueAttr = aValue;
    }

    public int doStartTag() throws JspException {
        super.doStartTag();

        addParameter("values", findValue(valueAttr));

        if (countAttr != null) {
            addParameter("count", findValue(countAttr));
        }

        if (separatorAttr != null) {
            addParameter("separator", findValue(separatorAttr));
        }

        return EVAL_BODY_INCLUDE;
    }
}

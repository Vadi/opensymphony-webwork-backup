/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.iterator;

import com.opensymphony.webwork.util.SortIteratorFilter;
import com.opensymphony.webwork.views.jsp.ActionTag;

import java.util.Comparator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;


/**
 *        @author Rickard Öberg (rickard@dreambean.com)
 *        @version $Revision$
 */
public class SortIteratorTag extends ActionTag {
    //~ Instance fields ////////////////////////////////////////////////////////

    String comparatorAttr;

    // Attributes ----------------------------------------------------
    String sourceAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setComparator(String aComparator) {
        comparatorAttr = aComparator;
    }

    // Constructor ---------------------------------------------------
    public void setParent(Tag t) {
        super.setParent(t);
        setName("'" + SortIteratorFilter.class.getName() + "'");
    }

    // Public --------------------------------------------------------
    public void setSource(String aName) {
        sourceAttr = aName;
    }

    public int doStartTag() throws JspException {
        int returnVal = super.doStartTag();

        if (sourceAttr == null) {
            addParameter("source", findValue("top"));
        } else {
            addParameter("source", findValue(sourceAttr));
        }

        Comparator c = (Comparator) findValue(comparatorAttr);
        addParameter("comparator", c);

        return returnVal;
    }
}

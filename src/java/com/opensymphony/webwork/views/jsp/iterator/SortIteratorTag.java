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
        super.doStartTag();

        // todo: make this work with the new action tag
        // Pop holder temporarily while we resolve names
        //        Object holder = getStack().popValue();
        //
        //        if (sourceAttr == null)
        //            ((SortIteratorFilter) bean).setSource(findValue("."));
        //        else
        //            ((SortIteratorFilter) bean).setSource(findValue(sourceAttr));
        //
        //        Comparator c = (Comparator) findValue(comparatorAttr);
        //        ((SortIteratorFilter) bean).setComparator((Comparator) findValue(comparatorAttr));
        //
        //        // Push holder back on stack
        //        getStack().pushValue(holder);
        return EVAL_BODY_INCLUDE;
    }
}

package com.opensymphony.webwork.views.jsp.iterator;

import com.opensymphony.webwork.views.jsp.ActionTag;
import com.opensymphony.webwork.util.SortIteratorFilter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import java.util.Comparator;

/**
 *	@author Rickard Öberg (rickard@dreambean.com)
 *	@version $Revision$
 */
public class SortIteratorTag
        extends ActionTag {
    // Attributes ----------------------------------------------------
    String sourceAttr;
    String comparatorAttr;

    // Constructor ---------------------------------------------------
    public void setParent(Tag t) {
        super.setParent(t);
        setName("'" + SortIteratorFilter.class.getName() + "'");
    }

    // Public --------------------------------------------------------
    public void setSource(String aName) {
        sourceAttr = aName;
    }

    public void setComparator(String aComparator) {
        comparatorAttr = aComparator;
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

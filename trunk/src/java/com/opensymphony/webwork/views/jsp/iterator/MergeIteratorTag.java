package com.opensymphony.webwork.views.jsp.iterator;

import com.opensymphony.webwork.util.MergeIteratorFilter;
import com.opensymphony.webwork.views.jsp.ActionTag;

import javax.servlet.jsp.tagext.Tag;

/**
 *	Append a list of iterators. The values of the iterators will be merged
 * into one iterator.
 *
 *	@author Rickard Öberg (rickard@dreambean.com)
 *	@version $Revision$
 */
public class MergeIteratorTag
        extends ActionTag {
    // Attributes ----------------------------------------------------

    // Constructor ---------------------------------------------------
    public void setParent(Tag t) {
        super.setParent(t);
        setName("'" + MergeIteratorFilter.class.getName() + "'");
    }
}

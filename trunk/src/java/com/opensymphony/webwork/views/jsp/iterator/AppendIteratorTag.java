package com.opensymphony.webwork.views.jsp.iterator;

import com.opensymphony.webwork.views.jsp.ActionTag;
import com.opensymphony.webwork.util.AppendIteratorFilter;

import javax.servlet.jsp.tagext.Tag;

/**
 *	Append a list of iterators. The values of the iterators will be merged
 * into one iterator.
 *
 *	@author Rickard Öberg (rickard@dreambean.com)
 *	@version $Revision$
 */
public class AppendIteratorTag
        extends ActionTag {
    // Attributes ----------------------------------------------------

    // Constructor ---------------------------------------------------
    public void setParent(Tag t) {
        super.setParent(t);
        setName("'" + AppendIteratorFilter.class.getName() + "'");
    }
}

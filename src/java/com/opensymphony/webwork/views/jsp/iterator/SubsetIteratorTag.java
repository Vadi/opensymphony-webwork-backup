package com.opensymphony.webwork.views.jsp.iterator;

import com.opensymphony.webwork.util.SubsetIteratorFilter;
import com.opensymphony.webwork.views.jsp.ActionTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 *	Instantiate a JavaBean.
 *
 * The bean may be an action, in which it is executed before used.
 * It is lazily executed, which means that you can set parameters
 * by using the "param" tag.
 *
 *	@author Rickard Öberg (rickard@dreambean.com)
 *	@version $Revision$
 */
public class SubsetIteratorTag
        extends ActionTag {
    // Attributes ----------------------------------------------------
    String sourceAttr;
    String countAttr;
    String startAttr;

    // Constructor ---------------------------------------------------
    public void setParent(Tag t) {
        super.setParent(t);
        setName("'" + SubsetIteratorFilter.class.getName() + "'");
    }

    // Public --------------------------------------------------------
    public void setSource(String aName) {
        sourceAttr = aName;
    }

    public void setCount(String aCount) {
        countAttr = aCount;
    }

    public void setStart(String aStart) {
        startAttr = aStart;
    }

    public int doStartTag() throws JspException {
        super.doStartTag();

        // Pop holder temporarily while we resolve names
        Object holder = getStack().pop();

        // todo: make this work with the new action tag
//      if (sourceAttr == null)
//         ((SubsetIteratorFilter)bean).setSource(findValue("top"));
//      else
//         ((SubsetIteratorFilter)bean).setSource(findValue(sourceAttr));
//
//      if (countAttr != null)
//         ((SubsetIteratorFilter)bean).setCount(Integer.parseInt(findString(countAttr)));
//
//      if (startAttr != null)
//         ((SubsetIteratorFilter)bean).setStart(Integer.parseInt(findString(startAttr)));

        // Push holder back on stack
        getStack().push(holder);

        return EVAL_BODY_INCLUDE;
    }
}

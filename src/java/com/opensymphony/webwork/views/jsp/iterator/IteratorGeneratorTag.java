package com.opensymphony.webwork.views.jsp.iterator;

import com.opensymphony.webwork.util.IteratorGenerator;
import com.opensymphony.webwork.views.jsp.ActionTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 *	Generate an iterator
 *
 *	@author Rickard Öberg (rickard@dreambean.com)
 *	@version $Revision$
 */
public class IteratorGeneratorTag
        extends ActionTag {
    // Attributes ----------------------------------------------------
    String valueAttr;
    String countAttr;
    String separatorAttr;

    // Constructor ---------------------------------------------------
    public void setParent(Tag t) {
        super.setParent(t);
        setName("'" + IteratorGenerator.class.getName() + "'");
    }

    // Public --------------------------------------------------------
    public void setVal(String aValue) {
        valueAttr = aValue;
    }

    public void setCount(String aCount) {
        countAttr = aCount;
    }

    public void setSeparator(String aChar) {
        separatorAttr = aChar;
    }

    // BodyTag implementation ----------------------------------------
    public int doStartTag() throws JspException {
        super.doStartTag();

        // Pop holder temporarily while we resolve names
//        Object holder = getStack().popValue();
//
//        ((IteratorGenerator) bean).setValues(findValue(valueAttr));
//        if (countAttr != null)
//            ((IteratorGenerator) bean).setCount(Integer.parseInt(findString(countAttr)));
//        if (separatorAttr != null)
//            ((IteratorGenerator) bean).setSeparator(findString(separatorAttr));
//
//        // Push holder back on stack
//        getStack().pushValue(holder);

        return EVAL_BODY_INCLUDE;
    }
}

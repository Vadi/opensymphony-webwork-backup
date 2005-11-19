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
 * Generate an iterator
 *
 * @jsp.tag name="generator" bodycontent="JSP"
 * @author Rickard Öberg (rickard@dreambean.com)
 */
public class IteratorGeneratorTag extends ActionTag {
    String countAttr;
    String separatorAttr;
    String valueAttr;

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setCount(String count) {
        countAttr = count;
    }

    public void setParent(Tag t) {
        super.setParent(t);
        setName("'" + IteratorGenerator.class.getName() + "'");
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setSeparator(String separator) {
        separatorAttr = separator;
    }

    /**
     * @jsp.attribute required="true"  rtexprvalue="true"
     */
    public void setVal(String val) {
        valueAttr = val;
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

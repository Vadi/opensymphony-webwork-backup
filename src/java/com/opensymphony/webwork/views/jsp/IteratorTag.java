/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.util.MakeIterator;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.Iterator;


/**
 * @see com.opensymphony.webwork.components.IteratorComponent
 */
public class IteratorTag extends WebWorkBodyTagSupport {

    protected Iterator iterator;
    protected IteratorStatus status;
    protected Object oldStatus;
    protected IteratorStatus.StatusState statusState;
    protected String statusAttr;
    protected String value;

    /**
     * @ww.tagattribute required="false" type="Boolean" default="false"
     * description="if specified, an instanceof IteratorStatus will be pushed into stack upon each iteration"
     */
    public void setStatus(String status) {
        this.statusAttr = status;
    }

    /**
     * @ww.tagattribute required="false"
     * description="the iteratable source to iterate over, else an the object itself will be put into a newly created List"
     */
    public void setValue(String value) {
        this.value = value;
    }

    public int doAfterBody() throws JspException {
        OgnlValueStack stack = getStack();
        stack.pop();

        if (iterator.hasNext()) {
            Object currentValue = iterator.next();
            stack.push(currentValue);

            String id = getId();

            if ((id != null) && (currentValue != null)) {
                pageContext.setAttribute(id, currentValue);
                pageContext.setAttribute(id, currentValue, PageContext.REQUEST_SCOPE);
            }

            // Update status
            if (status != null) {
                statusState.next(); // Increase counter
                statusState.setLast(!iterator.hasNext());
            }

            return EVAL_BODY_AGAIN;
        } else {
            // Reset status object in case someone else uses the same name in another iterator tag instance
            if (status != null) {
                if (oldStatus == null) {
                    stack.getContext().put(statusAttr, null);
                } else {
                    stack.getContext().put(statusAttr, oldStatus);
                }
            }

            // Release objects
            iterator = null;
            status = null;

            try {
                bodyContent.writeOut(bodyContent.getEnclosingWriter());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }

            return SKIP_BODY;
        }
    }

    public int doStartTag() throws JspException {
        //Create an iterator status if the status attribute was set.
        if (statusAttr != null) {
            statusState = new IteratorStatus.StatusState();
            status = new IteratorStatus(statusState);
        }

        OgnlValueStack stack = getStack();

        if (value == null) {
            value = "top";
        }

        iterator = MakeIterator.convert(findValue(value));

        // get the first
        if ((iterator != null) && iterator.hasNext()) {
            Object currentValue = iterator.next();
            stack.push(currentValue);

            String id = getId();

            if ((id != null) && (currentValue != null)) {
                pageContext.setAttribute(id, currentValue);
                pageContext.setAttribute(id, currentValue, PageContext.REQUEST_SCOPE);
            }

            // Status object
            if (statusAttr != null) {
                statusState.setLast(!iterator.hasNext());
                oldStatus = stack.getContext().get(statusAttr);
                stack.getContext().put(statusAttr, status);
            }

            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }
}
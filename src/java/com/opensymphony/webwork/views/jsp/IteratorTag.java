/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.util.MakeIterator;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;


/**
 * <p>Iterator will iterate over a value. An iterable value can be either of: java.util.Collection,
 * java.util.Iterator, java.util.Enumeration, java.util.Map, array.</p>
 *
 * <p>Example:</p>
 * <pre>
 * &lt;ww:iterator value="days"&gt;
 *   &lt;p&gt;day is: &lt;ww:property/&gt;&lt;/p&gt;
 * &lt;/ww:iterator&gt;
 * </pre>
 * <p>The above example retrieves the value of the getDays() method of the current object on the value stack and uses
 * it to iterate over. The &lt;ww:property/&gt; tag prints out the current value of the iterator.</p>
 * <p>The following example uses a {@link BeanTag} and places it into the ActionContext. The iterator tag will
 * retrieve that object from the ActionContext and then calls its getDays() method as above. The status attribute is also
 * used to create a {@link IteratorStatus} object, which in this example, its odd() method is used to
 * alternate row colours:</p>
 * <pre>
 * &lt;ww:bean name="com.opensymphony.webwork.example.IteratorExample" id="it"&gt;
 *   &lt;ww:param name="day" value="'foo'"/&gt;
 *   &lt;ww:param name="day" value="'bar'"/&gt;
 * &lt;/ww:bean&gt;

 * &lt;table border="0" cellspacing="0" cellpadding="1"&gt;
 * &lt;tr&gt;
 *   &lt;th&gt;Days of the week&lt;/th&gt;
 * &lt;/tr&gt;
 *
 * &lt;ww:iterator value="#it.days" status="rowstatus"&gt;
 *   &lt;tr&gt;
 *     &lt;ww:if test="#rowstatus.odd == true"&gt;
 *       &lt;td style="background: grey"&gt;&lt;ww:property/&gt;&lt;/td&gt;
 *     &lt;/ww:if&gt;
 *     &lt;ww:else&gt;
 *       &lt;td&gt;&lt;ww:property/&gt;&lt;/td&gt;
 *     &lt;/ww:else&gt;
 *   &lt;/tr&gt;
 * &lt;/ww:iterator&gt;
 * &lt;/table&gt;
 * </pre>
 *
 * @author $Author$
 * @author Rick Salsa (rsal@mb.sympatico.ca)
 * @version $Revision$
 */
public class IteratorTag extends WebWorkBodyTagSupport {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected Iterator iterator;
    protected IteratorStatus status;
    protected Object oldStatus;
    protected IteratorStatus.StatusState statusState;
    protected String id;
    protected String statusAttr;
    protected String value;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String name) {
        this.statusAttr = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int doAfterBody() throws JspException {
        OgnlValueStack stack = getStack();
        stack.pop();

        if (iterator.hasNext()) {
            Object currentValue = iterator.next();
            stack.push(currentValue);

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

        iterator = MakeIterator.convert(stack.findValue(value));

        // get the first
        if ((iterator != null) && iterator.hasNext()) {
            stack.push(iterator.next());

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

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        this.iterator = null;
        this.status = null;
        this.oldStatus = null;
        this.statusState = null;
        this.statusAttr = null;
        this.value = null;
    }
}

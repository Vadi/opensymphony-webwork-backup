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
 * <!-- START SNIPPET: javadoc -->
 * <p>Iterator will iterate over a value. An iterable value can be either of: java.util.Collection,
 * java.util.Iterator, java.util.Enumeration, java.util.Map, array.</p>
 * <p/>
 * <!-- END SNIPPET: javadoc -->
 * 
 * <!-- START SNIPPET: params -->
 * <ul>
 * 		<li>status (String) - if specified, an instanceof IteratorStatus will be pushed into stack upon each iteration</li>
 * 		<li>value (Object) - the source to iterate over, must be iteratable, else an the object itself will be put into a newly created List (see MakeIterator#convert(Object)</li>
 * 		<li>id (String) - if specified the current iteration object will be place with this id in both request and page scope</li>
 * </ul>
 * <!-- END SNIPPET: params -->
 * 
 * <!-- START SNIPPET: example -->
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
 * &lt;ww:bean name="'com.opensymphony.webwork.example.IteratorExample'" id="it"&gt;
 *   &lt;ww:param name="'day'" value="'foo'"/&gt;
 *   &lt;ww:param name="'day'" value="'bar'"/&gt;
 * &lt;/ww:bean&gt;
 * <p/>
 * &lt;table border="0" cellspacing="0" cellpadding="1"&gt;
 * &lt;tr&gt;
 *   &lt;th&gt;Days of the week&lt;/th&gt;
 * &lt;/tr&gt;
 * <p/>
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
 * <!-- END SNIPPET: example -->
 *
 * @jsp.tag name="iterator" bodycontent="JSP"
 * @author $Author$
 * @author Rick Salsa (rsal@mb.sympatico.ca)
 * @author tm_jee ( tm_jee(at)yahoo.co.uk )
 * @version $Revision$
 */
public class IteratorTag extends WebWorkBodyTagSupport {

    protected Iterator iterator;
    protected IteratorStatus status;
    protected Object oldStatus;
    protected IteratorStatus.StatusState statusState;
    protected String statusAttr;
    protected String value;

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setStatus(String status) {
        this.statusAttr = status;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
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

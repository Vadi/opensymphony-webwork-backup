/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.dispatcher.ServletDispatcher;
import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.webwork.dispatcher.ApplicationMap;
import com.opensymphony.webwork.ServletActionContext;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


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
public class IteratorTag extends BodyTagSupport {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected Iterator iterator;
    protected IteratorStatus status;
    protected Object oldStatus;
    protected OgnlValueStack stack;
    protected IteratorStatus.StatusState statusState;
    protected String statusAttr;
    protected String value;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setStatus(String name) {
        this.statusAttr = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int doAfterBody() throws JspException {
        stack.pop();

        if (iterator.hasNext()) {
            stack.push(iterator.next());

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
                    ActionContext.getContext().put(statusAttr, null);
                } else {
                    ActionContext.getContext().put(statusAttr, oldStatus);
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

        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        stack = (OgnlValueStack) req.getAttribute("webwork.valueStack");
        if (stack == null)
        {
            stack = new OgnlValueStack();
            HttpServletResponse res = (HttpServletResponse) pageContext.getResponse();
            Map extraContext = ServletDispatcher.createContextMap(req.getParameterMap(),
                    new SessionMap(req.getSession()),
                    new ApplicationMap(pageContext.getServletContext()),
                    req,
                    res,
                    pageContext.getServletConfig());
            extraContext.put(ServletActionContext.PAGE_CONTEXT, pageContext);
            stack.getContext().putAll(extraContext);
            req.setAttribute("webwork.valueStack", stack);
        }


        iterator = getIterator();

        // get the first
        if ((iterator != null) && iterator.hasNext()) {
            stack.push(iterator.next());

            // Status object
            if (statusAttr != null) {
                statusState.setLast(!iterator.hasNext());
                oldStatus = ActionContext.getContext().get(statusAttr);
                ActionContext.getContext().put(statusAttr, status);
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
        this.stack = null;
        this.statusState = null;
        this.statusAttr = null;
        this.value = null;
    }

    private Iterator getIterator() {
        if (stack == null) {
            return null;
        }

        Object o = stack.findValue(value);

        if (o == null) {
            return null;
        }

        if (o instanceof Collection) {
            return ((Collection) o).iterator();
        } else if (o.getClass().isArray()) {
            return Arrays.asList((Object[]) o).iterator();
        } else if (o instanceof Map) {
            return ((Map) o).entrySet().iterator();
        } else if (o instanceof Iterator) {
            return (Iterator) o;
        } else if (o instanceof Enumeration) {
            return new EnumeratorIterator((Enumeration) o);
        }

        return null;
    }

}

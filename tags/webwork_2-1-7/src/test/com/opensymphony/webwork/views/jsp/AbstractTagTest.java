/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.velocity.AbstractTagDirective;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import junit.framework.TestCase;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * AbstractTagTest
 * User: jcarreira
 * Created: Oct 17, 2003 10:24:34 PM
 */
public abstract class AbstractTagTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected Action action;
    protected Map context;
    protected Map session;
    protected OgnlValueStack stack;

    /**
     * contains the buffer that our unit test will write to.  we can later verify this buffer for correctness.
     */
    protected StringWriter writer;
    protected WebWorkMockHttpServletRequest request;
    protected WebWorkMockPageContext pageContext;
    protected HttpServletResponse response;

    //~ Constructors ///////////////////////////////////////////////////////////

    public AbstractTagTest() {
        super();
    }

    public AbstractTagTest(String s) {
        super(s);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Constructs the action that we're going to test against.  For most UI tests, this default action should be enough.
     * However, simply override getAction to return a custom Action if you need something more sophisticated.
     *
     * @return the Action to be added to the OgnlValueStack as part of the unit test
     */
    public Action getAction() {
        return new TestAction();
    }

    protected void setUp() throws Exception {
        super.setUp();

        /**
         * create our standard mock objects
         */
        action = this.getAction();
        stack = new OgnlValueStack();
        context = stack.getContext();
        stack.push(action);

        request = new WebWorkMockHttpServletRequest();
        request.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, stack);
        response = new WebWorkMockHttpServletResponse();
        request.setSession(new WebWorkMockHttpSession());

        writer = new StringWriter();

        JspWriter jspWriter = new WebWorkMockJspWriter(writer);
        context.put(AbstractTagDirective.VELOCITY_WRITER, writer);

        pageContext = new WebWorkMockPageContext();
        pageContext.setRequest(request);
        pageContext.setResponse(response);
        pageContext.setJspWriter(jspWriter);

        session = new HashMap();

        ActionContext.setContext(new ActionContext(context));

        Configuration.setConfiguration(null);
    }

    protected void tearDown() throws Exception {
        pageContext.verify();
        request.verify();
    }
}

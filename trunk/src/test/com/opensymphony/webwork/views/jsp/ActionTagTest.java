/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.dynamic.Mock;

import com.mockobjects.servlet.MockHttpSession;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.TestConfigurationProvider;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;


/**
 * ActionTagTest
 * @author Jason Carreira
 * Created Mar 27, 2003 9:10:27 PM
 */
public class ActionTagTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    HttpServletRequest request;
    Mock reqMock;
    Mock servletConfigMock;
    WebWorkMockPageContext pageContext;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testActionTagWithNamespace() {
        reqMock.expectAndReturn("getServletPath", TestConfigurationProvider.TEST_NAMESPACE + "/" + "foo.action");

        ActionTag tag = new ActionTag();
        tag.setPageContext(pageContext);
        tag.setName(TestConfigurationProvider.TEST_NAMESPACE_ACTION);
        tag.setId(TestConfigurationProvider.TEST_NAMESPACE_ACTION);

        try {
            tag.doStartTag();
            tag.doEndTag();

            Object o = pageContext.findAttribute(TestConfigurationProvider.TEST_NAMESPACE_ACTION);
            assertTrue(o instanceof TestAction);

            // Since the tag executes the proxy, the ActionContext is set back to the original action context, we
            // want to check the old one (the one the proxy was run as)
            ActionInvocation actionInvocation = tag.proxy.getLastContext().getActionInvocation();
            assertEquals(TestConfigurationProvider.TEST_NAMESPACE, actionInvocation.getProxy().getNamespace());
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }

        reqMock.verify();
        pageContext.verify();
    }

    public void testRenderer() {
        reqMock.expectAndReturn("getServletPath", "/foo.action");

        ActionTag tag = new ActionTag();
        tag.setName("testAction");
        tag.setId("testAction");

        try {
            tag.addParam("foo", "myFoo");
            tag.render(null, null);

            OgnlValueStack stack = ActionContext.getContext().getValueStack();
            assertEquals("myFoo", stack.findValue("#testAction.foo"));
            assertEquals(0, ActionContext.getContext().getValueStack().size());

            Object o = stack.findValue("#testAction");
            assertTrue(o instanceof TestAction);
            assertEquals("myFoo", ((TestAction) o).getFoo());
            assertEquals(Action.SUCCESS, ((TestAction) o).getResult());
        } catch (Exception e) {
            fail("unexpected " + e.getClass().getName() + " thrown!");
        }

        reqMock.verify();
        pageContext.verify();
    }

    public void testSimple() {
        reqMock.expectAndReturn("getServletPath", "/foo.action");

        ActionTag tag = new ActionTag();
        tag.setPageContext(pageContext);
        tag.setName("testAction");
        tag.setId("testAction");

        try {
            tag.doStartTag();
            tag.addParam("foo", "myFoo");
            tag.doEndTag();

            OgnlValueStack stack = ActionContext.getContext().getValueStack();
            assertEquals("myFoo", stack.findValue("#testAction.foo"));
            assertEquals(0, ActionContext.getContext().getValueStack().size());

            Object o = pageContext.findAttribute("testAction");
            assertTrue(o instanceof TestAction);
            assertEquals("myFoo", ((TestAction) o).getFoo());
            assertEquals(Action.SUCCESS, ((TestAction) o).getResult());
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }

        reqMock.verify();
        pageContext.verify();
    }

    public void testSimpleWithoutServletActionContext() {
        ServletActionContext.setServletConfig(null);
        ServletActionContext.setRequest(null);
        ServletActionContext.setResponse(null);
        this.testSimple();
    }

    protected void setUp() throws Exception {
        ConfigurationManager.clearConfigurationProviders();
        ConfigurationManager.addConfigurationProvider(new TestConfigurationProvider());
        ConfigurationManager.getConfiguration().reload();

        OgnlValueStack vs = new OgnlValueStack();
        ActionContext.setContext(new ActionContext(vs.getContext()));

        reqMock = new Mock(HttpServletRequest.class);
        request = (HttpServletRequest) reqMock.proxy();
        pageContext = new WebWorkMockPageContext();
        pageContext.setRequest(request);

        reqMock.expectAndReturn("getAttribute", "DefaultComponentManager", null);
        reqMock.expectAndReturn("getSession", new MockHttpSession());

        pageContext.setRequest(request);

        servletConfigMock = new Mock(ServletConfig.class);
        servletConfigMock.expectAndReturn("getServletContext", null);
        ServletActionContext.setRequest(request); // provide the request to the ServletActionContext for buildNamespace
        ServletActionContext.setServletConfig((ServletConfig) servletConfigMock.proxy());
    }
}

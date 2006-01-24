/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.TestActionTagResult;
import com.opensymphony.webwork.TestConfigurationProvider;
import com.opensymphony.webwork.components.ActionComponent;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;


/**
 * ActionTagTest
 *
 * @author Jason Carreira
 * @author tmjee ( tm_jee(at)yahoo.co.uk )
 *         Created Mar 27, 2003 9:10:27 PM
 */
public class ActionTagTest extends AbstractTagTest {

    public void testActionTagWithNamespace() {
        request.setupGetServletPath(TestConfigurationProvider.TEST_NAMESPACE + "/" + "foo.action");

        ActionTag tag = new ActionTag();
        tag.setPageContext(pageContext);
        tag.setName(TestConfigurationProvider.TEST_NAMESPACE_ACTION);
        tag.setId(TestConfigurationProvider.TEST_NAMESPACE_ACTION);

        try {
            tag.doStartTag();
            ActionComponent ac = ((ActionComponent) tag.component);
            tag.doEndTag();
            ActionProxy proxy = ac.getProxy();

            Object o = pageContext.findAttribute(TestConfigurationProvider.TEST_NAMESPACE_ACTION);
            assertTrue(o instanceof TestAction);

            assertEquals(TestConfigurationProvider.TEST_NAMESPACE, proxy.getNamespace());
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testSimple() {
        request.setupGetServletPath("/foo.action");

        ActionTag tag = new ActionTag();
        tag.setPageContext(pageContext);
        tag.setName("testAction");
        tag.setId("testAction");

        int stackSize = stack.size();

        try {
            tag.doStartTag();
            tag.addParameter("foo", "myFoo");
            tag.doEndTag();

            assertEquals(stack.size(), ActionContext.getContext().getValueStack().size());
            assertEquals("myFoo", stack.findValue("#testAction.foo"));
            assertEquals(stackSize, stack.size());

            Object o = pageContext.findAttribute("testAction");
            assertTrue(o instanceof TestAction);
            assertEquals("myFoo", ((TestAction) o).getFoo());
            assertEquals(Action.SUCCESS, ((TestAction) o).getResult());
        } catch (JspException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    public void testSimpleWithoutServletActionContext() {
        ServletActionContext.setRequest(null);
        ServletActionContext.setResponse(null);
        this.testSimple();
    }
    
    public void testActionWithExecuteResult() throws Exception {
    	ActionTag tag = new ActionTag();
    	tag.setPageContext(pageContext);
    	tag.setNamespace("");
    	tag.setName("testActionTagAction");
    	tag.setExecuteResult(true);
    	
    	tag.doStartTag();
    	
    	// tag clear components on doEndTag
    	ActionComponent component = (ActionComponent) tag.getComponent();
    	
    	tag.doEndTag();

    	TestActionTagResult result = (TestActionTagResult) component.getProxy().getInvocation().getResult();
    	
    	assertTrue(stack.getContext().containsKey(ServletActionContext.PAGE_CONTEXT));
    	assertTrue(stack.getContext().get(ServletActionContext.PAGE_CONTEXT) instanceof PageContext);
    	assertTrue(result.isExecuted());
    }
    
    public void testActionWithoutExecuteResult() throws Exception {
    	ActionTag tag = new ActionTag();
    	tag.setPageContext(pageContext);
    	tag.setNamespace("");
    	tag.setName("testActionTagAction");
    	tag.setExecuteResult(false);
    	
    	tag.doStartTag();
    	
    	// tag clear components on doEndTag, so we need to get it here
    	ActionComponent component = (ActionComponent) tag.getComponent();
    	
    	tag.doEndTag();
    	
    	TestActionTagResult result = (TestActionTagResult) component.getProxy().getInvocation().getResult();
    	
    	assertTrue(stack.getContext().containsKey(ServletActionContext.PAGE_CONTEXT));
    	assertTrue(stack.getContext().get(ServletActionContext.PAGE_CONTEXT) instanceof PageContext);
    	assertNull(result); // result is never executed, hence never set into invocation
    }

    protected void setUp() throws Exception {
        super.setUp();

        ConfigurationManager.clearConfigurationProviders();
        ConfigurationManager.addConfigurationProvider(new TestConfigurationProvider());
        ConfigurationManager.getConfiguration().reload();

        ActionContext actionContext = new ActionContext(context);
        actionContext.setValueStack(stack);
        ActionContext.setContext(actionContext);
    }

    protected void tearDown() throws Exception {
        ConfigurationManager.destroyConfiguration();

        OgnlValueStack stack = new OgnlValueStack();
        ActionContext.setContext(new ActionContext(stack.getContext()));
        super.tearDown();
    }
}

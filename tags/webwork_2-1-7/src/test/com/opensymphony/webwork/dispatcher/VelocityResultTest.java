/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.mockobjects.dynamic.Mock;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.util.OgnlValueStack;
import junit.framework.TestCase;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class VelocityResultTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    ActionInvocation actionInvocation;
    Mock mockActionProxy;
    OgnlValueStack stack;
    String namespace;
    TestVelocityEngine velocity;
    VelocityResult result;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testCanResolveLocationUsingOgnl() throws Exception {
        TestResult result = new TestResult();

        String location = "/myaction.action";
        Bean bean = new Bean();
        bean.setLocation(location);

        OgnlValueStack stack = ActionContext.getContext().getValueStack();
        stack.push(bean);

        assertEquals(location, stack.findValue("location"));

        result.setLocation("${location}");
        result.execute(actionInvocation);
        assertEquals(location, result.finalLocation);
    }

    public void testCanResolveLocationUsingStaticExpression() throws Exception {
        TestResult result = new TestResult();
        String location = "/any.action";
        result.setLocation("${'" + location + "'}");
        result.execute(actionInvocation);
        assertEquals(location, result.finalLocation);
    }

    public void testResourcesFoundUsingAbsolutePath() throws Exception {
        String location = "/WEB-INF/views/registration.vm";

        Template template = result.getTemplate(stack, velocity, actionInvocation, location);
        assertNotNull(template);
        assertEquals("expect absolute locations to be handled as is", location, velocity.templateName);
    }

    public void testResourcesFoundUsingNames() throws Exception {
        String location = "Registration.vm";
        String expectedTemplateName = namespace + "/" + location;

        Template template = result.getTemplate(stack, velocity, actionInvocation, location);
        assertNotNull(template);
        assertEquals("expect the prefix to be appended to the path when the location is not absolute", expectedTemplateName, velocity.templateName);
    }

    protected void setUp() throws Exception {
        namespace = "/html";
        result = new VelocityResult();
        stack = new OgnlValueStack();
        ActionContext.getContext().setValueStack(stack);
        velocity = new TestVelocityEngine();
        mockActionProxy = new Mock(ActionProxy.class);
        mockActionProxy.expectAndReturn("getNamespace", "/html");

        Mock mockActionInvocation = new Mock(ActionInvocation.class);
        mockActionInvocation.expectAndReturn("getProxy", mockActionProxy.proxy());
        mockActionInvocation.expectAndReturn("getStack", stack);
        actionInvocation = (ActionInvocation) mockActionInvocation.proxy();
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    class Bean {
        private String location;

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLocation() {
            return location;
        }
    }

    class TestResult extends WebWorkResultSupport {
        public String finalLocation;

        protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
            this.finalLocation = finalLocation;
        }
    }

    class TestVelocityEngine extends VelocityEngine {
        public String templateName;

        public Template getTemplate(String templateName) throws ResourceNotFoundException, ParseErrorException, Exception {
            this.templateName = templateName;

            return new Template();
        }
    }
}

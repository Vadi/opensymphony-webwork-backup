/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.mockobjects.dynamic.Mock;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;


/**
 *
 *
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
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
        String location = "/myaction.action";
        Bean bean = new Bean();
        bean.setLocation(location);
        stack.push(bean);

        assertEquals(location, stack.findValue("location"));

        result.setParse(true);
        result.getTemplate(stack, velocity, actionInvocation, "${location}");
        assertEquals(location, velocity.templateName);
    }

    public void testCanResolveLocationUsingStaticExpression() throws Exception {
        String location = "/any.action";
        result.setParse(true);
        result.getTemplate(stack, velocity, actionInvocation, "${'" + location + "'}");
        assertEquals(location, velocity.templateName);
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
        velocity = new TestVelocityEngine();
        mockActionProxy = new Mock(ActionProxy.class);
        mockActionProxy.expectAndReturn("getNamespace", "/html");

        Mock mockActionInvocation = new Mock(ActionInvocation.class);
        mockActionInvocation.expectAndReturn("getProxy", mockActionProxy.proxy());
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

    class TestVelocityEngine extends VelocityEngine {
        public String templateName;

        public Template getTemplate(String templateName) throws ResourceNotFoundException, ParseErrorException, Exception {
            this.templateName = templateName;

            return new Template();
        }
    }
}

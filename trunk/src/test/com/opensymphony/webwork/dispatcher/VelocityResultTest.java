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

    Mock mockActionInvocation;
    Mock mockActionProxy;
    OgnlValueStack stack;
    String namespace;
    TestVelocityEngine velocity;
    VelocityResult result;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testResourcesFoundUsingAbsolutePath() throws Exception {
        String location = "/WEB-INF/views/registration.vm";

        result.setLocation(location);

        Template template = result.getTemplate(stack, velocity, (ActionInvocation) mockActionInvocation.proxy());
        assertNotNull(template);
        assertEquals("expect absolute locations to be handled as is", location, velocity.templateName);
    }

    public void testResourcesFoundUsingNames() throws Exception {
        String location = "Registration.vm";
        String expectedTemplateName = namespace + "/" + location;

        result.setLocation(location);

        Template template = result.getTemplate(stack, velocity, (ActionInvocation) mockActionInvocation.proxy());
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
        mockActionInvocation = new Mock(ActionInvocation.class);
        mockActionInvocation.expectAndReturn("getProxy", mockActionProxy.proxy());
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    class TestVelocityEngine extends VelocityEngine {
        public String templateName;

        public Template getTemplate(String templateName) throws ResourceNotFoundException, ParseErrorException, Exception {
            this.templateName = templateName;

            return new Template();
        }
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import com.mockobjects.dynamic.Mock;

import com.opensymphony.webwork.views.jsp.WebWorkMockServletContext;
import com.opensymphony.webwork.views.velocity.ui.MockTag;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.File;
import java.io.StringWriter;

import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by IntelliJ IDEA.
 * User: matt
 * Date: May 28, 2003
 * Time: 9:48:33 AM
 * To change this template use Options | File Templates.
 */
public class TagDirectiveTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    Mock mockConfig;
    Mock mockRequest;
    Mock mockResponse;
    MockTag mockTag;
    VelocityEngine velocityEngine;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testBodyTag() throws Exception {
        Template template = velocityEngine.getTemplate("/com/opensymphony/webwork/views/velocity/bodytag.vm");
        StringWriter writer = new StringWriter();
        Context context = VelocityManager.createContext((ServletConfig) mockConfig.proxy(), (ServletRequest) mockRequest.proxy(), (ServletResponse) mockResponse.proxy());
        template.merge(context, writer);

        // verify that we got one param, hello=world
        Map params = mockTag.getParams();
        Assert.assertNotNull(params);
        Assert.assertEquals(2, params.size());
        Assert.assertTrue(params.containsKey("hello"));
        Assert.assertEquals("world", params.get("hello")); // hello = world
        Assert.assertTrue(params.containsKey("foo"));
        Assert.assertEquals("bar", params.get("foo")); // foo = bar

        // verify that our date thingy was populated correctly
        Calendar cal = Calendar.getInstance();
        cal.set(2003, Calendar.MARCH, 25, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Assert.assertNotNull(mockTag.getDate());
        Assert.assertEquals(cal.getTime().getTime(), mockTag.getDate().getTime());

        // now verify that each of the fields was set as we expected
        Assert.assertEquals("hello world", mockTag.getString());
        Assert.assertEquals(new Float(2.1), mockTag.getFloat());
        Assert.assertEquals(new Double(5.6), mockTag.getDouble());
        Assert.assertEquals(new Integer(13), mockTag.getInteger());
        Assert.assertEquals(new Long(1234), mockTag.getLong());
    }

    /**
     * pretty much the same as the BodyTag test, but we're not passing in any parameters
     * @throws Exception
     */
    public void testTag() throws Exception {
        Template template = velocityEngine.getTemplate("/com/opensymphony/webwork/views/velocity/tag.vm");
        StringWriter writer = new StringWriter();
        Context context = VelocityManager.createContext((ServletConfig) mockConfig.proxy(), (ServletRequest) mockRequest.proxy(), (ServletResponse) mockResponse.proxy());
        template.merge(context, writer);

        // verify that our date thingy was populated correctly
        Calendar cal = Calendar.getInstance();
        cal.set(2003, Calendar.MARCH, 25, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Assert.assertNotNull(mockTag.getDate());
        Assert.assertEquals(cal.getTime().getTime(), mockTag.getDate().getTime());

        // now verify that each of the fields was set as we expected
        Assert.assertEquals("hello world", mockTag.getString());
        Assert.assertEquals(new Float(2.1), mockTag.getFloat());
        Assert.assertEquals(new Double(5.6), mockTag.getDouble());
        Assert.assertEquals(new Integer(13), mockTag.getInteger());
        Assert.assertEquals(new Long(1234), mockTag.getLong());
    }

    protected void setUp() throws Exception {
        super.setUp();

        ConfigurationManager.clearConfigurationProviders();
        ConfigurationManager.destroyConfiguration();

        OgnlValueStack stack = new OgnlValueStack();
        ActionContext.setContext(new ActionContext(stack.getContext()));

        /**
         * construct our sandbox VelocityEngine
         */
        Properties props = new Properties();

        props.setProperty("resource.loader", "file");

        // adding src/java to the Velocity load path
        props.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
        props.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        props.setProperty("file.resource.loader.path", System.getProperty("webwork.webapp.path", new File("src/test").getAbsolutePath()));
        props.setProperty("file.resource.loader.cache", "false");
        props.setProperty("file.resource.loader.modificationCheckInterval", "2");
        props.setProperty("userdirective", "com.opensymphony.webwork.views.velocity.TagDirective,com.opensymphony.webwork.views.velocity.BodyTagDirective,com.opensymphony.webwork.views.velocity.ParamDirective");

        props.setProperty("runtime.log", "velocity.log");

        velocityEngine = new VelocityEngine();
        velocityEngine.init(props);

        //        Mock aservletContext = new Mock(ServletContext.class);
        //        aservletContext.expectAndReturn("getRealPath", C.ANY_ARGS, System.getProperty("webwork.webapp.path", new File("src/test").getAbsolutePath()));
        //        aservletContext.expectAndReturn("getRealPath", C.ANY_ARGS, System.getProperty("webwork.webapp.path", new File("src/test").getAbsolutePath()));
        //        aservletContext.expectAndReturn("getRealPath", C.ANY_ARGS, System.getProperty("webwork.webapp.path", new File("src/test").getAbsolutePath()));
        WebWorkMockServletContext servletContext = new WebWorkMockServletContext();
        servletContext.setupGetRealPath(System.getProperty("webwork.webapp.path", new File("src/test").getAbsolutePath()));

        mockConfig = new Mock(ServletConfig.class);
        mockRequest = new Mock(HttpServletRequest.class);
        mockResponse = new Mock(HttpServletResponse.class);

        mockRequest.matchAndReturn("getAttribute", "webwork.valueStack", stack);

        // initialize the VelocityManager.  required to use the UI tag libraries
        VelocityManager.init(servletContext);

        // initialize the MockTag for use
        mockTag = MockTag.getInstance();
        mockTag.reset();
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import com.mockobjects.servlet.MockJspWriter;
import com.mockobjects.servlet.MockServletConfig;
import com.mockobjects.servlet.MockServletContext;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.velocity.VelocityManager;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 */
public abstract class AbstractJspTest extends TestCase {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
    * Initialize Velocity for file based access.  This should be close enough to what's going on in the web based
    * environment.
    */
    static {
        Properties props = new Properties();
        props.setProperty("resource.loader", "file1,file2");

        // adding src/java to the Velocity load path
        props.setProperty("file1.resource.loader.description", "Velocity File Resource Loader");
        props.setProperty("file1.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        props.setProperty("file1.resource.loader.path", System.getProperty("webwork.webapp.path", new File("src/java").getAbsolutePath()));
        props.setProperty("file1.resource.loader.cache", "false");
        props.setProperty("file1.resource.loader.modificationCheckInterval", "2");

        // adding src/test to the Velocity load path
        props.setProperty("file2.resource.loader.description", "Velocity File Resource Loader");
        props.setProperty("file2.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        props.setProperty("file2.resource.loader.path", System.getProperty("webwork.webapp.path", new File("src/test").getAbsolutePath()));
        props.setProperty("file2.resource.loader.cache", "false");
        props.setProperty("file2.resource.loader.modificationCheckInterval", "2");

        props.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
        props.setProperty("class.resource.loader.class", "com.opensymphony.webwork.views.velocity.WebWorkResourceLoader");

        try {
            Velocity.init(props);
        } catch (Exception e) {
            e.printStackTrace(); //To change body of catch statement use Options | File Templates.
        }

        Mock mockServletContext = new Mock(ServletContext.class);
        mockServletContext.matchAndReturn("getRealPath", C.ANY_ARGS, new File("nosuchfile.properties").getAbsolutePath());

        ServletContext servletContext = (ServletContext) mockServletContext.proxy();

        // ensure that the VelocityManager has been initialized prior to any work going on!
        VelocityManager.init(servletContext);

        MockServletConfig config = new MockServletConfig();

        config.setServletContext(servletContext);
        config.setInitParameter("resource.loader", "file1,file2");

        // adding src/java to the Velocity load path
        config.setInitParameter("file1.resource.loader.description", "Velocity File Resource Loader");
        config.setInitParameter("file1.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        config.setInitParameter("file1.resource.loader.path", System.getProperty("webwork.webapp.path", new File("src/java").getAbsolutePath()));
        config.setInitParameter("file1.resource.loader.cache", "false");
        config.setInitParameter("file1.resource.loader.modificationCheckInterval", "2");

        // adding src/test to the Velocity load path
        config.setInitParameter("file2.resource.loader.description", "Velocity File Resource Loader");
        config.setInitParameter("file2.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        config.setInitParameter("file2.resource.loader.path", System.getProperty("webwork.webapp.path", new File("src/test").getAbsolutePath()));
        config.setInitParameter("file2.resource.loader.cache", "false");
        config.setInitParameter("file2.resource.loader.modificationCheckInterval", "2");

        config.setInitParameter("class.resource.loader.description", "Velocity Classpath Resource Loader");
        config.setInitParameter("class.resource.loader.class", "com.opensymphony.webwork.views.velocity.WebWorkResourceLoader");

        ServletActionContext.setServletConfig(config);
    }

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
    private HttpServletResponse response;

    //~ Constructors ///////////////////////////////////////////////////////////

    public AbstractJspTest() {
    }

    public AbstractJspTest(String s) {
        super(s);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
    * Constructs the action that we're going to test against.  For most UI tests, this default action should be enough.
    * However, simply override getAction to return a custom Action if you need something more sophisticated.
    * @return the Action to be added to the OgnlValueStack as part of the unit test
    */
    public Action getAction() {
        return new TestAction();
    }

    /**
    * Attempt to verify the contents of this.writer against the contents of the URL specified.  verify() performs a
    * trim on both ends
    * @param url the HTML snippet that we want to validate against
    * @throws Exception if the validation failed
    */
    public void verify(URL url) throws Exception {
        if (url == null) {
            fail("unable to verify a null URL");
        } else if (this.writer == null) {
            fail("AbstractJspWriter.writer not initialized.  Unable to verify");
        }

        StringBuffer buffer = new StringBuffer(128);
        InputStream in = url.openStream();
        byte[] buf = new byte[4096];
        int nbytes;

        while ((nbytes = in.read(buf)) > 0) {
            buffer.append(new String(buf, 0, nbytes));
        }

        in.close();

        /**
        * compare the trimmed values of each buffer and make sure they're equivalent.  however, let's make sure to
        * normalize the strings first to account for line termination differences between platforms.
        */
        String writerString = normalize(writer.getBuffer());
        String bufferString = normalize(buffer);

        if (!writerString.equals(bufferString)) {
            StringBuffer gripe = new StringBuffer((writerString.length() * 2) + 64);
            gripe.append("\r\n");
            gripe.append("expected: ").append(bufferString).append("\r\n");
            gripe.append("actual:   ").append(writerString).append("\r\n");
            gripe.append("file:     ").append(url.toExternalForm()).append("\r\n");
            fail(gripe.toString());
        }
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
        request.setAttribute("webwork.valueStack", stack);
        response = new WebWorkMockHttpServletResponse();
        request.setSession(new WebWorkMockHttpSession());
        ActionContext.getContext().setValueStack(stack);

        writer = new StringWriter();

        JspWriter jspWriter = new TestJspWriter(writer);

        pageContext = new WebWorkMockPageContext();
        pageContext.setRequest(request);
        pageContext.setResponse(response);
        pageContext.setJspWriter(jspWriter);

        session = new HashMap();
        ActionContext.getContext().setSession(session);

        Configuration.setConfiguration(null);
    }

    protected void tearDown() throws Exception {
        pageContext.verify();
        request.verify();
    }

    /**
    * normalizes a string so that strings generated on different platforms can be compared.  any group of one or more
    * space, tab, \r, and \n characters are converted to a single space character
    * @param obj the object to be normalized.  normalize will perform its operation on obj.toString().trim() ;
    * @return the normalized string
    */
    private String normalize(Object obj) {
        StringTokenizer st = new StringTokenizer(obj.toString().trim(), " \t\r\n");
        StringBuffer buffer = new StringBuffer(128);

        while (st.hasMoreTokens()) {
            buffer.append(st.nextToken());

            if (st.hasMoreTokens()) {
                buffer.append(" ");
            }
        }

        return buffer.toString();
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    /**
    * Unforunately, the MockJspWriter throws a NotImplementedException when any of the Writer methods are invoked and
    * as you might guess, Velocity uses the Writer methods.  I'velocityEngine subclassed the MockJspWriter for the time being so
    * that we can do testing on the results until MockJspWriter gets fully implemented.
    *
    * @todo replace this once MockJspWriter implements Writer correctly (i.e. doesn't throw NotImplementException)
    */
    public class TestJspWriter extends MockJspWriter {
        StringWriter writer;

        public TestJspWriter(StringWriter writer) {
            this.writer = writer;
        }

        public void write(String str) throws IOException {
            writer.write(str);
        }

        public void write(int c) throws IOException {
            writer.write(c);
        }

        public void write(char[] cbuf) throws IOException {
            writer.write(cbuf);
        }

        public void write(String str, int off, int len) throws IOException {
            writer.write(str, off, len);
        }
    }
}

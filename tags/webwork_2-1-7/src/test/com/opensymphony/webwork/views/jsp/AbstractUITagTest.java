/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;
import com.mockobjects.servlet.MockServletConfig;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.views.velocity.VelocityManager;
import com.opensymphony.xwork.ActionContext;
import org.apache.velocity.app.Velocity;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.StringTokenizer;


/**
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 * @version $Id$
 */
public abstract class AbstractUITagTest extends AbstractTagTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Attempt to verify the contents of this.writer against the contents of the URL specified.  verify() performs a
     * trim on both ends
     *
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
        Properties props = new Properties();
        props.setProperty("resource.loader", "file1,file2,class");

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
        VelocityManager.getInstance().init(servletContext);

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

        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ActionContext.setContext(null);
    }

    /**
     * normalizes a string so that strings generated on different platforms can be compared.  any group of one or more
     * space, tab, \r, and \n characters are converted to a single space character
     *
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
}

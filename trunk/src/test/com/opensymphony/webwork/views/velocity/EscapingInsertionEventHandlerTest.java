/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import com.opensymphony.webwork.views.util.TextUtil;

import junit.framework.TestCase;

import org.apache.commons.collections.ExtendedProperties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class EscapingInsertionEventHandlerTest extends TestCase {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static String instring;

    //~ Instance fields ////////////////////////////////////////////////////////

    EventCartridge cartridge;
    Map params;
    SimpleAction action;
    VelocityContext context;
    VelocityEngine velocityEngine;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testEvaluateUsingASimpleReferenceInsertionEventHandler() throws Exception {
        SimpleReferenceInsertionEventHandler eventHandler = new SimpleReferenceInsertionEventHandler();
        cartridge.addEventHandler(eventHandler);

        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, null, "$action.scalar");
        assertEquals(action.getScalar(), writer.toString());

        assertEquals("$action.scalar", eventHandler.reference);
        assertEquals(action.getScalar(), eventHandler.value);
    }

    public void testEvaluateUsingEmptyEventCartridge() throws Exception {
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, null, "$action.scalar");
        assertEquals(action.getScalar(), writer.toString());
    }

    public void testMergeObjectUsingEscapingInsertionEventHandler() throws Exception {
        cartridge.addEventHandler(new EscapingInsertionEventHandler());

        StringWriter writer = new StringWriter();
        instring = "$action";

        Template template = velocityEngine.getTemplate("template");
        template.merge(context, writer);
        assertEquals(action.toString(), writer.toString());
    }

    public void testMergeStringUsingEscapingInsertionEventHandler() throws Exception {
        cartridge.addEventHandler(new EscapingInsertionEventHandler());

        StringWriter writer = new StringWriter();
        instring = "$action.scalar";

        Template template = velocityEngine.getTemplate("template");
        template.merge(context, writer);
        assertEquals(TextUtil.escapeHTML(action.getScalar()), writer.toString());
    }

    public void testMergeUsingASimpleReferenceInsertionEventHandler() throws Exception {
        cartridge.addEventHandler(new SimpleReferenceInsertionEventHandler());

        StringWriter writer = new StringWriter();
        instring = "$action.scalar";

        Template template = velocityEngine.getTemplate("template");
        template.merge(context, writer);
        assertEquals(action.getScalar(), writer.toString());
    }

    protected void setUp() throws Exception {
        Properties props = new Properties();
        props.setProperty("resource.loader", "file2");

        // adding src/test to the Velocity load path
        props.setProperty("file2.resource.loader.description", "Velocity File Resource Loader");
        props.setProperty("file2.resource.loader.class", StringResourceLoader.class.getName());
        props.setProperty("file2.resource.loader.cache", "false");
        props.setProperty("file2.resource.loader.modificationCheckInterval", "2");

        action = new SimpleAction();
        params = new HashMap();
        params.put("action", action);

        context = new VelocityContext(params);
        cartridge = new EventCartridge();
        cartridge.attachToContext(context);

        velocityEngine = new VelocityEngine();
        velocityEngine.init(props);
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    public static class SimpleAction {
        private String scalar = "a scalar value with <&>\"' magic characters";

        public void setScalar(String scalar) {
            this.scalar = scalar;
        }

        public String getScalar() {
            return this.scalar;
        }
    }

    static public class StringResourceLoader extends ResourceLoader {
        public long getLastModified(Resource resource) {
            return System.currentTimeMillis();
        }

        public InputStream getResourceStream(String resource) throws ResourceNotFoundException {
            if (!"template".equals(resource)) {
                throw new ResourceNotFoundException("the only valid resource is page");
            }

            byte[] bytes = null;

            if (instring != null) {
                bytes = instring.getBytes();
            }

            return new ByteArrayInputStream(bytes);
        }

        public boolean isSourceModified(Resource resource) {
            return true;
        }

        public void init(ExtendedProperties extendedProperties) {
        }
    }

    class SimpleReferenceInsertionEventHandler implements ReferenceInsertionEventHandler {
        public Object value;
        public String reference;

        public Object referenceInsert(String reference, Object value) {
            this.reference = reference;
            this.value = value;

            return value;
        }
    }
}

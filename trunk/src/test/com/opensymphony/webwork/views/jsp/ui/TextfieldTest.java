/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractJspTest;

import junit.framework.Assert;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;


/**
 *
 *
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class TextfieldTest extends AbstractJspTest {
    //~ Constructors ///////////////////////////////////////////////////////////

    public TextfieldTest() {
    }

    public TextfieldTest(String s) {
        super(s);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testErrors() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + TextFieldTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        TextFieldTag tag = new TextFieldTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'foo'");
        tag.setValue("bar");

        testAction.addFieldError("foo", "bar error message");
        tag.doEndTag();

        verify(TextFieldTag.class.getResource("Textfield-2.txt"));
    }

    public void testSimple() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + TextFieldTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        TextFieldTag tag = new TextFieldTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'myname'");
        tag.setValue("foo");
        tag.setSize("'10'");

        tag.doEndTag();

        verify(TextFieldTag.class.getResource("Textfield-1.txt"));
    }
}

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
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class CheckboxTest extends AbstractJspTest {
    //~ Constructors ///////////////////////////////////////////////////////////

    public CheckboxTest() {
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testChecked() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + CheckboxTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        CheckboxTag tag = new CheckboxTag();
        tag.setPageContext(pageContext);
        tag.setLabel("mylabel");
        tag.setName("myname");
        tag.setValue("foo");

        int result = tag.doEndTag();

        verify(CheckboxTag.class.getResource("Checkbox-1.txt"));
    }

    public void testUnchecked() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + CheckboxTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        CheckboxTag tag = new CheckboxTag();
        tag.setPageContext(pageContext);
        tag.setLabel("mylabel");
        tag.setName("myname");

        int result = tag.doEndTag();

        verify(CheckboxTag.class.getResource("Checkbox-2.txt"));
    }
}

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
public class RadioTest extends AbstractJspTest {
    //~ Constructors ///////////////////////////////////////////////////////////

    public RadioTest() {
    }

    public RadioTest(String s) {
        super(s);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + RadioTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");
        testAction.setList(new String[][] {
                {"hello", "world"},
                {"foo", "bar"}
            });

        RadioTag tag = new RadioTag();
        tag.setPageContext(pageContext);
        tag.setLabel("mylabel");
        tag.setName("myname");
        tag.setValue("peek().list[1][1]");
        tag.setList("list");
        tag.setListKey("[0]");
        tag.setListValue("[1]");

        int result = tag.doEndTag();

        verify(RadioTag.class.getResource("Radio-1.txt"));
    }
}

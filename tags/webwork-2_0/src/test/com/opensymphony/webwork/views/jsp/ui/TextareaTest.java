/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;

import junit.framework.Assert;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class TextareaTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + TextareaTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        TextareaTag tag = new TextareaTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'myname'");
        tag.setValue("foo");
        tag.setRows("'30'");
        tag.setCols("'20'");
        tag.setDisabled("true");
        tag.setTabindex("'5'");
        tag.setOnchange("'alert(\\'goodbye\\');'");
        tag.setId("the_id");
        tag.setOnkeyup("'alert(\\'hello\\');'");
        tag.setReadonly("true");

        int result = tag.doEndTag();

        verify(TextareaTag.class.getResource("Textarea-1.txt"));
    }
}

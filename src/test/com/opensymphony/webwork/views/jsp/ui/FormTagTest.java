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
 * FormTagTest
 * @author Jason Carreira
 * Created Apr 3, 2003 10:28:58 AM
 */
public class FormTagTest extends AbstractJspTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testForm() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + FormTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        FormTag tag = new FormTag();
        tag.setPageContext(pageContext);
        tag.setName("myForm");
        tag.setMethod("POST");
        tag.setAction("myAction");
        tag.setEnctype("myEncType");

        tag.doStartTag();
        tag.doEndTag();

        verify(FormTag.class.getResource("Formtag-1.txt"));
    }
}

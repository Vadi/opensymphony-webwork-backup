/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.freemarker.tags.TextFieldModel;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import freemarker.template.TransformControl;

import java.util.HashMap;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class TextfieldTest extends AbstractUITagTest {

    public void testErrors() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        TextFieldTag tag = new TextFieldTag();
        tag.setPageContext(pageContext);
        tag.setId("myId");
        tag.setLabel("mylabel");
        tag.setName("foo");
        tag.setValue("bar");
        tag.setTitle("mytitle");

        testAction.addFieldError("foo", "bar error message");
        tag.doStartTag();
        tag.doEndTag();

        verify(TextFieldTag.class.getResource("Textfield-2.txt"));
    }

    public void testNoLabelJsp() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        TextFieldTag tag = new TextFieldTag();
        tag.setPageContext(pageContext);
        tag.setName("myname");
        tag.setValue("%{foo}");
        tag.setSize("10");
        tag.setOnblur("blahescape('somevalue');");

        tag.doStartTag();
        tag.doEndTag();

        verify(TextFieldTag.class.getResource("Textfield-3.txt"));
    }

    public void testNoLabelFtl() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        TextFieldModel model = new TextFieldModel(stack, request, response);
        HashMap params = new HashMap();
        params.put("name", "myname");
        params.put("value", "%{foo}");
        params.put("size", "10");
        params.put("onblur", "blahescape('somevalue');");
        TransformControl control = (TransformControl) model.getWriter(writer, params);
        control.onStart();
        control.afterBody();

        verify(TextFieldTag.class.getResource("Textfield-3.txt"));
    }

    public void testSimple() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        TextFieldTag tag = new TextFieldTag();
        tag.setPageContext(pageContext);
        tag.setLabel("mylabel");
        tag.setName("myname");
        tag.setValue("%{foo}");
        tag.setSize("10");

        tag.doStartTag();
        tag.doEndTag();

        verify(TextFieldTag.class.getResource("Textfield-1.txt"));
    }
}

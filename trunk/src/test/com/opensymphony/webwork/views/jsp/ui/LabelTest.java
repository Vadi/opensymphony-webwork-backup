/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class LabelTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        LabelTag tag = new LabelTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'myname'");
        tag.setValue("foo");

        int result = tag.doEndTag();

        verify(LabelTest.class.getResource("Label-1.txt"));
    }

    public void testSimpleWithLabelposition() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        LabelTag tag = new LabelTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'myname'");
        tag.setValue("foo");
        tag.setLabelposition("'top'");

        int result = tag.doEndTag();

        verify(LabelTest.class.getResource("Label-3.txt"));
    }

    public void testWithNoValue() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("baz");

        LabelTag tag = new LabelTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'foo'");
        tag.setFor("'for'");

        int result = tag.doEndTag();

        verify(LabelTest.class.getResource("Label-2.txt"));
    }
}

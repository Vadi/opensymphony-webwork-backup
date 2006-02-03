/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;

import java.util.Map;
import java.util.HashMap;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class LabelTest extends AbstractUITagTest {

    public void testSimple() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        LabelTag tag = new LabelTag();
        tag.setPageContext(pageContext);
        tag.setLabel("mylabel");
        tag.setName("myname");
        tag.setTitle("mytitle");
        tag.setValue("%{foo}");

        tag.doStartTag();
        tag.doEndTag();

        verify(LabelTest.class.getResource("Label-1.txt"));
    }

    public void testSimpleWithLabelposition() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        LabelTag tag = new LabelTag();
        tag.setPageContext(pageContext);
        tag.setLabel("mylabel");
        tag.setName("myname");
        tag.setValue("%{foo}");
        tag.setLabelPosition("top");

        tag.doStartTag();
        tag.doEndTag();

        verify(LabelTest.class.getResource("Label-3.txt"));
    }

    /**
     * Initialize a map of {@link com.opensymphony.webwork.views.jsp.AbstractUITagTest.PropertyHolder} for generic tag
     * property testing. Will be used when calling {@link #verifyGenericProperties(com.opensymphony.webwork.views.jsp.ui.AbstractUITag,
     * String, String[])} as properties to verify.<p/> This implementation extends testdata from AbstractUITag.
     *
     * @return A Map of PropertyHolders values bound to {@link com.opensymphony.webwork.views.jsp.AbstractUITagTest.PropertyHolder#getName()}
     *         as key.
     */
    protected Map initializedGenericTagTestProperties() {
        Map result = new HashMap();
        new PropertyHolder("title", "someTitle").addToMap(result);
        new PropertyHolder("cssClass", "cssClass1", "class=\"cssClass1\"").addToMap(result);
        new PropertyHolder("cssStyle", "cssStyle1", "style=\"cssStyle1\"").addToMap(result);
        new PropertyHolder("id", "someId").addToMap(result);
        new PropertyHolder("for", "someFor").addToMap(result);
        return result;
    }

    public void testWithNoValue() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("baz");

        LabelTag tag = new LabelTag();
        tag.setPageContext(pageContext);
        tag.setLabel("mylabel");
        tag.setName("foo");
        tag.setFor("for");

        tag.doStartTag();
        tag.doEndTag();

        verify(LabelTest.class.getResource("Label-2.txt"));
    }

    public void testGenericSimple() throws Exception {
        LabelTag tag = new LabelTag();
        verifyGenericProperties(tag, "simple", null);
    }

    public void testGenericXhtml() throws Exception {
        LabelTag tag = new LabelTag();
        verifyGenericProperties(tag, "xhtml", null);
    }

    public void testGenericAjax() throws Exception {
        LabelTag tag = new LabelTag();
        verifyGenericProperties(tag, "ajax", null);
    }

}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;

import java.util.Map;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class TextareaTest extends AbstractUITagTest {

    public void testSimple() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        TextareaTag tag = new TextareaTag();
        tag.setPageContext(pageContext);
        tag.setLabel("mylabel");
        tag.setName("myname");
        tag.setValue("%{foo}");
        tag.setRows("30");
        tag.setCols("20");
        tag.setTitle("mytitle");
        tag.setDisabled("true");
        tag.setTabindex("5");
        tag.setOnchange("alert('goodbye');");
        tag.setOnclick("alert('onclick');");
        tag.setId("the_id");
        tag.setOnkeyup("alert('hello');");
        tag.setReadonly("true");

        tag.doStartTag();
        tag.doEndTag();

        verify(TextareaTag.class.getResource("Textarea-1.txt"));
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
        Map result = super.initializedGenericTagTestProperties();
        new PropertyHolder("cols", "10").addToMap(result);
        new PropertyHolder("rows", "11").addToMap(result);
        new PropertyHolder("readonly", "true", "readonly=\"readonly\"").addToMap(result);
        new PropertyHolder("wrap", "soft").addToMap(result);
        return result;
    }

    public void testGenericSimple() throws Exception {
        TextareaTag tag = new TextareaTag();
        verifyGenericProperties(tag, "simple", new String[] {"value"});
    }

    public void testGenericXhtml() throws Exception {
        TextareaTag tag = new TextareaTag();
        verifyGenericProperties(tag, "xhtml", new String[] {"value"});
    }

    public void testGenericAjax() throws Exception {
        TextareaTag tag = new TextareaTag();
        verifyGenericProperties(tag, "ajax", new String[] {"value"});
    }

}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;

import java.util.HashMap;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class RadioTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testMapChecked() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        HashMap map = new HashMap();
        map.put("1", "One");
        map.put("2", "Two");
        testAction.setMap(map);

        RadioTag tag = new RadioTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'myname'");
        tag.setValue("\"1\"");
        tag.setList("map");
        tag.setListKey("key");
        tag.setListValue("value");

        int result = tag.doEndTag();

        verify(RadioTag.class.getResource("Radio-2.txt"));
    }

    public void testSimple() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");
        testAction.setList(new String[][]{
            {"hello", "world"},
            {"foo", "bar"}
        });

        RadioTag tag = new RadioTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'myname'");
        tag.setValue("");
        tag.setList("list");
        tag.setListKey("top[0]");
        tag.setListValue("top[1]");

        int result = tag.doEndTag();

        verify(RadioTag.class.getResource("Radio-1.txt"));
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;

import java.util.ArrayList;
import java.util.Collection;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class SelectTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testMultiple() throws Exception {
        TestAction testAction = (TestAction) action;
        Collection collection = new ArrayList(2);
        collection.add("hello");
        collection.add("foo");
        testAction.setCollection(collection);
        testAction.setList(new String[][]{
            {"hello", "world"},
            {"foo", "bar"},
            {"cat", "dog"}
        });

        SelectTag tag = new SelectTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'collection'");
        tag.setList("list");
        tag.setListKey("top[0]");
        tag.setListValue("top[1]");
        tag.setMultiple("true");

        int result = tag.doEndTag();

        verify(SelectTag.class.getResource("Select-2.txt"));
    }

    public void testSimple() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("hello");
        testAction.setList(new String[][]{
            {"hello", "world"},
            {"foo", "bar"}
        });

        SelectTag tag = new SelectTag();
        tag.setPageContext(pageContext);
        tag.setEmptyOption("true");
        tag.setLabel("'mylabel'");
        tag.setName("'foo'");
        tag.setList("list");
        tag.setListKey("top[0]");
        tag.setListValue("top[1]");

        // header stuff
        tag.setHeaderKey("'headerKey'");
        tag.setHeaderValue("'headerValue'");

        // empty option
        tag.setEmptyOption("true");

        int result = tag.doEndTag();

        verify(SelectTag.class.getResource("Select-1.txt"));
    }
}

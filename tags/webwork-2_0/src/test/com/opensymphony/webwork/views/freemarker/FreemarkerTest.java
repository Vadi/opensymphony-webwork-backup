/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 1/10/2003
 *
 */
package com.opensymphony.webwork.views.freemarker;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import freemarker.template.SimpleHash;
import freemarker.template.SimpleSequence;

import junit.framework.TestCase;

import java.util.Collection;
import java.util.List;


/**
 * @author CameronBraid
 *
 */
public class FreemarkerTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    TestAction testAction = null;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     *
     */
    public FreemarkerTest(String name) {
        super(name);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSelectHelper() {
        FreemarkerUtil wwUtil = new FreemarkerUtil();

        List selectList = null;

        selectList = wwUtil.makeSelectList("stringList", null, null);
        assertEquals("one", ((ListEntry) selectList.get(0)).getKey());
        assertEquals("one", ((ListEntry) selectList.get(0)).getValue());

        selectList = wwUtil.makeSelectList("beanList", "name", "value");
        assertEquals("one", ((ListEntry) selectList.get(0)).getKey());
        assertEquals("1", ((ListEntry) selectList.get(0)).getValue());
    }

    public void testValueStackMode() throws Exception {
        ValueStackModel model = new ValueStackModel(new SimpleHash());

        SimpleSequence stringList = null;

        stringList = (SimpleSequence) model.get("stringList");
        assertEquals("one", stringList.get(0).toString());

        assertEquals("one", model.get("stringList[0]").toString());
        assertEquals("one", model.get("beanList[0].name").toString());
    }

    protected void setUp() throws Exception {
        super.setUp();

        OgnlValueStack stack = new OgnlValueStack();
        ActionContext.setContext(new ActionContext(stack.getContext()));

        testAction = new TestAction();
        ActionContext.getContext().getValueStack().push(testAction);
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 1/10/2003
 *
 */
package com.opensymphony.webwork.views.freemarker;

import java.util.List;

import junit.framework.TestCase;

import com.opensymphony.webwork.util.FreemarkerWebWorkUtil;
import com.opensymphony.webwork.util.ListEntry;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.SimpleSequence;


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
        FreemarkerWebWorkUtil wwUtil = new FreemarkerWebWorkUtil(ActionContext.getContext().getValueStack(), null, null);

        List selectList = null;

        selectList = wwUtil.makeSelectList("ignored", "stringList", null, null);
        assertEquals("one", ((ListEntry) selectList.get(0)).getKey());
        assertEquals("one", ((ListEntry) selectList.get(0)).getValue());

        selectList = wwUtil.makeSelectList("ignored", "beanList", "name", "value");
        assertEquals("one", ((ListEntry) selectList.get(0)).getKey());
        assertEquals("1", ((ListEntry) selectList.get(0)).getValue());
    }

    public void testValueStackMode() throws Exception {
        ValueStackModel model = new ValueStackModel(ActionContext.getContext().getValueStack(), new SimpleHash(), ObjectWrapper.DEFAULT_WRAPPER);

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

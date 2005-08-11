/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.webwork.views.jsp.ui.DivTag;


/**
 * @author Ian Roughley<a href="mailto:ian@fdar.com">&lt;ian@fdar.com&gt;</a>
 * @version $Id$
 */
public class DivTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////


    public void testSimple() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        DivTag tag = new DivTag();
        tag.setPageContext(pageContext);

        tag.setId("mylabel");
        tag.setTheme("ajax");
        tag.setHref("a");
        tag.setLoadingText("b");
        tag.setErrorText("c");
        tag.setShowErrorTransportText("true");
        tag.setDelay("4000");
        tag.setUpdateFreq("1000");
        tag.setListenTopics("g");
        tag.setAfterLoading("h");

        tag.doStartTag();
        tag.doEndTag();

        verify(DivTest.class.getResource("div-1.txt"));
    }

}

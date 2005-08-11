/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.webwork.views.jsp.ui.HrefTag;


/**
 * @author Ian Roughley<a href="mailto:ian@fdar.com">&lt;ian@fdar.com&gt;</a>
 * @version $Id$
 */
public class HrefTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////


    public void testSimple() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        HrefTag tag = new HrefTag();
        tag.setPageContext(pageContext);

        tag.setId("mylink");
        tag.setTheme("ajax");
        tag.setHref("a");
        tag.setErrorText("c");
        tag.setShowErrorTransportText("true");
        tag.setNotifyTopics("g");
        tag.setAfterLoading("h");

        tag.doStartTag();
        tag.doEndTag();

        verify(HrefTest.class.getResource("href-1.txt"));
    }

}

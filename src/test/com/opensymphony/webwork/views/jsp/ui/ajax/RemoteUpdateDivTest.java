/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;

import javax.servlet.jsp.tagext.TagSupport;


/**
 * @author Ian Roughley<a href="mailto:ian@fdar.com">&lt;ian@fdar.com&gt;</a>
 * @version $Id$
 */
public class RemoteUpdateDivTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////


    public void testSimple() throws Exception {
        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        RemoteUpdateDivTag tag = new RemoteUpdateDivTag();
        tag.setPageContext(pageContext);

        tag.setId("mylabel");
        tag.setTheme("ajax");
        tag.addParameter( "href", "a" );
        tag.addParameter( "loadingText", "b" );
        tag.addParameter( "errorText", "c" );
        tag.addParameter( "showErrorTransportText", "d" );
        tag.addParameter( "delay", "e" );
        tag.addParameter( "updateFreq", "f" );
        tag.addParameter( "listenTopics", "g" );
        tag.addParameter( "afterLoading", "h" );

        assertEquals( tag.doStartTag(), TagSupport.EVAL_BODY_INCLUDE );
        assertEquals( tag.doEndTag(), TagSupport.EVAL_PAGE );

        verify(RemoteUpdateDivTest.class.getResource("remotediv-1.txt"));
    }

}

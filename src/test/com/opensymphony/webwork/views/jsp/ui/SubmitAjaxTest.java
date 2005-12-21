/**
 * Copyright:	Copyright (c) From Down & Around, Inc.
 */

package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.views.jsp.AbstractUITagTest;

/**
 * @author Ian Roughley
 * @version $Id$
 */
public class SubmitAjaxTest extends AbstractUITagTest {


    public void testSimple() throws Exception {
        SubmitTag tag = new SubmitTag();
        tag.setPageContext(pageContext);

        tag.setId("mylink");
        tag.setValue("submit");
        tag.setTheme("ajax");
        tag.setResultDivId("formId");
        tag.setOnLoadJS("alert('form submitted');");
        tag.setListenTopics("a");
        tag.setNotifyTopics("b");

        tag.doStartTag();
        tag.doEndTag();

        verify(AnchorTest.class.getResource("submit-ajax-1.txt"));
    }

}
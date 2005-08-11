/**
 * Copyright:	Copyright (c) From Down & Around, Inc.
 */

package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.webwork.views.jsp.ui.SubmitTag;
import com.opensymphony.webwork.views.jsp.ui.RemoteLinkTest;

/**
 * @author Ian Roughley
 * @version $Id$
 */
public class SubmitAjaxTest extends AbstractUITagTest {

    //~ Methods ////////////////////////////////////////////////////////////////

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

        verify(RemoteLinkTest.class.getResource("submit-1.txt"));
    }

}
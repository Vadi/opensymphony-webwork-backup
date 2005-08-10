/**
 * Copyright:	Copyright (c) From Down & Around, Inc.
 */

package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.webwork.views.jsp.ParamTag;
import com.opensymphony.webwork.views.jsp.WebWorkMockBodyContent;
import com.opensymphony.webwork.views.jsp.WebWorkMockJspWriter;
import com.opensymphony.webwork.views.jsp.ui.SubmitTag;
import com.opensymphony.webwork.TestAction;

/**
 * @author Ian Roughley
 * @version $Id$
 */
public class SubmitTest extends AbstractUITagTest {

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testFindAncestor() throws Exception {
        SubmitTag tag1 = new SubmitTag();
        tag1.setPageContext(pageContext);

        SubmitTag tag2 = new SubmitTag();
        tag2.setPageContext(pageContext);

        ParamTag resultLoc = new ParamTag();
        resultLoc.setPageContext(pageContext);
        resultLoc.setParent(tag2);

        SubmitTag tag3 = new SubmitTag();
        tag3.setPageContext(pageContext);

        assertSame( tag2, ParamTag.findAncestorWithClass(resultLoc, ParamTag.Parametric.class) );
    }

    public void testSimple_div() throws Exception {
        SubmitTag tag = new SubmitTag();
        tag.setPageContext(pageContext);

        tag.setId("mylink");
        tag.setValue("submit");
        tag.setTheme("ajax");

        ParamTag resultLoc = new ParamTag();
        resultLoc.setPageContext(pageContext);
        resultLoc.setName("resultDivId");
        WebWorkMockBodyContent webWorkMockBodyContent = new WebWorkMockBodyContent(new WebWorkMockJspWriter(writer));
        webWorkMockBodyContent.setString("formId");
        resultLoc.setBodyContent(webWorkMockBodyContent);
        resultLoc.setParent(tag);

        tag.doStartTag();
        tag.doEndTag();

        verify(RemoteLinkTest.class.getResource("submit-1.txt"));
    }

     public void testSimple_onLoad() throws Exception {
        SubmitTag tag = new SubmitTag();
        tag.setPageContext(pageContext);

        tag.setId("mylink");
        tag.setValue("submit");
        tag.setTheme("ajax");

        ParamTag resultLoc = new ParamTag();
        resultLoc.setPageContext(pageContext);
        resultLoc.setName("onLoadJS");
        WebWorkMockBodyContent webWorkMockBodyContent = new WebWorkMockBodyContent(new WebWorkMockJspWriter(writer));
        webWorkMockBodyContent.setString("alert('form submitted');");
        resultLoc.setBodyContent(webWorkMockBodyContent);
        resultLoc.setParent(tag);

        tag.doStartTag();
        tag.doEndTag();

        verify(RemoteLinkTest.class.getResource("submit-1.txt"));
    }

}
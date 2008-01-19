/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.views.jsp.ui.AnchorTag;
import com.opensymphony.webwork.views.jsp.ui.WebWorkBodyContent;

import javax.servlet.jsp.JspWriter;
import java.io.StringWriter;


/**
 * @author Mike Porter
 * @author Claus Ibsen
 * @version $Date$ $Id$
 */
public class AnchorTagTest extends AbstractUITagTest {
    private StringWriter writer = new StringWriter();
    private AnchorTag tag;

    public void testActionURL() throws Exception {
		tag.setHref("TestAction.action");
		tag.doStartTag();
		tag.doEndTag();
		assertTrue(writer.toString().indexOf("href=\"TestAction.action\"") > -1);
	}
    
    public void testNoNewLineAtEnd() throws Exception {
		tag.setHref("TestAction.action");
		tag.doStartTag();
		tag.doEndTag();
		assertFalse(writer.toString().endsWith("\n"));
	}

    public void testAccessKey() throws Exception {
		tag.setHref("TestAction.action");
		tag.setAccesskey("T");
		tag.doStartTag();
		tag.doEndTag();
		assertTrue(writer.toString().indexOf("accesskey=\"T\"") > -1);
		assertFalse(writer.toString().endsWith("\n"));
	}

	public void testAddParameters() throws Exception {
		tag.setHref("/TestAction.action");
		String bodyText = "<img src=\"#\"/>";
		WebWorkBodyContent bodyContent = new WebWorkBodyContent(null);
		bodyContent.print(bodyText);
		tag.setBodyContent(bodyContent);

		tag.doStartTag();
		tag.doEndTag();
	}
	

	public void testId() throws Exception {
		tag.setId("home&improvements");
		tag.doStartTag();
		tag.doEndTag();
		assertEquals("<a id=\"home&amp;improvements\"></a>", writer.toString());
		assertFalse(writer.toString().endsWith("\n"));
	}

	public void testTitle() throws Exception {
		tag.setHref("home.ftl");
		tag.setTitle("home & improvements");
		tag.doStartTag();
		tag.doEndTag();
		assertEquals(
				"<a href=\"home.ftl\" title=\"home &amp; improvements\"></a>",
				writer.toString());
		assertFalse(writer.toString().endsWith("\n"));
	}

	public void testOnMouseOver() throws Exception {
		tag.setHref("TestAction.action");
		tag.setOnmouseover("over");
		tag.doStartTag();
		tag.doEndTag();
		assertTrue(writer.toString().indexOf("onmouseover=\"over\"") > -1);
		assertFalse(writer.toString().endsWith("\n"));
	}

	public void testOnMouseOverAndFocus() throws Exception {
		tag.setHref("TestAction.action");
		tag.setOnmouseover("overme");
		tag.setOnfocus("focusme");
		tag.doStartTag();
		tag.doEndTag();
		assertTrue(writer.toString().indexOf("onmouseover=\"overme\"") > -1);
		assertTrue(writer.toString().indexOf("onfocus=\"focusme\"") > -1);
		assertFalse(writer.toString().endsWith("\n"));
	}

	public void testWithContent() throws Exception {
		tag.setHref("TestAction.action");
		tag.doStartTag();
		writer.write("Home");
		tag.doEndTag();
		assertEquals("<a href=\"TestAction.action\">Home</a>", writer
				.toString());
		assertFalse(writer.toString().endsWith("\n"));
	}

	



    protected void setUp() throws Exception {
        super.setUp();

        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(80);

        tag = new AnchorTag();
        tag.setPageContext(pageContext);
        JspWriter jspWriter = new WebWorkMockJspWriter(writer);
        pageContext.setJspWriter(jspWriter);
    }

}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;
import java.io.StringWriter;
import java.util.Map;


/**
 * ParamTagTest
 *
 * @author Jason Carreira
 *         Date: Mar 15, 2004 9:48:06 PM
 */
public class ParamTagTest extends AbstractTagTest {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final String NAME_EXP = "'name'";
    private static final String NAME_VAL = "name";
    private static final String VAL_EXP = "'value'";
    private static final String VAL_VAL = "value";

    //~ Instance fields ////////////////////////////////////////////////////////

    private ParamTag tag;
    private ParametereizedBodyTagSupport parentTag;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testParamSetWithNameAndBodyContent() throws JspException {
        StringWriter writer = new StringWriter();
        tag.setName(NAME_EXP);

        WebWorkMockBodyContent webWorkMockBodyContent = new WebWorkMockBodyContent(new WebWorkMockJspWriter(writer));
        webWorkMockBodyContent.setString(VAL_VAL);
        tag.setBodyContent(webWorkMockBodyContent);
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());

        Map params = parentTag.getParameters();
        assertEquals(1, params.size());
        assertTrue(params.containsKey(NAME_VAL));
        assertEquals(VAL_VAL, params.get(NAME_VAL));
    }

    public void testParamSetWithNameAndValueAttributes() throws JspException {
        tag.setName(NAME_EXP);
        tag.setValue(VAL_EXP);
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());

        Map params = parentTag.getParameters();
        assertEquals(1, params.size());
        assertTrue(params.containsKey(NAME_VAL));
        assertEquals(VAL_VAL, params.get(NAME_VAL));
    }

    public void testParamSetWithValueAttributeWhenBothValueAttrAndBodyAreSet() throws JspException {
        StringWriter writer = new StringWriter();
        tag.setName(NAME_EXP);
        tag.setValue(VAL_EXP);

        WebWorkMockBodyContent webWorkMockBodyContent = new WebWorkMockBodyContent(new WebWorkMockJspWriter(writer));
        webWorkMockBodyContent.setString("Will not be used");
        tag.setBodyContent(webWorkMockBodyContent);
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());

        Map params = parentTag.getParameters();
        assertEquals(1, params.size());
        assertTrue(params.containsKey(NAME_VAL));
        assertEquals(VAL_VAL, params.get(NAME_VAL));
    }

    protected void setUp() throws Exception {
        super.setUp();
        tag = new ParamTag();
        tag.setPageContext(pageContext);
        parentTag = new ParametereizedBodyTagSupport();
        tag.setParent(parentTag);
    }
}

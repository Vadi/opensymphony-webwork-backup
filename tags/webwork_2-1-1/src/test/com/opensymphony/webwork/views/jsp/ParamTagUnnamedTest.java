/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import java.io.StringWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;


/**
 * ParamTagTest
 *
 * @author Jason Carreira
 *         Date: Mar 15, 2004 9:48:06 PM
 */
public class ParamTagUnnamedTest extends AbstractTagTest {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final String NAME_EXP = "'name'";
    private static final String NAME_VAL = "name";
    private static final String VAL_EXP = "'value'";
    private static final String VAL_VAL = "value";

    //~ Instance fields ////////////////////////////////////////////////////////

    private ParamTag tag;
    private UnnamedParametricBodyTag parentTag;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testParamSetWithBodyContent() throws JspException {
        StringWriter writer = new StringWriter();
        WebWorkMockBodyContent webWorkMockBodyContent = new WebWorkMockBodyContent(new WebWorkMockJspWriter(writer));
        webWorkMockBodyContent.setString(VAL_VAL);
        tag.setBodyContent(webWorkMockBodyContent);
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());

        List params = parentTag.getValuesList();
        assertEquals(1, params.size());
        assertEquals(VAL_VAL, params.get(0));
    }

    public void testParamSetWithValueAttribute() throws JspException {
        tag.setValue(VAL_EXP);
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());

        List params = parentTag.getValuesList();
        assertEquals(1, params.size());
        assertEquals(VAL_VAL, params.get(0));
    }

    public void testParamSetWithValueAttributeWhenBothValueAttrAndBodyAreSet() throws JspException {
        StringWriter writer = new StringWriter();
        tag.setValue(VAL_EXP);

        WebWorkMockBodyContent webWorkMockBodyContent = new WebWorkMockBodyContent(new WebWorkMockJspWriter(writer));
        webWorkMockBodyContent.setString("Will not be used");
        tag.setBodyContent(webWorkMockBodyContent);
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());

        List params = parentTag.getValuesList();
        assertEquals(1, params.size());
        assertEquals(VAL_VAL, params.get(0));
    }

    protected void setUp() throws Exception {
        super.setUp();
        tag = new ParamTag();
        tag.setPageContext(pageContext);
        parentTag = new UnnamedParametricBodyTag();
        tag.setParent(parentTag);
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    private class UnnamedParametricBodyTag extends WebWorkBodyTagSupport implements ParamTag.UnnamedParametric {
        private List values;

        public Map getParameters() {
            return null;
        }

        public List getValuesList() {
            return values;
        }

        public void addParameter(String aName, Object aValue) {
            addParameter(aValue);
        }

        public void addParameter(Object aValue) {
            if (aValue == null) {
                return;
            }

            if (values == null) {
                values = new ArrayList();
            }

            values.add(aValue);
        }
    }
}

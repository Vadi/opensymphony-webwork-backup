/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractTagTest;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * TextTagTest
 * User: jcarreira
 * Created: Oct 17, 2003 2:15:36 PM
 */
public class TextTagTest extends AbstractTagTest {
    //~ Instance fields ////////////////////////////////////////////////////////

    private String fooValue = "com.opensymphony.webwork.views.jsp.ui.TextTagTest.fooValue";
    private TextTag tag;

    //~ Methods ////////////////////////////////////////////////////////////////

    public Action getAction() {
        TestAction action = new TestAction();
        action.setFoo(fooValue);

        return action;
    }

    public void testExpressionsEvaluated() throws Exception {
        String key = "'expressionKey'";
        String value = "Foo is " + fooValue;
        tag.setName(key);
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());
        assertEquals(value, writer.toString());
    }

    public void testMessageFormatWorks() throws Exception {
        String key = "'messageFormatKey'";
        String pattern = "Params are {0} {1} {2}";
        Object param1 = new Integer(12);
        Object param2 = new Date();
        Object param3 = "StringVal";
        List params = new ArrayList();
        params.add(param1);
        params.add(param2);
        params.add(param3);

        String expected = MessageFormat.format(pattern, params.toArray());
        tag.setName(key);
        tag.addParameter(param1);
        tag.addParameter(param2);
        tag.addParameter(param3);
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());
        assertEquals(expected, writer.toString());
    }

    public void testSimpleKeyValueWorks() throws JspException {
        String key = "'simpleKey'";
        String value = "Simple Message";
        tag.setName(key);
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());
        assertEquals(value, writer.toString());
    }

    public void testWithNoMessageAndBodyIsNotEmptyBodyIsReturned() throws Exception {
        final String key = "'key.does.not.exist'";
        final String bodyText = "body text";
        tag.setName(key);

        WebWorkBodyContent bodyContent = new WebWorkBodyContent(null);
        bodyContent.print(bodyText);
        tag.setBodyContent(bodyContent);
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());
        assertEquals(bodyText, writer.toString());
    }

    public void testWithNoMessageAndNoDefaultKeyReturned() throws JspException {
        final String key = "key.does.not.exist";
        tag.setName("'" + key + "'");
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());
        assertEquals(key, writer.toString());
    }

    /**
     * todo remove ActionContext set after LocalizedTextUtil is fixed to not use ThreadLocal
     *
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        tag = new TextTag();
        tag.setPageContext(pageContext);
        ActionContext.setContext(new ActionContext(stack.getContext()));
    }

    protected void tearDown() throws Exception {
        OgnlValueStack valueStack = new OgnlValueStack();
        ActionContext.setContext(new ActionContext(valueStack.getContext()));
    }
}

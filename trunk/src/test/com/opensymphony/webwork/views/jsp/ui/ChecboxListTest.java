package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.webwork.TestAction;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import junit.framework.Assert;

public class ChecboxListTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + SelectTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");
        testAction.setList(new String[][] {
                {"hello", "world"},
                {"foo", "bar"}
            });
        // TODO: write a damn test
//
//        ChecboxList tag = new ChecboxList();
//        tag.setPageContext(pageContext);
//        tag.setEmptyOption("true");
//        tag.setLabel("'mylabel'");
//        tag.setName("'myname'");
//        tag.setValue("'foo'");
//        tag.setList("list");
//        tag.setListKey("that[0]");
//        tag.setListValue("that[1]");
//
//        int result = tag.doEndTag();
//
//        verify(SelectTag.class.getResource("Select-1.txt"));
    }
}
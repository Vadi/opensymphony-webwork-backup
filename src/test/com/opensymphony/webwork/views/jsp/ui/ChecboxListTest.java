package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.webwork.TestAction;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import junit.framework.Assert;

import java.util.Collection;
import java.util.ArrayList;

public class ChecboxListTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////

  public void testSimple() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + CheckboxListTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("hello");
        testAction.setList(new String[][] {
                {"hello", "world"},
                {"foo", "bar"}
            });

        CheckboxListTag tag = new CheckboxListTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'foo'");
        tag.setList("list");
        tag.setListKey("top[0]");
        tag.setListValue("top[1]");

        int result = tag.doEndTag();

        verify(CheckboxListTag.class.getResource("CheckboxList-1.txt"));
    }

    public void testMultiple() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + CheckboxListTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        Collection collection = new ArrayList(2);
        collection.add("hello");
        collection.add("foo");
        testAction.setCollection(collection);
        testAction.setList(new String[][] {
                {"hello", "world"},
                {"foo", "bar"},
                {"cat", "dog"}
            });

        CheckboxListTag tag = new CheckboxListTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'collection'");
        tag.setList("list");
        tag.setListKey("top[0]");
        tag.setListValue("top[1]");

        int result = tag.doEndTag();

        verify(CheckboxListTag.class.getResource("CheckboxList-2.txt"));
    }
}
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.webwork.TestAction;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import junit.framework.Assert;

import java.util.Collection;
import java.util.ArrayList;

public class ComboBoxTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + ComboBoxTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("hello");
        ArrayList collection = new ArrayList();
        collection.add("foo");
        collection.add("bar");
        collection.add("baz");
        testAction.setCollection(collection);

        ComboBoxTag tag = new ComboBoxTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'foo'");
        tag.setList("collection");

        int result = tag.doEndTag();

        verify(ComboBoxTag.class.getResource("ComboBox-1.txt"));
    }
}
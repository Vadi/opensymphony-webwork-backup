package com.opensymphony.webwork.views.jsp.ui;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import junit.framework.Assert;
import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.views.jsp.AbstractJspTest;

/**
 * User: plightbo
 * Date: Oct 16, 2003
 * Time: 10:52:57 PM
 */
public class SubmitTest extends AbstractJspTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + SubmitTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        SubmitTag tag = new SubmitTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setAlign("'left'");
        tag.setName("'myname'");
        tag.setValue("foo");

        tag.doEndTag();

        verify(TextFieldTag.class.getResource("Submit-1.txt"));
    }

    public void testDefaultValues() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + SubmitTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        SubmitTag tag = new SubmitTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'myname'");

        tag.doEndTag();

        verify(TextFieldTag.class.getResource("Submit-2.txt"));
    }
}
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.views.jsp.AbstractJspTest;
import com.opensymphony.webwork.TestAction;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import junit.framework.Assert;

/**
 * User: plightbo
 * Date: Oct 16, 2003
 * Time: 10:49:54 PM
 */
public class HiddenTest extends AbstractJspTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + HiddenTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        HiddenTag tag = new HiddenTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'myname'");
        tag.setValue("foo");

        tag.doEndTag();

        verify(TextFieldTag.class.getResource("Hidden-1.txt"));
    }
}
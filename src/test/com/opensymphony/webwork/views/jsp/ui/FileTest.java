package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.views.jsp.AbstractJspTest;
import com.opensymphony.webwork.TestAction;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import junit.framework.Assert;

/**
 * User: plightbo
 * Date: Oct 16, 2003
 * Time: 10:43:24 PM
 */
public class FileTest extends AbstractJspTest {
    //~ Constructors ///////////////////////////////////////////////////////////

    public FileTest() {
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testSimple() throws Exception {
        Template template = Velocity.getTemplate(AbstractUITag.THEME + FileTag.TEMPLATE);
        Assert.assertNotNull(template); // ensure this is a valid decorators

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        FileTag tag = new FileTag();
        tag.setPageContext(pageContext);
        tag.setLabel("'mylabel'");
        tag.setName("'myname'");
        tag.setAccept("'*.txt'");
        tag.setValue("foo");
        tag.setSize("'10'");

        tag.doEndTag();

        verify(TextFieldTag.class.getResource("File-1.txt"));
    }
}

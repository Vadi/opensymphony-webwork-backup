/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.TestConfigurationProvider;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.config.ConfigurationManager;


/**
 * FormTagTest
 *
 * @author Jason Carreira
 *         Created Apr 3, 2003 10:28:58 AM
 */
public class FormTagTest extends AbstractUITagTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testForm() throws Exception {
        request.setupGetServletPath("/testAction");

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        FormTag tag = new FormTag();
        tag.setPageContext(pageContext);
        tag.setName("'myForm'");
        tag.setMethod("'POST'");
        tag.setAction("'myAction'");
        tag.setEnctype("'myEncType'");

        tag.doStartTag();
        tag.doEndTag();

        verify(FormTag.class.getResource("Formtag-1.txt"));
    }

    /**
     * Testing that this: <p>
     * &lt;ww:form name=&quot;'myForm'&quot; namespace=&quot;'/testNamespace'&quot; action=&quot;'testNamespaceAction'&quot; method=&quot;'POST'&quot;&gt;
     * <p/>
     * doesn't create an action of &quot;/testNamespace/testNamespaceAction.action&quot; when the &quot;webwork.action.extension&quot;
     * config property is set to &quot;jspa&quot;.
     */
    public void testFormTagWithDifferentActionExtension() throws Exception {
        request.setupGetServletPath("/testNamespace/testNamespaceAction");
        Configuration.set("webwork.action.extension", "jspa");

        FormTag tag = new FormTag();
        tag.setPageContext(pageContext);
        tag.setNamespace("'/testNamespace'");
        tag.setAction("'testNamespaceAction'");
        tag.setMethod("'POST'");
        tag.setName("'myForm'");

        tag.doStartTag();
        tag.doEndTag();

        verify(FormTag.class.getResource("Formtag-5.txt"));

        // set it back to the default
        Configuration.set("webwork.action.extension", "action");
    }

    /**
     * Testing that this: <p>
     * &lt;ww:form name=&quot;'myForm'&quot; action=&quot;'/testNamespace/testNamespaceAction.jspa'&quot; method=&quot;'POST'&quot;&gt;
     * <p/>
     * doesn't create an action of &quot;/testNamespace/testNamespaceAction.action&quot;
     */
    public void testFormTagWithDifferentActionExtensionHardcoded() throws Exception {
        request.setupGetServletPath("/testNamespace/testNamespaceAction");

        FormTag tag = new FormTag();
        tag.setPageContext(pageContext);
        tag.setAction("'/testNamespace/testNamespaceAction.jspa'");
        tag.setMethod("'POST'");
        tag.setName("'myForm'");

        tag.doStartTag();
        tag.doEndTag();

        verify(FormTag.class.getResource("Formtag-5.txt"));
    }

    public void testFormWithNamespaceDefaulting() throws Exception {
        request.setupGetServletPath("/testNamespace/testNamespaceAction");

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        FormTag tag = new FormTag();
        tag.setPageContext(pageContext);
        tag.setName("'myForm'");
        tag.setMethod("'POST'");
        tag.setAction("'testNamespaceAction'");

        tag.doStartTag();
        tag.doEndTag();

        verify(FormTag.class.getResource("Formtag-3.txt"));
    }

    public void testFormWithNoAction() throws Exception {
        FormTag tag = new FormTag();
        tag.setPageContext(pageContext);
        tag.doStartTag();
        tag.doEndTag();

        verify(FormTag.class.getResource("Formtag-4.txt"));
    }

    protected void setUp() throws Exception {
        super.setUp();
        ConfigurationManager.clearConfigurationProviders();
        ConfigurationManager.addConfigurationProvider(new TestConfigurationProvider());
        ActionContext.getContext().setValueStack(stack);
    }
}

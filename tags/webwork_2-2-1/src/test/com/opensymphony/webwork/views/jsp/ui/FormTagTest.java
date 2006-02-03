/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.TestConfigurationProvider;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.webwork.views.jsp.ActionTag;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.config.ConfigurationManager;


/**
 * FormTagTest
 *
 * @author Jason Carreira
 *         Created Apr 3, 2003 10:28:58 AM
 *         
 * @version $Date$ $Id$
 */
public class FormTagTest extends AbstractUITagTest {

    public void testForm() throws Exception {
        request.setupGetServletPath("/testAction");

        TestAction testAction = (TestAction) action;
        testAction.setFoo("bar");

        FormTag tag = new FormTag();
        tag.setPageContext(pageContext);
        tag.setName("myForm");
        tag.setMethod("POST");
        tag.setAction("myAction");
        tag.setEnctype("myEncType");
        tag.setTitle("mytitle");
        tag.setOnsubmit("submitMe()");

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
        String oldConfiguration = (String) Configuration.get(WebWorkConstants.WEBWORK_ACTION_EXTENSION);
        Configuration.set(WebWorkConstants.WEBWORK_ACTION_EXTENSION, "jspa");

        FormTag tag = new FormTag();
        tag.setPageContext(pageContext);
        tag.setNamespace("/testNamespace");
        tag.setAction("testNamespaceAction");
        tag.setMethod("POST");
        tag.setName("myForm");

        tag.doStartTag();
        tag.doEndTag();

        Configuration.set(WebWorkConstants.WEBWORK_ACTION_EXTENSION, oldConfiguration);

        verify(FormTag.class.getResource("Formtag-5.txt"));

        // set it back to the default
        Configuration.set(WebWorkConstants.WEBWORK_ACTION_EXTENSION, "action");
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
        tag.setAction("/testNamespace/testNamespaceAction.jspa");
        tag.setMethod("POST");
        tag.setName("myForm");

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
        tag.setName("myForm");
        tag.setMethod("POST");
        tag.setAction("testNamespaceAction");

        tag.doStartTag();
        tag.doEndTag();

        verify(FormTag.class.getResource("Formtag-3.txt"));
    }
    
    public void testFormTagForStackOverflowException1() throws Exception {
    	request.setRequestURI("/requestUri");
    	
    	FormTag form1 = new FormTag();
    	form1.setPageContext(pageContext);
    	form1.doStartTag();
    	
    	assertEquals(form1.getComponent().getComponentStack().size(), 1);
    	
    	ActionTag tag = new ActionTag();
    	tag.setPageContext(pageContext);
    	tag.setName("testAction");
    	tag.doStartTag();
    	
    	assertEquals(tag.getComponent().getComponentStack().size(), 2);
    	
    	tag.doEndTag();
    	
    	assertEquals(form1.getComponent().getComponentStack().size(), 1);
    	
    	form1.doEndTag();
    	
    	assertNull(form1.getComponent()); // component is removed after end tag
    }
    
    public void testFormTagForStackOverflowException2() throws Exception {
    	request.setRequestURI("/requestUri");
    	
    	FormTag form1 = new FormTag();
    	form1.setPageContext(pageContext);
    	form1.doStartTag();
    	
    	assertEquals(form1.getComponent().getComponentStack().size(), 1);
    	
    	FormTag form2 = new FormTag();
    	form2.setPageContext(pageContext);
    	form2.doStartTag();

    	assertEquals(form2.getComponent().getComponentStack().size(), 2);
    	
    	ActionTag tag = new ActionTag();
    	tag.setPageContext(pageContext);
    	tag.setName("testAction");
    	tag.doStartTag();
    	
    	assertEquals(tag.getComponent().getComponentStack().size(), 3);
    	
    	tag.doEndTag();

    	assertEquals(form2.getComponent().getComponentStack().size(), 2);
    	
    	form2.doEndTag();
    	
    	assertEquals(form1.getComponent().getComponentStack().size(), 1);

    	form1.doEndTag();
    	
    	assertNull(form1.getComponent()); // component is removed after end tag
    }
    
    
    public void testFormTagForStackOverflowException3() throws Exception {
    	request.setRequestURI("/requestUri");
    	
    	FormTag form1 = new FormTag();
    	form1.setPageContext(pageContext);
    	form1.doStartTag();
    	
    	assertEquals(form1.getComponent().getComponentStack().size(), 1);
    	
    	FormTag form2 = new FormTag();
    	form2.setPageContext(pageContext);
    	form2.doStartTag();
    	
    	assertEquals(form2.getComponent().getComponentStack().size(), 2);
    	
    	FormTag form3 = new FormTag();
    	form3.setPageContext(pageContext);
    	form3.doStartTag();

    	assertEquals(form3.getComponent().getComponentStack().size(), 3);
    	
    	ActionTag tag = new ActionTag();
    	tag.setPageContext(pageContext);
    	tag.setName("testAction");
    	tag.doStartTag();
    	
    	assertEquals(tag.getComponent().getComponentStack().size(), 4);
    	
    	tag.doEndTag();
    	
    	assertEquals(form3.getComponent().getComponentStack().size(), 3);

    	form3.doEndTag();
    	
    	assertEquals(form2.getComponent().getComponentStack().size(), 2);
    	
    	form2.doEndTag();
    	
    	assertEquals(form1.getComponent().getComponentStack().size(), 1);

    	form1.doEndTag();
    	
    	assertNull(form1.getComponent()); // component is removed after end tag
    }
    
    
    public void testFormComponentIsRemoved() throws Exception {
    	request.setRequestURI("/requestUri");
    	
    	FormTag form = new FormTag();
    	form.setPageContext(pageContext);
    	form.doStartTag();
    	
    	assertEquals(form.getComponent().getComponentStack().size(), 1);
    	
    	form.doEndTag();
    	
    	assertNull(form.getComponent());
    }
    
    
    public void testFormWithNoAction() throws Exception {
        request.setupGetServletPath("/");
        request.setupGetContextPath("/");
        request.setRequestURI("/foo.jsp");

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

/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.spring;

import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.config.Configuration;
import junit.framework.TestCase;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Unit test for {@link WebWorkSpringObjectFactory}.
 *
 * @author Claus Ibsen
 */
public class WebWorkSpringObjectFactoryTest extends TestCase {

    public void testNoSpringContext() throws Exception {
        // to cover situations where there will be logged an error
        WebWorkSpringObjectFactory fac = new WebWorkSpringObjectFactory();
        ServletContext msc = (ServletContext) new MockServletContext();
        fac.init(msc);
    }

    public void testWithSpringContext() throws Exception {
        WebWorkSpringObjectFactory fac = new WebWorkSpringObjectFactory();

        // autowire by constructure, we try a non default setting in this unit test
        Configuration.set(WebWorkConstants.WEBWORK_OBJECTFACTORY_SPRING_AUTOWIRE, "constructor");

        ConfigurableWebApplicationContext ac = new XmlWebApplicationContext();
        ServletContext msc = (ServletContext) new MockServletContext();
        msc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ac);
        ac.setServletContext(msc);
        ac.setConfigLocations(new String[] {"/com/opensymphony/webwork/spring/WebWorkSpringObjectFactoryTest-applicationContext.xml"});
        ac.refresh();

        fac.init(msc);

        assertEquals(AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR, fac.getAutowireStrategy());
    }


}

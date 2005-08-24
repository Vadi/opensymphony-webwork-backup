/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 2/10/2003
 *
 */
package com.opensymphony.webwork.dispatcher;

import junit.framework.TestCase;

import com.mockobjects.servlet.MockHttpServletRequest;
import com.opensymphony.webwork.dispatcher.mapper.DefaultActionMapper;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;


/**
 * @author roughley
 */
public class DefautActionMapperTest extends TestCase {
    public DefautActionMapperTest(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testGetMapping() throws Exception {

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setupGetRequestURI("/my/namespace/actionName.action");
        req.setupGetServletPath("/my/namespace/actionName.action");
        req.setupGetAttribute(null);
        req.addExpectedGetAttributeName("javax.servlet.include.servlet_path");

        DefaultActionMapper mapper = new DefaultActionMapper();
        ActionMapping mapping = mapper.getMapping(req);

        assertEquals( "/my/namespace", mapping.getNamespace() );
        assertEquals( "actionName", mapping.getName() );
        assertEquals( "", mapping.getMethod() );

    }

    public void testGetMappingWithMethod() throws Exception {

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setupGetRequestURI("/my/namespace/actionName!add.action");
        req.setupGetServletPath("/my/namespace/actionName!add.action");
        req.setupGetAttribute(null);
        req.addExpectedGetAttributeName("javax.servlet.include.servlet_path");

        DefaultActionMapper mapper = new DefaultActionMapper();
        ActionMapping mapping = mapper.getMapping(req);

        assertEquals( "/my/namespace", mapping.getNamespace() );
        assertEquals( "actionName", mapping.getName() );
        assertEquals( "add", mapping.getMethod() );

    }

    public void testGetUri() throws Exception {

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setupGetRequestURI("/my/namespace/actionName.action");
        req.setupGetServletPath("/my/namespace/actionName.action");
        req.setupGetAttribute(null);
        req.addExpectedGetAttributeName("javax.servlet.include.servlet_path");

        DefaultActionMapper mapper = new DefaultActionMapper();
        ActionMapping mapping = mapper.getMapping(req);
        assertEquals( "/my/namespace/actionName.action", mapper.getUriFromActionMapping(mapping) );

    }

    public void testGetUriWithMethod() throws Exception {

        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setupGetRequestURI("/my/namespace/actionName!add.action");
        req.setupGetServletPath("/my/namespace/actionName!add.action");
        req.setupGetAttribute(null);
        req.addExpectedGetAttributeName("javax.servlet.include.servlet_path");

        DefaultActionMapper mapper = new DefaultActionMapper();
        ActionMapping mapping = mapper.getMapping(req);

        assertEquals( "/my/namespace/actionName!add.action", mapper.getUriFromActionMapping(mapping) );

    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 2/10/2003
 *
 */
package com.opensymphony.webwork.dispatcher;

import com.mockobjects.servlet.MockHttpServletRequest;
import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.webwork.dispatcher.mapper.DefaultActionMapper;

import java.util.HashMap;


/**
 * @author roughley
 */
public class DefautActionMapperTest extends WebWorkTestCase {

    public void testGetMapping() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setupGetParameterMap(new HashMap());
        req.setupGetRequestURI("/my/namespace/actionName.action");
        req.setupGetServletPath("/my/namespace/actionName.action");
        req.setupGetAttribute(null);
        req.addExpectedGetAttributeName("javax.servlet.include.servlet_path");

        DefaultActionMapper mapper = new DefaultActionMapper();
        ActionMapping mapping = mapper.getMapping(req);

        assertEquals("/my/namespace", mapping.getNamespace());
        assertEquals("actionName", mapping.getName());
        assertNull(mapping.getMethod());
    }

    public void testGetMappingWithMethod() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setupGetParameterMap(new HashMap());
        req.setupGetRequestURI("/my/namespace/actionName!add.action");
        req.setupGetServletPath("/my/namespace/actionName!add.action");
        req.setupGetAttribute(null);
        req.addExpectedGetAttributeName("javax.servlet.include.servlet_path");

        DefaultActionMapper mapper = new DefaultActionMapper();
        ActionMapping mapping = mapper.getMapping(req);

        assertEquals("/my/namespace", mapping.getNamespace());
        assertEquals("actionName", mapping.getName());
        assertEquals("add", mapping.getMethod());
    }

    public void testGetUri() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setupGetParameterMap(new HashMap());
        req.setupGetRequestURI("/my/namespace/actionName.action");
        req.setupGetServletPath("/my/namespace/actionName.action");
        req.setupGetAttribute(null);
        req.addExpectedGetAttributeName("javax.servlet.include.servlet_path");

        DefaultActionMapper mapper = new DefaultActionMapper();
        ActionMapping mapping = mapper.getMapping(req);
        assertEquals("/my/namespace/actionName.action", mapper.getUriFromActionMapping(mapping));
    }

    public void testGetUriWithMethod() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setupGetParameterMap(new HashMap());
        req.setupGetRequestURI("/my/namespace/actionName!add.action");
        req.setupGetServletPath("/my/namespace/actionName!add.action");
        req.setupGetAttribute(null);
        req.addExpectedGetAttributeName("javax.servlet.include.servlet_path");

        DefaultActionMapper mapper = new DefaultActionMapper();
        ActionMapping mapping = mapper.getMapping(req);

        assertEquals("/my/namespace/actionName!add.action", mapper.getUriFromActionMapping(mapping));
    }

    public void testGetMappingWithNoExtension() throws Exception {
        Object old = Configuration.get("webwork.action.extension");
        Configuration.set("webwork.action.extension", "");
        try {
            MockHttpServletRequest req = new MockHttpServletRequest();
            req.setupGetParameterMap(new HashMap());
            req.setupGetRequestURI("/my/namespace/actionName");
            req.setupGetServletPath("/my/namespace/actionName");
            req.setupGetAttribute(null);
            req.addExpectedGetAttributeName("javax.servlet.include.servlet_path");

            DefaultActionMapper mapper = new DefaultActionMapper();
            ActionMapping mapping = mapper.getMapping(req);

            assertEquals("/my/namespace", mapping.getNamespace());
            assertEquals("actionName", mapping.getName());
            assertNull(mapping.getMethod());
        }
        finally {
            Configuration.set("webwork.action.extension", old);
        }
    }
}

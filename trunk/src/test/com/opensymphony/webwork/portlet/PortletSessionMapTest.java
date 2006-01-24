/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.portlet;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;


/**
 * PortletSessionMapTest. Insert description.
 * 
 * @author Nils-Helge Garli
 * @version $Revision$ $Date$
 */
public class PortletSessionMapTest extends MockObjectTestCase {

    public void testPut() {
        
        Mock mockSession = mock(PortletSession.class);
        Mock mockRequest = mock(PortletRequest.class);
        
        PortletRequest req = (PortletRequest)mockRequest.proxy();
        PortletSession session = (PortletSession)mockSession.proxy();
        
        mockRequest.expects(once()).method("getPortletSession").will(returnValue(session));
        Constraint[] params = new Constraint[]{eq("testAttribute1"), eq("testValue1")};
        mockSession.expects(once()).method("setAttribute").with(params);
        mockSession.expects(once()).method("getAttribute").with(eq("testAttribute1")).will(returnValue("testValue1"));
        params = new Constraint[]{eq("testAttribute2"), eq("testValue2")};
        mockSession.expects(once()).method("setAttribute").with(params);
        mockSession.expects(once()).method("getAttribute").with(eq("testAttribute2")).will(returnValue("testValue2"));
        
        PortletSessionMap map = new PortletSessionMap(req);
        map.put("testAttribute1", "testValue1");
        map.put("testAttribute2", "testValue2");
        mockRequest.verify();
        mockSession.verify();
        
    }
    
    public void testGet() {
        Mock mockSession = mock(PortletSession.class);
        Mock mockRequest = mock(PortletRequest.class);
        
        PortletRequest req = (PortletRequest)mockRequest.proxy();
        PortletSession session = (PortletSession)mockSession.proxy();
        
        mockRequest.expects(once()).method("getPortletSession").will(returnValue(session));
        mockSession.expects(once()).method("getAttribute").with(eq("testAttribute1")).will(returnValue("testValue1"));
        mockSession.expects(once()).method("getAttribute").with(eq("testAttribute2")).will(returnValue("testValue2"));
        
        PortletSessionMap map = new PortletSessionMap(req);
        Object val1 = map.get("testAttribute1");
        Object val2 = map.get("testAttribute2");
        assertEquals("testValue1", val1);
        assertEquals("testValue2", val2);
        mockRequest.verify();
        mockSession.verify();
    }
    
    public void testClear() {
        Mock mockSession = mock(PortletSession.class);
        Mock mockRequest = mock(PortletRequest.class);
        
        PortletRequest req = (PortletRequest)mockRequest.proxy();
        PortletSession session = (PortletSession)mockSession.proxy();
        
        mockRequest.expects(once()).method("getPortletSession").will(returnValue(session));
        mockSession.expects(once()).method("invalidate");
        
        PortletSessionMap map = new PortletSessionMap(req);
        map.clear();
        mockRequest.verify();
        mockSession.verify();
    }
    
}

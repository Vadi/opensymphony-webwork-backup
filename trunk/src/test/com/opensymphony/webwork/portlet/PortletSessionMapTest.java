/*
 * Copyright 2004 BEKK Consulting
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

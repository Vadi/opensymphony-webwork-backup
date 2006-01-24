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

import java.util.ArrayList;
import java.util.Collections;

import javax.portlet.PortletRequest;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;


/**
 * PortletRequestMapTest. Insert description.
 * 
 * @author Nils-Helge Garli
 * @version $Revision$ $Date$
 */
public class PortletRequestMapTest extends MockObjectTestCase {

    public void testSetAttribute() {
        
    }
    
    public void testGet() {
        Mock mockRequest = mock(PortletRequest.class, "testGet");
        mockRequest.expects(once()).method("getAttribute").with(eq("testAttribute")).will(returnValue("testValue"));
        PortletRequestMap map = new PortletRequestMap((PortletRequest)mockRequest.proxy());
        String value = (String)map.get("testAttribute");
        mockRequest.verify();
        assertEquals("testValue", value);
    }
    
    public void testPut() {
        Mock mockRequest = mock(PortletRequest.class, "testPut");
        Object value = new String("testValue");
        Constraint[] params = new Constraint[]{eq("testAttribute"), eq(value)};
        mockRequest.expects(once()).method("setAttribute").with(params);
        mockRequest.expects(once()).method("getAttribute").with(eq("testAttribute")).will(returnValue(value));
        PortletRequestMap map = new PortletRequestMap((PortletRequest)mockRequest.proxy());
        Object obj = map.put("testAttribute", value);
        mockRequest.verify();
        assertEquals(obj, value);
    }
    
    public void testClear() {
        Mock mockRequest = mock(PortletRequest.class, "testClear");

        mockRequest.expects(once()).method("removeAttribute").with(eq("a"));
        mockRequest.expects(once()).method("removeAttribute").with(eq("b"));
        
        ArrayList dummy = new ArrayList();
        dummy.add("a");
        dummy.add("b");
        
        mockRequest.expects(once()).method("getAttributeNames").will(returnValue(Collections.enumeration(dummy)));
        
        PortletRequestMap map = new PortletRequestMap((PortletRequest)mockRequest.proxy());
        map.clear();
        mockRequest.verify();
    }
    
}

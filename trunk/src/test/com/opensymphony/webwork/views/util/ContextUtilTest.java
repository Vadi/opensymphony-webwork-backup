/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.util;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

/**
 * Test case for ContextUtil
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class ContextUtilTest extends TestCase {

    public void testAltSyntaxMethod1() throws Exception {
        OgnlValueStack stack = new OgnlValueStack();
        stack.getContext().put("useAltSyntax", "true");
        
        Configuration.reset();
        Configuration.set("webwork.tag.altSyntax", "true");
        
        assertEquals(Configuration.getString("webwork.tag.altSyntax"), "true");
        assertTrue(ContextUtil.isUseAltSyntax(stack.getContext()));
    }
    
    public void testAltSyntaxMethod2() throws Exception {
        OgnlValueStack stack = new OgnlValueStack();
        stack.getContext().put("useAltSyntax", "false");
        
        Configuration.reset();
        Configuration.set("webwork.tag.altSyntax", "true");
        
        assertEquals(Configuration.getString("webwork.tag.altSyntax"), "true");
        assertFalse(ContextUtil.isUseAltSyntax(stack.getContext()));
    }
    
    public void testAltSyntaxMethod3() throws Exception {
        OgnlValueStack stack = new OgnlValueStack();
        stack.getContext().put("useAltSyntax", "true");
        
        Configuration.reset();
        Configuration.set("webwork.tag.altSyntax", "false");
        
        assertEquals(Configuration.getString("webwork.tag.altSyntax"), "false");
        assertFalse(ContextUtil.isUseAltSyntax(stack.getContext()));
    }
    
    public void testAltSyntaxMethod4() throws Exception {
        OgnlValueStack stack = new OgnlValueStack();
        stack.getContext().put("useAltSyntax", "false");
        
        Configuration.reset();
        Configuration.set("webwork.tag.altSyntax", "false");
        
        assertEquals(Configuration.getString("webwork.tag.altSyntax"), "false");
        assertFalse(ContextUtil.isUseAltSyntax(stack.getContext()));
    }
    
    //========================================================
    
    public void testAltSyntaxMethod5() throws Exception {
        OgnlValueStack stack = new OgnlValueStack();
        stack.getContext().put("useAltSyntax", Boolean.TRUE);
        
        Configuration.reset();
        Configuration.set("webwork.tag.altSyntax", "true");
        
        assertEquals(Configuration.getString("webwork.tag.altSyntax"), "true");
        assertTrue(ContextUtil.isUseAltSyntax(stack.getContext()));
    }
    public void testAltSyntaxMethod6() throws Exception {
        OgnlValueStack stack = new OgnlValueStack();
        stack.getContext().put("useAltSyntax", Boolean.FALSE);
        
        Configuration.reset();
        Configuration.set("webwork.tag.altSyntax", "true");
        
        assertEquals(Configuration.getString("webwork.tag.altSyntax"), "true");
        assertFalse(ContextUtil.isUseAltSyntax(stack.getContext()));
    }
    public void testAltSyntaxMethod7() throws Exception {
        OgnlValueStack stack = new OgnlValueStack();
        stack.getContext().put("useAltSyntax", Boolean.TRUE);
        
        Configuration.reset();
        Configuration.set("webwork.tag.altSyntax", "false");
        
        assertEquals(Configuration.getString("webwork.tag.altSyntax"), "false");
        assertFalse(ContextUtil.isUseAltSyntax(stack.getContext()));
    }
    public void testAltSyntaxMethod8() throws Exception {
        OgnlValueStack stack = new OgnlValueStack();
        stack.getContext().put("useAltSyntax", Boolean.FALSE);
        
        Configuration.reset();
        Configuration.set("webwork.tag.altSyntax", "false");
        
        assertEquals(Configuration.getString("webwork.tag.altSyntax"), "false");
        assertFalse(ContextUtil.isUseAltSyntax(stack.getContext()));
    }
    
    // ==========================================
    public void testAltSyntaxMethod9() throws Exception {
        OgnlValueStack stack = new OgnlValueStack();
        stack.getContext().put("useAltSyntax", null);
        
        Configuration.reset();
        Configuration.set("webwork.tag.altSyntax", Boolean.TRUE);
        
        assertEquals(Configuration.get("webwork.tag.altSyntax"), Boolean.TRUE);
        assertFalse(ContextUtil.isUseAltSyntax(stack.getContext()));
    }
}

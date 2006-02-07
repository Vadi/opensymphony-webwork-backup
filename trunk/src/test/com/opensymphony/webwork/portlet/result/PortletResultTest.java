/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.portlet.result;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import junit.textui.TestRunner;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.opensymphony.webwork.portlet.PortletActionConstants;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;

/**
 * PortletResultTest. Insert description.
 * 
 * @author Nils-Helge Garli
 * @version $Revision$ $Date$
 */
public class PortletResultTest extends MockObjectTestCase {
    
    Mock mockInvocation = null;
    Mock mockConfig = null;
    Mock mockCtx = null;
    
    public void setUp() throws Exception {
        super.setUp();
        mockInvocation = mock(ActionInvocation.class);
        mockConfig = mock(PortletConfig.class);
        mockCtx = mock(PortletContext.class);
        
        mockConfig.stubs().method(ANYTHING);
        mockConfig.stubs().method("getPortletContext").will(returnValue(mockCtx.proxy()));
        
        Map paramMap = new HashMap();
        Map sessionMap = new HashMap();
        
        Map context = new HashMap();
        context.put(ActionContext.SESSION, sessionMap);
        context.put(ActionContext.PARAMETERS, paramMap);
        context.put(PortletActionConstants.PORTLET_CONFIG, mockConfig.proxy());
        
        ActionContext.setContext(new ActionContext(context));
        
        mockInvocation.stubs().method("getInvocationContext").will(returnValue(ActionContext.getContext()));
        
    }
    
    public void testDoExecute_render() {
        Mock mockRequest = mock(RenderRequest.class);
        Mock mockResponse = mock(RenderResponse.class);
        Mock mockRd = mock(PortletRequestDispatcher.class);
        Mock mockPrep = mock(PortletRequestDispatcher.class);
        
        RenderRequest req = (RenderRequest)mockRequest.proxy();
        RenderResponse res = (RenderResponse)mockResponse.proxy();
        PortletRequestDispatcher rd = (PortletRequestDispatcher)mockRd.proxy();
        PortletConfig cfg = (PortletConfig)mockConfig.proxy();
        PortletContext ctx = (PortletContext)mockCtx.proxy();
        ActionInvocation inv = (ActionInvocation)mockInvocation.proxy();
        
        Constraint[] params = new Constraint[]{same(req), same(res)};
        mockRd.expects(once()).method("include").with(params);
        mockPrep.expects(once()).method("include").with(params);
        mockCtx.expects(once()).method("getRequestDispatcher").with(eq("/WEB-INF/pages/testPage.jsp")).will(returnValue(rd));
        mockCtx.expects(once()).method("getNamedDispatcher").with(eq("preparator")).will(returnValue(mockPrep.proxy()));
        mockResponse.expects(once()).method("setContentType").with(eq("text/html"));
        mockConfig.expects(once()).method("getPortletContext").will(returnValue(ctx));
        
        mockRequest.stubs().method("getPortletMode").will(returnValue(PortletMode.VIEW));
        
        ActionContext ctxMap = ActionContext.getContext();
        ctxMap.put(PortletActionConstants.RESPONSE, res);
        ctxMap.put(PortletActionConstants.REQUEST, req);
        ctxMap.put(PortletActionConstants.PORTLET_CONFIG, cfg);
        ctxMap.put(PortletActionConstants.PHASE, PortletActionConstants.RENDER_PHASE);
        
        PortletResult result = new PortletResult();
        try {
            result.doExecute("/WEB-INF/pages/testPage.jsp", inv);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("Error occured!");
        }
        
    }
    
    public void testDoExecute_event_locationIsAction() {
        
        Mock mockRequest = mock(ActionRequest.class);
        Mock mockResponse = mock(ActionResponse.class);
        
        Constraint[] params = new Constraint[]{eq(PortletActionConstants.ACTION_PARAM), eq("testView")};
        mockResponse.expects(once()).method("setRenderParameter").with(params);
        params = new Constraint[]{eq(PortletActionConstants.MODE_PARAM), eq(PortletMode.VIEW.toString())};
        mockResponse.expects(once()).method("setRenderParameter").with(params);
        mockRequest.stubs().method("getPortletMode").will(returnValue(PortletMode.VIEW));
        ActionContext ctx = ActionContext.getContext();
        
        ctx.put(PortletActionConstants.REQUEST, mockRequest.proxy());
        ctx.put(PortletActionConstants.RESPONSE, mockResponse.proxy());
        ctx.put(PortletActionConstants.PHASE, PortletActionConstants.EVENT_PHASE);
        
        PortletResult result = new PortletResult();
        try {
            result.doExecute("testView.action", (ActionInvocation)mockInvocation.proxy());
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("Error occured!");
        }
        
    }
    
    public void testDoExecute_event_locationIsJsp() {
        Mock mockRequest = mock(ActionRequest.class);
        Mock mockResponse = mock(ActionResponse.class);
        
        Constraint[] params = new Constraint[]{eq(PortletActionConstants.ACTION_PARAM), eq("renderDirect")};
        mockResponse.expects(once()).method("setRenderParameter").with(params);
        params = new Constraint[]{eq("location"), eq("/WEB-INF/pages/testJsp.jsp")};
        mockResponse.expects(once()).method("setRenderParameter").with(params);
        params = new Constraint[]{eq(PortletActionConstants.MODE_PARAM), eq(PortletMode.VIEW.toString())};
        mockResponse.expects(once()).method("setRenderParameter").with(params);
        mockRequest.stubs().method("getPortletMode").will(returnValue(PortletMode.VIEW));
        
        ActionContext ctx = ActionContext.getContext();
        
        ctx.put(PortletActionConstants.REQUEST, mockRequest.proxy());
        ctx.put(PortletActionConstants.RESPONSE, mockResponse.proxy());
        ctx.put(PortletActionConstants.PHASE, PortletActionConstants.EVENT_PHASE);
        
        PortletResult result = new PortletResult();
        try {
            result.doExecute("/WEB-INF/pages/testJsp.jsp", (ActionInvocation)mockInvocation.proxy());
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("Error occured!");
        }
    }
    
    public void testDoExecute_event_locationHasQueryParams() {
        Mock mockRequest = mock(ActionRequest.class);
        Mock mockResponse = mock(ActionResponse.class);
        
        Constraint[] params = new Constraint[]{eq(PortletActionConstants.ACTION_PARAM), eq("testView")};
        mockResponse.expects(once()).method("setRenderParameter").with(params);
        params = new Constraint[]{eq("testParam1"), eq("testValue1")};
        mockResponse.expects(once()).method("setRenderParameter").with(params);
        params = new Constraint[]{eq("testParam2"), eq("testValue2")};
        mockResponse.expects(once()).method("setRenderParameter").with(params);
        params = new Constraint[]{eq(PortletActionConstants.MODE_PARAM), eq(PortletMode.VIEW.toString())};
        mockResponse.expects(once()).method("setRenderParameter").with(params);
        mockRequest.stubs().method("getPortletMode").will(returnValue(PortletMode.VIEW));
        
        ActionContext ctx = ActionContext.getContext();
        
        ctx.put(PortletActionConstants.REQUEST, mockRequest.proxy());
        ctx.put(PortletActionConstants.RESPONSE, mockResponse.proxy());
        ctx.put(PortletActionConstants.PHASE, PortletActionConstants.EVENT_PHASE);
        
        PortletResult result = new PortletResult();
        try {
            result.doExecute("testView.action?testParam1=testValue1&testParam2=testValue2", (ActionInvocation)mockInvocation.proxy());
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("Error occured!");
        }
    }
    
    public void tearDown() throws Exception {
        super.tearDown();
        ActionContext.setContext(null);
    }
    
    public static void main(String[] args) {
        TestRunner.run(PortletResultTest.class);
    }
    
}

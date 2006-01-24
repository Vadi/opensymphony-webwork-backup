/*
 * Created on Nov 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.opensymphony.webwork.portlet.context;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;

import junit.textui.TestRunner;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.opensymphony.webwork.portlet.PortletActionConstants;
import com.opensymphony.xwork.ActionContext;

/**
 * @author Nils-Helge Garli
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PortletActionContextTest extends MockObjectTestCase {
    
    public void testGetRenderRequest() {
        Mock mockRenderReq = mock(RenderRequest.class);
        RenderRequest req = (RenderRequest)mockRenderReq.proxy();
        
        Map extraContext = new HashMap();
        extraContext.put(PortletActionConstants.REQUEST, req);
        extraContext.put(PortletActionConstants.PHASE, PortletActionConstants.RENDER_PHASE);
        ActionContext ctx = new ActionContext(extraContext);
        ActionContext.setContext(ctx);
        
        assertNotNull(PortletActionContext.getRenderRequest());
    }
    
    public static void main(String[] args) {
        TestRunner.run(PortletActionContextTest.class);
    }
}

/*
 * Created on 26.jun.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.opensymphony.webwork.portlet.example;

import java.util.Collection;
import java.util.Map;

import javax.portlet.RenderRequest;

import com.opensymphony.webwork.portlet.context.PortletActionContext;
import com.opensymphony.xwork.ActionSupport;

/**
 * @author Nils-Helge Garli
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FormResultAction extends ActionSupport {

    private String result = null;
    
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    
    public Collection getRenderParams() {
        RenderRequest req = PortletActionContext.getRenderRequest();
        Map params = req.getParameterMap();
        return params.entrySet();
    }
}

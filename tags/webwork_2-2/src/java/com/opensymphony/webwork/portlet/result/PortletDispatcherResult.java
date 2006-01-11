/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.result;

import com.opensymphony.webwork.portlet.WebWorkPortletStatics;
import com.opensymphony.webwork.portlet.context.PortletActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;

import javax.portlet.PortletSession;


/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu </a>
 * @since 2005-7-18
 */
public class PortletDispatcherResult implements Result, WebWorkPortletStatics {
    private String _location;

    public void setLocation(String location) {
        _location = location;
    }

    public void execute(ActionInvocation invocation) throws Exception {
        PortletSession portletSession = (PortletSession) PortletActionContext.getPortletSession();
        portletSession.setAttribute(RENDER_TEMPLATE, _location);
    }
}
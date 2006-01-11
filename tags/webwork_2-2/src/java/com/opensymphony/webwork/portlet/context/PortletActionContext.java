/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.context;

import com.opensymphony.webwork.portlet.WebWorkPortletStatics;
import com.opensymphony.xwork.ActionContext;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import java.util.Map;

/**
 * PorletActionContext only in WebWork Framework scope.
 *
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu </a>
 * @since 2005-7-18
 */
public class PortletActionContext extends ActionContext implements WebWorkPortletStatics {


    public PortletActionContext(Map context) {
        super(context);
    }

    public static PortletRequest getPortletRequest() {
        return (PortletRequest) ActionContext.getContext().get(PORTLET_REQUEST);
    }

    public static PortletResponse getPortletResponse() {
        return (PortletResponse) ActionContext.getContext().get(PORTLET_RESPONSE);
    }

    public static PortletContext getPortletContext() {
        return (PortletContext) ActionContext.getContext().get(PORTLET_CONTEXT);
    }

    public static Map getApplicationContextScopeSession() {
        return (Map) ActionContext.getContext().get(APPLICATION_SCOPE_SESSION);
    }

    public static PortletSession getPortletSession() {
        return getPortletRequest().getPortletSession();
    }


}
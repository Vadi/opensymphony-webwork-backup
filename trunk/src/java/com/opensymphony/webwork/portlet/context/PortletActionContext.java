/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.portlet.context;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.opensymphony.webwork.portlet.PortletActionConstants;
import com.opensymphony.xwork.ActionContext;


/**
 * PortletActionContext. Insert description.
 * 
 * @author Nils-Helge Garli
 * @version $Revision$ $Date$
 */
public class PortletActionContext implements PortletActionConstants {

    public static PortletConfig getPortletConfig() {
        return (PortletConfig) getContext().get(PORTLET_CONFIG);
    }

    public static RenderRequest getRenderRequest() {
        if (!isRender()) {
            throw new IllegalStateException(
                    "RenderRequest cannot be obtained in event phase");
        }
        return (RenderRequest) getContext().get(REQUEST);
    }

    public static RenderResponse getRenderResponse() {
        if (!isRender()) {
            throw new IllegalStateException(
                    "RenderResponse cannot be obtained in event phase");
        }
        return (RenderResponse) getContext().get(RESPONSE);
    }

    public static ActionRequest getActionRequest() {
        if (!isEvent()) {
            throw new IllegalStateException(
                    "ActionRequest cannot be obtained in render phase");
        }
        return (ActionRequest) getContext().get(REQUEST);
    }

    public static ActionResponse getActionResponse() {
        if (!isEvent()) {
            throw new IllegalStateException(
                    "ActionResponse cannot be obtained in render phase");
        }
        return (ActionResponse) getContext().get(RESPONSE);
    }
    
    public static String getPortletNamespace() {
        return (String)getContext().get(PORTLET_NAMESPACE);
    }

    public static PortletRequest getRequest() {
        return (PortletRequest) getContext().get(REQUEST);
    }

    public static PortletResponse getResponse() {
        return (PortletResponse) getContext().get(RESPONSE);
    }

    public static Integer getPhase() {
        return (Integer) getContext().get(PHASE);
    }

    public static boolean isRender() {
        return PortletActionConstants.RENDER_PHASE.equals(getPhase());
    }

    public static boolean isEvent() {
        return PortletActionConstants.EVENT_PHASE.equals(getPhase());
    }

    private static ActionContext getContext() {
        return ActionContext.getContext();
    }
    
    public static boolean isPortletRequest() {
        return getRequest() != null;
    }

    public static String getDefaultActionForMode() {
        return (String)getContext().get(DEFAULT_ACTION_FOR_MODE);
    }

    public static Map getModeNamespaceMap() {
        return (Map)getContext().get(MODE_NAMESPACE_MAP);
    }

}
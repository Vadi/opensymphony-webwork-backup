/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.context;

import com.opensymphony.module.sitemesh.Factory;
import com.opensymphony.webwork.portlet.WebWorkPortletStatics;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.Map;

/**
 * We should get PortletContext object in all running scope.
 * PortletContext must add Serializable interface, because
 * processAction & doView in JetSpeed/Tomcat ware belong to two
 * difference threads. We should use PortletSession as bridge to
 * transfer PortletContext between these two threads.
 *
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu </a>
 * @since 2005-7-18
 */
public class PortletContext implements Serializable {

    static ThreadLocal portletContextThreadLocal = new PortletContextThreadLocal();

    Map mapContext;

    private static class PortletContextThreadLocal extends ThreadLocal {
        private PortletContextThreadLocal() {
        }
    }

    public PortletContext(Map mapContext) {
        this.mapContext = mapContext;
    }

    public static PortletContext getContext() {
        PortletContext portletContext = (PortletContext) portletContextThreadLocal.get();

        if (portletContext == null) {
            OgnlValueStack vs = new OgnlValueStack();
            portletContext = new PortletContext(vs.getContext());
            portletContextThreadLocal.set(portletContext);
        }

        return portletContext;
    }

    ///////////////Fix for JetSpeed/Tomcat////////////////////
    public static void setContext(PortletContext portletContext) {
        portletContextThreadLocal.set(portletContext);
    }

    public Object get(Object key) {
        return mapContext.get(key);
    }

    public void set(Object key, Object value) {
        mapContext.put(key, value);
    }

    public void setActionContextMap(Map contextMap) {
        set(WebWorkPortletStatics.ACTION_CONTEXT_MAP, contextMap);
    }

    public Map getActionContextMap() {
        return (Map) get(WebWorkPortletStatics.ACTION_CONTEXT_MAP);
    }

    public void setNameAction(String nameAction) {
        set(WebWorkPortletStatics.NAME_ACTION, nameAction);
    }

    public String getNameAction() {
        return (String) get(WebWorkPortletStatics.NAME_ACTION);
    }

    public void setPortletContextType(String portletContextType) {
        set(WebWorkPortletStatics.PORTLET_CONTENT_TYPE, portletContextType);
    }

    public String getPortletContextType() {
        return (String) get(WebWorkPortletStatics.PORTLET_CONTENT_TYPE);
    }

    public void setPortletResult(String portletResult) {
        set(WebWorkPortletStatics.PORTLET_RESULT, portletResult);
    }

    public String getPortletResult() {
        return (String) get(WebWorkPortletStatics.PORTLET_RESULT);
    }

    public void setActionURL(String actionURL) {
        set(WebWorkPortletStatics.PORTLET_ACTION_URL, actionURL);
    }

    public String getActionURL() {
        return (String) get(WebWorkPortletStatics.PORTLET_ACTION_URL);
    }

    public void setRunTag(String runTag) {
        set(WebWorkPortletStatics.PORTLET_RUN_TAG, runTag);
    }

    public String getRunTag() {
        return (String) get(WebWorkPortletStatics.PORTLET_RUN_TAG);
    }

    public void setUserUID(String userName) {
        set(WebWorkPortletStatics.USER_UID, userName);
    }

    public String getUserUID() {
        return (String) get(WebWorkPortletStatics.USER_UID);
    }

    public void setPortletInstanceUID(Integer portletInstanceUID) {
        set(WebWorkPortletStatics.PORTLET_INSTANCE_UID, portletInstanceUID);
    }

    public Integer getPortletInstanceUID() {
        return (Integer) get(WebWorkPortletStatics.PORTLET_INSTANCE_UID);
    }

    public void setPortletMode(String portletMode) {
        set(WebWorkPortletStatics.PORTLET_MODE, portletMode);
    }

    public String getPortletMode() {
        return (String) get(WebWorkPortletStatics.PORTLET_MODE);
    }

    public void setSiteMeshFactory(Factory factory) {
        set(WebWorkPortletStatics.SITEMESH_FACTORY, factory);
    }

    public Factory getSiteMeshFactory() {
        return (Factory) get(WebWorkPortletStatics.SITEMESH_FACTORY);
    }

    public void setServletConfig(ServletConfig servletConfig) {
        set(WebWorkPortletStatics.SERVLET_CONFIG, servletConfig);
    }

    public ServletConfig getServletConfig() {
        return (ServletConfig) get(WebWorkPortletStatics.SERVLET_CONFIG);
    }

    //////////////////////////////Fix for JetSpeed/Tomcat /////////////////////////
    public void setServletContext(ServletContext servletContext) {
        set(WebWorkPortletStatics.SERVLET_CONTEXT, servletContext);
    }

    public ServletContext getServletContext() {
        return (ServletContext) get(WebWorkPortletStatics.SERVLET_CONTEXT);
    }
    ////////////////////////////////////////////////////////////////////////////////

//    public void setActionExecuted(String actionExecuted) {
//        set(WebWorkPortletStatics.ACTION_EXECUTED, actionExecuted);
//    }
//
//    public String getActionExecuted() {
//        return (String) get(WebWorkPortletStatics.ACTION_EXECUTED);
//    }

}
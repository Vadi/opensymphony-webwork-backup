/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.util;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import java.io.NotSerializableException;
import java.io.Serializable;


/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu </a>
 * @since 2005-7-18
 */
public class PortletMessaging {

    public static final void publish(PortletRequest request, String messageName, Object message)
            throws NotSerializableException {
        String key = messageName;
        if (message instanceof Serializable) {
            request.getPortletSession().setAttribute(key, message, PortletSession.PORTLET_SCOPE);
        } else {
            throw new NotSerializableException("Message not serializable for " + key);
        }
    }

    public static final Object consume(PortletRequest request, String messageName) {
        String key = messageName;
        Object object = request.getPortletSession().getAttribute(key, PortletSession.PORTLET_SCOPE);
        // consume it
        request.getPortletSession().removeAttribute(key, PortletSession.PORTLET_SCOPE);
        return object;
    }

    public static final Object receive(PortletRequest request, String messageName) {
        String key = messageName;
        Object object = request.getPortletSession().getAttribute(key, PortletSession.PORTLET_SCOPE);
        return object;
    }

    public static final void cancel(PortletRequest request, String messageName) {
        String key = messageName;
        request.getPortletSession().removeAttribute(key, PortletSession.PORTLET_SCOPE);
    }

}

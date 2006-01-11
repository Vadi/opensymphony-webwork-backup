/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import java.io.Serializable;
import java.util.*;


/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu </a>
 * @since 2005-7-18
 */
public class SessionMap extends AbstractMap implements Serializable {

    PortletSession session;
    Set entries;


    /**
     * Creates a new session map given a http servlet request. Note, ths enumeration of request
     * attributes will occur when the map entries are asked for.
     *
     * @param request the http servlet request object.
     */
    public SessionMap(PortletRequest request) {
        this.session = request.getPortletSession(true);
    }


    /**
     * Removes all attributes from the session as well as clears entries in this map.
     */
    public void clear() {
        synchronized (session) {
            entries = null;
            session.invalidate();
        }
    }

    /**
     * Returns a Set of attributes from the http session.
     *
     * @return a Set of attributes from the http session.
     */
    public Set entrySet() {
        synchronized (session) {
            if (entries == null) {
                entries = new HashSet();

                Enumeration enumeration = session.getAttributeNames();

                while (enumeration.hasMoreElements()) {
                    final String key = enumeration.nextElement().toString();
                    final Object value = session.getAttribute(key);
                    entries.add(new Map.Entry() {
                        public boolean equals(Object obj) {
                            Map.Entry entry = (Map.Entry) obj;

                            return ((key == null) ? (entry.getKey() == null) : key.equals(entry.getKey())) && ((value == null) ? (entry.getValue() == null) : value.equals(entry.getValue()));
                        }

                        public int hashCode() {
                            return ((key == null) ? 0 : key.hashCode()) ^ ((value == null) ? 0 : value.hashCode());
                        }

                        public Object getKey() {
                            return key;
                        }

                        public Object getValue() {
                            return value;
                        }

                        public Object setValue(Object obj) {
                            session.setAttribute(key.toString(), obj);

                            return value;
                        }
                    });
                }
            }
        }

        return entries;
    }

    /**
     * Returns the session attribute associated with the given key or <tt>null</tt> if it doesn't exist.
     *
     * @param key the name of the session attribute.
     * @return the session attribute or <tt>null</tt> if it doesn't exist.
     */
    public Object get(Object key) {
        synchronized (session) {
            return session.getAttribute(key.toString());
        }
    }

    /**
     * Saves an attribute in the session.
     *
     * @param key   the name of the session attribute.
     * @param value the value to set.
     * @return the object that was just set.
     */
    public Object put(Object key, Object value) {
        synchronized (session) {
            entries = null;
            session.setAttribute(key.toString(), value);

            return get(key);
        }
    }

    /**
     * Removes the specified session attribute.
     *
     * @param key the name of the attribute to remove.
     * @return the value that was removed or <tt>null</tt> if the value was not found (and hence, not removed).
     */
    public Object remove(Object key) {
        synchronized (session) {
            entries = null;

            Object value = get(key);
            session.removeAttribute(key.toString());

            return value;
        }
    }
}

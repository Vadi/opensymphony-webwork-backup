/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import java.io.Serializable;

import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * A simple implementation of the {@link java.util.Map} interface to handle a collection of HTTP session
 * attributes. The {@link #entrySet()} method enumerates over all session attributes and creates a Set of entries.
 * Note, this will occur lazily - only when the entry set is asked for.
 *
 * @author <a href="mailto:rickard@middleware-company.com">Rickard Öberg</a>
 * @author Bill Lynch (docs)
 */
public class SessionMap extends AbstractMap implements Serializable {

    HttpServletRequest request;
    Set entries;

    /**
     * Creates a new session map given a http servlet request. Note, ths enumeration of request
     * attributes will occur when the map entries are asked for.
     *
     * @param request the http servlet request object.
     */
    public SessionMap(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Removes all attributes from the session as well as clears entries in this map.
     */
    public void clear() {
        HttpSession session = request.getSession();

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
        HttpSession session = request.getSession();

        synchronized (session) {
            if (entries == null) {
                entries = new HashSet();

                Enumeration enum = session.getAttributeNames();

                while (enum.hasMoreElements()) {
                    final String key = enum.nextElement().toString();
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
                                request.getSession().setAttribute(key.toString(), obj);

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
        HttpSession session = request.getSession();

        synchronized (session) {
            return session.getAttribute(key.toString());
        }
    }

    /**
     * Saves an attribute in the session.
     *
     * @param key the name of the session attribute.
     * @param value the value to set.
     * @return the object that was just set.
     */
    public Object put(Object key, Object value) {
        HttpSession session = request.getSession();

        synchronized (session) {
            entries = null;
            session.setAttribute(key.toString(), value);

            return get(key);
        }
    }

    /**
     * Removes the specified session attribute.
     * @param key the name of the attribute to remove.
     * @return the value that was removed or <tt>null</tt> if the value was not found (and hence, not removed).
     */
    public Object remove(Object key) {
        HttpSession session = request.getSession();

        synchronized (session) {
            entries = null;

            Object value = get(key);
            session.removeAttribute(key.toString());

            return value;
        }
    }
}

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

import javax.servlet.ServletContext;


/**
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class ApplicationMap extends AbstractMap implements Serializable {
    //~ Instance fields ////////////////////////////////////////////////////////

    ServletContext context;
    Set entries;

    //~ Constructors ///////////////////////////////////////////////////////////

    public ApplicationMap(ServletContext ctx) {
        this.context = ctx;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void clear() {
        entries = null;

        Enumeration enum = context.getAttributeNames();

        while (enum.hasMoreElements()) {
            context.removeAttribute(enum.nextElement().toString());
        }
    }

    public Set entrySet() {
        if (entries == null) {
            entries = new HashSet();

            // Add servlet context attributes
            Enumeration enum = context.getAttributeNames();

            while (enum.hasMoreElements()) {
                final String key = enum.nextElement().toString();
                final Object value = context.getAttribute(key);
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
                            context.setAttribute(key.toString(), obj);

                            return value;
                        }
                    });
            }

            // Add servlet context init params
            enum = context.getInitParameterNames();

            while (enum.hasMoreElements()) {
                final String key = enum.nextElement().toString();
                final Object value = context.getInitParameter(key);
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
                            context.setAttribute(key.toString(), obj);

                            return value;
                        }
                    });
            }
        }

        return entries;
    }

    public Object get(Object key) {
        // Try context attributes first, then init params
        // This gives the proper shadowing effects
        String keyString = key.toString();
        Object value = context.getAttribute(keyString);

        return (value == null) ? context.getInitParameter(keyString) : value;
    }

    public Object put(Object key, Object value) {
        entries = null;
        context.setAttribute(key.toString(), value);

        return get(key);
    }

    public Object remove(Object key) {
        entries = null;

        Object value = get(key);
        context.removeAttribute(key.toString());

        return value;
    }
}

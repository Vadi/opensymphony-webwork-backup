/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.*;

/**
 * ProxyAttrAdapter is a pass-through adapter for objects which already
 * implement the Attr interface.  All methods are proxied to the underlying
 * Node except node traversal (e.g. getParent()) related methods which
 * are implemented by the abstract adapter node to work with the parent adapter.
 *
 * @author Pat Niemeyer (pat@pat.net)
 *
 */
public class ProxyAttrAdapter extends ProxyNodeAdapter implements Attr {
    private Log log = LogFactory.getLog(this.getClass());

    public ProxyAttrAdapter(AdapterFactory factory, AdapterNode parent, Attr value) {
        super(factory, parent, value);
    }

    // convenience
    protected Attr attr() {
        return (Attr) getPropertyValue();
    }

    // Proxied Attr methods

    public String getName() {
        return attr().getName();
    }

    public boolean getSpecified() {
        return attr().getSpecified();
    }

    public String getValue() {
        return attr().getValue();
    }

    public void setValue(String string) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public Element getOwnerElement() {
        return (Element) getParent();
    }

    // DOM level 3

    public TypeInfo getSchemaTypeInfo() {
        throw operationNotSupported();
    }

    public boolean isId() {
        throw operationNotSupported();
    }

    // end DOM level 3

    // End Proxied Attr methods

    public String toString() {
        return "ProxyAttribute for: " + attr();
    }
}


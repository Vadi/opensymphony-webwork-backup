/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.*;

/**
 * ProxyTextNodeAdapter is a pass-through adapter for objects which already
 * implement the Text interface.  All methods are proxied to the underlying
 * Node except getParent(), getNextSibling() and getPreviousSibling(), which
 * are implemented by the abstract adapter node to work with the parent adapter.
 *
 * @author Pat Niemeyer (pat@pat.net)
 */
public class ProxyTextNodeAdapter extends ProxyNodeAdapter implements Text {
    private Log log = LogFactory.getLog(this.getClass());

    public ProxyTextNodeAdapter(AdapterFactory factory, AdapterNode parent, Text value) {
        super(factory, parent, value);
    }

    // convenience
    Text text() {
        return (Text) getPropertyValue();
    }

    public String toString() {
        return "ProxyTextNode for: " + text();
    }

    public Text splitText(int offset) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public int getLength() {
        return text().getLength();
    }

    public void deleteData(int offset, int count) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public String getData() throws DOMException {
        return text().getData();
    }

    public String substringData(int offset, int count) throws DOMException {
        return text().substringData(offset, count);
    }

    public void replaceData(int offset, int count, String arg) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public void insertData(int offset, String arg) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public void appendData(String arg) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public void setData(String data) throws DOMException {
        throw new UnsupportedOperationException();
    }

    // DOM level 3

    public boolean isElementContentWhitespace() {
        throw operationNotSupported();
    }

    public String getWholeText() {
        throw operationNotSupported();
    }

    public Text replaceWholeText(String string) throws DOMException {
        throw operationNotSupported();
    }
}


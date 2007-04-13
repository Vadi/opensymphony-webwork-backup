/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.*;
import java.util.List;
import java.util.ArrayList;

/**
 * ProxyElementAdapter is a pass-through adapter for objects which already
 * implement the Element interface.  All methods are proxied to the underlying
 * Node except getParent(), getNextSibling() and getPreviousSibling(), which
 * are implemented by the abstract adapter node to work with the parent adapter.
 *
 * @author Pat Niemeyer (pat@pat.net)
 */
/*
	Note: this class wants to be (extend) both an AbstractElementAdapter
	and ProxyElementAdapter, but its proxy-ness is winning right now.
*/
public class ProxyElementAdapter extends ProxyNodeAdapter implements Element {
    private Log log = LogFactory.getLog(this.getClass());

    public ProxyElementAdapter(AdapterFactory factory, AdapterNode parent, Element value) {
        super(factory, parent, value);
    }

    /**
     * Get the proxied Element
     */
    protected Element element() {
        return (Element) getPropertyValue();
    }

    protected List buildChildAdapters() {
        List adapters = new ArrayList();
        NodeList children = node().getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            Node adapter = wrap(child);
            if (adapter != null) {
                log.debug("wrapped child node: " + child.getNodeName());
                adapters.add(adapter);
            }
        }
        return adapters;
    }

    // Proxied Element methods

    public String getTagName() {
        return element().getTagName();
    }

    public boolean hasAttribute(String name) {
        return element().hasAttribute(name);
    }

    public String getAttribute(String name) {
        return element().getAttribute(name);
    }

    public boolean hasAttributeNS(String namespaceURI, String localName) {
        return element().hasAttributeNS(namespaceURI, localName);
    }

    public Attr getAttributeNode(String name) {
        log.debug("wrapping attribute");
        return (Attr) wrap(element().getAttributeNode(name));
    }

    // I'm overriding this just for clarity.  The base impl is correct.
    public NodeList getElementsByTagName(String name) {
        return super.getElementsByTagName(name);
    }

    public String getAttributeNS(String namespaceURI, String localName) {
        return element().getAttributeNS(namespaceURI, localName);
    }

    public Attr getAttributeNodeNS(String namespaceURI, String localName) {
        return (Attr) wrap(element().getAttributeNodeNS(namespaceURI, localName));
    }

    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        return super.getElementsByTagNameNS(namespaceURI, localName);
    }

    // Unsupported mutators of Element

    public void removeAttribute(String name) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public void setAttribute(String name, String value) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public Attr setAttributeNode(Attr newAttr) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
        throw new UnsupportedOperationException();
    }

    // end proxied Element methods

    // unsupported DOM level 3 methods

    public TypeInfo getSchemaTypeInfo() {
        throw operationNotSupported();
    }

    public void setIdAttribute(String string, boolean b) throws DOMException {
        throw operationNotSupported();
    }

    public void setIdAttributeNS(String string, String string1, boolean b) throws DOMException {
        throw operationNotSupported();
    }

    public void setIdAttributeNode(Attr attr, boolean b) throws DOMException {
        throw operationNotSupported();
    }

    // end DOM level 3 methods

    public String toString() {
        return "ProxyElement for: " + element();
    }
}


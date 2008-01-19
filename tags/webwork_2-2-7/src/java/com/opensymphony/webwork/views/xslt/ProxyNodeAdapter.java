/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.*;

/**
 * ProxyNodeAdapter is a read-only delegating adapter for objects which already
 * implement the Node interface.  All methods are proxied to the underlying
 * Node except getParent(), getNextSibling() and getPreviousSibling(), which
 * are implemented by the abstract adapter node to work with the parent adapter.
 *
 * @author Pat Niemeyer (pat@pat.net)
 */
public abstract class ProxyNodeAdapter extends AbstractAdapterNode {
    private Log log = LogFactory.getLog(this.getClass());

    public ProxyNodeAdapter(AdapterFactory factory, AdapterNode parent, Node value) {
        setContext(factory, parent, "document"/*propname unused*/, value);
        log.debug("proxied node is: " + value);
        log.debug("node class is: " + value.getClass());
        log.debug("node type is: " + value.getNodeType());
        log.debug("node name is: " + value.getNodeName());
    }

    /**
     * Get the proxied Node value
     */
    protected Node node() {
        return (Node) getPropertyValue();
    }

    /**
     * Get and adapter to wrap the proxied node.
     *
     * @param node
     */
    protected Node wrap(Node node) {
        return getAdapterFactory().proxyNode(this, node);
    }

    protected NamedNodeMap wrap(NamedNodeMap nnm) {
        return getAdapterFactory().proxyNamedNodeMap(this, nnm);
    }
    //protected NodeList wrap( NodeList nl ) { }

    //protected Node unwrap( Node child ) {
    //	return ((ProxyNodeAdapter)child).node();
    //}

    // Proxied Node methods

    public String getNodeName() {
        log.trace("getNodeName");
        return node().getNodeName();
    }

    public String getNodeValue() throws DOMException {
        log.trace("getNodeValue");
        return node().getNodeValue();
    }

    public short getNodeType() {
        if (log.isTraceEnabled())
            log.trace("getNodeType: " + getNodeName() + ": " + node().getNodeType());
        return node().getNodeType();
    }

    public NamedNodeMap getAttributes() {
        NamedNodeMap nnm = wrap(node().getAttributes());
        if (log.isTraceEnabled())
            log.trace("getAttributes: " + nnm);
        return nnm;
    }

    public boolean hasChildNodes() {
        log.trace("hasChildNodes");
        return node().hasChildNodes();
    }

    public boolean isSupported(String s, String s1) {
        log.trace("isSupported");
        // Is this ok?  What kind of features are they asking about?
        return node().isSupported(s, s1);
    }

    public String getNamespaceURI() {
        log.trace("getNamespaceURI");
        return node().getNamespaceURI();
    }

    public String getPrefix() {
        log.trace("getPrefix");
        return node().getPrefix();
    }

    public String getLocalName() {
        log.trace("getLocalName");
        return node().getLocalName();
    }

    public boolean hasAttributes() {
        log.trace("hasAttributes");
        return node().hasAttributes();
    }

    // End proxied Node methods

    public String toString() {
        return "ProxyNode for: " + node();
    }
}


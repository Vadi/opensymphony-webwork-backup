/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A NamedNodeMap that wraps the Nodes returned in their proxies.
 * 
 * @author Pat Niemeyer (pat@pat.net)
 */
/*
  Note: Since maps have no guaranteed order we don't need to worry about identity
  here as we do with "child" adapters.  In that case we need to preserve identity
  in order to support finding the next/previous siblings.
 */
public class ProxyNamedNodeMap implements NamedNodeMap {
    private NamedNodeMap nodes;
    private AdapterFactory adapterFactory;
    private AdapterNode parent;
    private Log log = LogFactory.getLog(this.getClass());

    public ProxyNamedNodeMap(AdapterFactory factory, AdapterNode parent, NamedNodeMap nodes) {
        this.nodes = nodes;
        this.adapterFactory = factory;
        this.parent = parent;
    }

    protected Node wrap(Node node) {
        return adapterFactory.proxyNode(parent, node);
    }

    public int getLength() {
        return nodes.getLength();
    }

    public Node item(int index) {
        return wrap(nodes.item(index));
    }

    public Node getNamedItem(String name) {
        return wrap((Node) nodes.getNamedItem(name));
    }

    public Node removeNamedItem(String name) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public Node setNamedItem(Node arg) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public Node setNamedItemNS(Node arg) throws DOMException {
        throw new UnsupportedOperationException();
    }

    public Node getNamedItemNS(String namespaceURI, String localName) {
        return wrap((Node) nodes.getNamedItemNS(namespaceURI, localName));
    }

    public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
        throw new UnsupportedOperationException();
    }
}

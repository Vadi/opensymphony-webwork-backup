/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.Node;

/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * @author Pat Niemeyer (pat@pat.net)
 */
public interface AdapterNode extends Node {
    /**
     * The adapter factory that created this node.
     */
    AdapterFactory getAdapterFactory();

    /**
     * The adapter factory that created this node.
     */
    void setAdapterFactory(AdapterFactory factory);

    /**
     * The parent adapter node of this node. Note that our parent must be another adapter node, but our children may be any
     * kind of Node.
     */
    AdapterNode getParent();

    /**
     * The parent adapter node of this node. Note that our parent must be another adapter node, but our children may be any
     * kind of Node.
     */
    void setParent(AdapterNode parent);

    /**
     * The child node before the specified sibling
     */
    Node getChildBefore(Node thisNode);

    /**
     * The child node after the specified sibling
     */
    Node getChildAfter(Node thisNode);

    /**
     * The name of the Java object (property) that we are adapting
     */
    String getPropertyName();

    /**
     * The name of the Java object (property) that we are adapting
     */
    void setPropertyName(String name);

    /**
     * The Java object (property) that we are adapting
     */
    Object getPropertyValue();

    /** The Java object (property) that we are adapting */
    void setPropertyValue(Object prop );
}

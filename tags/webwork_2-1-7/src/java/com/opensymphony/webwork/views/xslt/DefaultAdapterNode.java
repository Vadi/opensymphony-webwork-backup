/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.LogFactory;
import org.w3c.dom.*;


/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 *         Date: 10.10.2003
 *         Time: 19:46:43
 */
public abstract class DefaultAdapterNode implements Node, AdapterNode {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final NodeList EMPTY_NODELIST = new NodeList() {
        public Node item(int i) {
            return null;
        }

        public int getLength() {
            return 0;
        }
    };


    //~ Instance fields ////////////////////////////////////////////////////////

    private AdapterNode parent;
    private DOMAdapter rootAdapter;
    private Object value;
    private String propertyName;

    //~ Constructors ///////////////////////////////////////////////////////////

    public DefaultAdapterNode(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        //assert rootAdapter != null : "rootAdapter == null";
        this.rootAdapter = rootAdapter;

        //assert parent != null : "parent == null";
        this.parent = parent;

        //assert propertyName != null : "propertyName == null";
        this.propertyName = propertyName;
        this.value = value;

        if (LogFactory.getLog(getClass()).isDebugEnabled()) {
            LogFactory.getLog(getClass()).debug("Creating " + this);
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public NamedNodeMap getAttributes() {
        return null;
    }

    public NodeList getChildNodes() {
        return EMPTY_NODELIST;
    }

    public Node getFirstChild() {
        return null;
    }

    public Node getLastChild() {
        return null;
    }

    public String getLocalName() {
        return null;
    }

    public String getNamespaceURI() {
        return null;
    }

    public Node getNextSibling() {
        return (getParentAdapterNode() != null) ? getParentAdapterNode().getNextSibling(this) : null;
    }

    public Node getNextSibling(AdapterNode child) {
        return null;
    }

    public void setNodeValue(String string) throws DOMException {
        operationNotSupported();
    }

    public String getNodeValue() throws DOMException {
        operationNotSupported();

        return null;
    }

    public Document getOwnerDocument() {
        return null;
    }

    public AdapterNode getParentAdapterNode() {
        return parent;
    }

    public Node getParentNode() {
        return parent;
    }

    public void setPrefix(String string) throws DOMException {
        operationNotSupported();
    }

    public String getPrefix() {
        return null;
    }

    public Node getPreviousSibling() {
        return null;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public DOMAdapter getRootAdapter() {
        return rootAdapter;
    }

    public boolean isSupported(String string, String string1) {
        operationNotSupported();

        return false;
    }

    public Object getValue() {
        return value;
    }

    public Node appendChild(Node node) throws DOMException {
        operationNotSupported();

        return null;
    }

    public Node cloneNode(boolean b) {
        operationNotSupported();

        return null;
    }

    public boolean equals(Object other) {
        try {
            AdapterNode otherNode = (AdapterNode) other;
            boolean result = true;
            result &= getRootAdapter().equals(otherNode.getRootAdapter());
            result &= getPropertyName().equals(otherNode.getPropertyName());
            result &= ((getValue() != null) ? getValue().equals(otherNode.getValue()) : (otherNode.getValue() == null));
            result &= ((getParentAdapterNode() != null) ? getParentAdapterNode().equals(otherNode.getParentAdapterNode()) : (otherNode.getParentAdapterNode() == null));

            return result;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public boolean hasAttributes() {
        return false;
    }

    public boolean hasChildNodes() {
        return false;
    }

    public int hashCode() {
        return (getRootAdapter().hashCode() * 37) + ((getParentAdapterNode() != null) ? (getParentAdapterNode().hashCode() * 41) : 0) + (getPropertyName().hashCode() * 43) + ((getValue() != null) ? (getValue().hashCode() * 47) : 0);
    }

    public Node insertBefore(Node node, Node node1) throws DOMException {
        operationNotSupported();

        return null;
    }

    public void normalize() {
        operationNotSupported();
    }

    public Node removeChild(Node node) throws DOMException {
        operationNotSupported();

        return null;
    }

    public Node replaceChild(Node node, Node node1) throws DOMException {
        operationNotSupported();

        return null;
    }

    protected void operationNotSupported() {
        throw new RuntimeException("Operation not supported.");
    }
}

package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Document;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * Date: 10.10.2003
 * Time: 19:46:43
 */
public abstract class DefaultAdapterNode implements Node, AdapterNode {

    private DOMAdapter rootAdapter;
    private AdapterNode parent;
    private String propertyName;
    private Object value;

    private static final NodeList EMPTY_NODELIST = new NodeList() {
        public Node item(int i) {
            return null;
        }

        public int getLength() {
            return 0;
        }
    };

    public DefaultAdapterNode(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        //assert rootAdapter != null : "rootAdapter == null";
        this.rootAdapter = rootAdapter;
        //assert parent != null : "parent == null";
        this.parent = parent;
        //assert propertyName != null : "propertyName == null";
        this.propertyName = propertyName;
        this.value = value;
        LogFactory.getLog(getClass()).debug("Creating " + this);
    }

    public boolean equals(Object other) {
        try {
            AdapterNode otherNode = (AdapterNode) other;
            boolean result = true;
            result &= getRootAdapter().equals(otherNode.getRootAdapter());
            result &= getPropertyName().equals(otherNode.getPropertyName());
            result &= (getValue() != null ?
                       getValue().equals(otherNode.getValue()) : otherNode.getValue() == null);
            result &= (getParentAdapterNode() != null ?
                       getParentAdapterNode().equals(otherNode.getParentAdapterNode()) : otherNode.getParentAdapterNode() == null);
            return result;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return getRootAdapter().hashCode() * 37 +
               (getParentAdapterNode() != null ? getParentAdapterNode().hashCode() * 41 : 0) +
               getPropertyName().hashCode() * 43 +
               (getValue() != null ? getValue().hashCode() * 47 : 0);
    }

    public DOMAdapter getRootAdapter() {
        return rootAdapter;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getValue() {
        return value;
    }

    protected void operationNotSupported() {
        throw new RuntimeException("Operation not supported.");
    }

    public String getNodeValue() throws DOMException {
        operationNotSupported();
        return null;
    }

    public void setNodeValue(String string) throws DOMException {
        operationNotSupported();
    }

    public Node getParentNode() {
        return parent;
    }


    public NamedNodeMap getAttributes() {
        return null;
    }

    public Document getOwnerDocument() {
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

    public Node getPreviousSibling() {
        return null;
    }

    public Node getNextSibling() {
        return getParentAdapterNode() != null ? getParentAdapterNode().getNextSibling(this) : null;
    }

    public Node getNextSibling(AdapterNode child) {
        return null;
    }

    public AdapterNode getParentAdapterNode() {
        return parent;
    }

    public Node insertBefore(Node node, Node node1) throws DOMException {
        operationNotSupported();
        return null;
    }

    public Node replaceChild(Node node, Node node1) throws DOMException {
        operationNotSupported();
        return null;
    }

    public Node removeChild(Node node) throws DOMException {
        operationNotSupported();
        return null;
    }

    public Node appendChild(Node node) throws DOMException {
        operationNotSupported();
        return null;
    }

    public boolean hasChildNodes() {
        return false;
    }

    public Node cloneNode(boolean b) {
        operationNotSupported();
        return null;
    }

    public void normalize() {
        operationNotSupported();
    }

    public boolean isSupported(String string, String string1) {
        operationNotSupported();
        return false;
    }

    public String getNamespaceURI() {
        return null;
    }

    public String getPrefix() {
        return null;
    }

    public void setPrefix(String string) throws DOMException {
        operationNotSupported();
    }

    public String getLocalName() {
        return null;
    }

    public boolean hasAttributes() {
        return false;
    }
}

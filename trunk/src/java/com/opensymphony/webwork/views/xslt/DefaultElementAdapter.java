/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 *         Date: 14.10.2003
 *         Time: 19:01:02
 */
public abstract class DefaultElementAdapter extends DefaultAdapterNode implements AdapterNode, Element {
    //~ Instance fields ////////////////////////////////////////////////////////

    private List adapters;

    //~ Constructors ///////////////////////////////////////////////////////////

    public DefaultElementAdapter(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        super(rootAdapter, parent, propertyName, value);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * ***********************************************************************************
     * Not supported below
     * ************************************************************************************
     */

    //
    public void setAttribute(String string, String string1) throws DOMException {
        operationNotSupported();
    }

    /**
     * ***********************************************************************************
     * No attributes, return empty attributes if asked.
     * ************************************************************************************
     */
    public String getAttribute(String string) {
        return "";
    }

    public void setAttributeNS(String string, String string1, String string2) throws DOMException {
        operationNotSupported();
    }

    public String getAttributeNS(String string, String string1) {
        return null;
    }

    public Attr setAttributeNode(Attr attr) throws DOMException {
        operationNotSupported();

        return null;
    }

    public Attr getAttributeNode(String string) {
        return null;
    }

    public Attr setAttributeNodeNS(Attr attr) throws DOMException {
        operationNotSupported();

        return null;
    }

    public Attr getAttributeNodeNS(String string, String string1) {
        operationNotSupported();

        return null;
    }

    public NodeList getChildNodes() {
        return getElementsByTagName("*");
    }

    public NodeList getElementsByTagName(String tagName) {
        initChildrenIfNessecary();

        if (tagName.equals("*")) {
            return new CollectionNodeList(getAdapters());
        } else {
            LinkedList filteredChildren = new LinkedList();

            for (Iterator i = getAdapters().iterator(); i.hasNext();) {
                AdapterNode adapterNode = (AdapterNode) i.next();

                if (adapterNode.getNodeName().equals(tagName)) {
                    filteredChildren.add(adapterNode);
                }
            }

            return new CollectionNodeList(filteredChildren);
        }
    }

    public NodeList getElementsByTagNameNS(String string, String string1) {
        return null;
    }

    public Node getFirstChild() {
        return (getChildNodes().getLength() > 0) ? getChildNodes().item(0) : null;
    }

    public Node getLastChild() {
        return (getChildNodes().getLength() > 0) ? getChildNodes().item(getChildNodes().getLength() - 1) : null;
    }

    public Node getNextSibling(AdapterNode child) {
        int index = getAdapters().indexOf(child);

        if (index < 0) {
            throw new RuntimeException(child + " is no child of " + this);
        }

        int siblingIndex = index + 1;
        Node sibling = ((0 < siblingIndex) && (siblingIndex < getAdapters().size())) ? ((Node) getAdapters().get(siblingIndex)) : null;

        return sibling;
    }

    public String getNodeName() {
        return getTagName();
    }

    public short getNodeType() {
        return Node.ELEMENT_NODE;
    }

    public String getTagName() {
        return getPropertyName();
    }

    public boolean hasAttribute(String string) {
        return false;
    }

    public boolean hasAttributeNS(String string, String string1) {
        return false;
    }

    public boolean hasChildNodes() {
        return getElementsByTagName("*").getLength() > 0;
    }

    public void removeAttribute(String string) throws DOMException {
        operationNotSupported();
    }

    public void removeAttributeNS(String string, String string1) throws DOMException {
        operationNotSupported();
    }

    public Attr removeAttributeNode(Attr attr) throws DOMException {
        operationNotSupported();

        return null;
    }

    protected List getAdapters() {
        initChildrenIfNessecary();

        return adapters;
    }

    protected abstract List buildChildrenAdapters();

    protected void initChildrenIfNessecary() {
        if (adapters == null) {
            adapters = new ArrayList();

            synchronized (adapters) {
                adapters = buildChildrenAdapters();
            }
        }
    }
    
    
    /* (non-Javadoc)
     * @see org.w3c.dom.Node#getBaseURI()
     */
    public String getBaseURI() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Node#compareDocumentPosition(org.w3c.dom.Node)
     */
    public short compareDocumentPosition(Node other) throws DOMException {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Node#getTextContent()
     */
    public String getTextContent() throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Node#setTextContent(java.lang.String)
     */
    public void setTextContent(String textContent) throws DOMException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Node#isSameNode(org.w3c.dom.Node)
     */
    public boolean isSameNode(Node other) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Node#lookupPrefix(java.lang.String)
     */
    public String lookupPrefix(String namespaceURI) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Node#isDefaultNamespace(java.lang.String)
     */
    public boolean isDefaultNamespace(String namespaceURI) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Node#lookupNamespaceURI(java.lang.String)
     */
    public String lookupNamespaceURI(String prefix) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Node#isEqualNode(org.w3c.dom.Node)
     */
    public boolean isEqualNode(Node arg) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Node#getFeature(java.lang.String, java.lang.String)
     */
    public Object getFeature(String feature, String version) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Node#setUserData(java.lang.String, java.lang.Object, org.w3c.dom.UserDataHandler)
     */
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Node#getUserData(java.lang.String)
     */
    public Object getUserData(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Element#getSchemaTypeInfo()
     */
    public TypeInfo getSchemaTypeInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Element#setIdAttribute(java.lang.String, boolean)
     */
    public void setIdAttribute(String name, boolean isId) throws DOMException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Element#setIdAttributeNS(java.lang.String, java.lang.String, boolean)
     */
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.Element#setIdAttributeNode(org.w3c.dom.Attr, boolean)
     */
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
        // TODO Auto-generated method stub
        
    }

    
    
    
    
}

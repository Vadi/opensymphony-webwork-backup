package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * Date: 14.10.2003
 * Time: 19:01:02
 */
public abstract class DefaultElementAdapter extends DefaultAdapterNode implements AdapterNode, Element {
    private List adapters;

    public DefaultElementAdapter(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        super(rootAdapter, parent, propertyName, value);
    }

    public short getNodeType() {
        return Node.ELEMENT_NODE;
    }

    public String getTagName() {
        return getPropertyName();
    }

    public String getNodeName() {
        return getTagName();
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

    protected void initChildrenIfNessecary() {
        if (adapters == null) {
            adapters = new ArrayList();
            synchronized (adapters) {
                adapters = buildChildrenAdapters();
            }
        }
    }

    public Node getNextSibling(AdapterNode child) {
        int index = getAdapters().indexOf(child);
        if (index < 0) {
            throw new RuntimeException(child + " is no child of " + this);
        }
        int siblingIndex = index+1;
        Node sibling = (0 < siblingIndex && siblingIndex < getAdapters().size()) ? ((Node) getAdapters().get(siblingIndex)) : null;
        return sibling;
    }


    public NodeList getElementsByTagNameNS(String string, String string1) {
        return null;
    }

    public NodeList getChildNodes() {
        return getElementsByTagName("*");
    }

    public Node getFirstChild() {
        return getChildNodes().getLength() > 0 ? getChildNodes().item(0) : null;
    }

    public Node getLastChild() {
        return getChildNodes().getLength() > 0 ? getChildNodes().item(getChildNodes().getLength() - 1) : null;
    }

    public boolean hasChildNodes() {
        return getElementsByTagName("*").getLength() > 0;
    }


    /**************************************************************************************
     * No attributes, return empty attributes if asked.
     **************************************************************************************/

    public String getAttribute(String string) {
        return "";
    }

    public Attr getAttributeNode(String string) {
        return null;
    }

    public String getAttributeNS(String string, String string1) {
        return null;
    }

    public Attr getAttributeNodeNS(String string, String string1) {
        operationNotSupported();
        return null;
    }

    public boolean hasAttribute
            (String
            string) {
        return false;
    }

    public boolean hasAttributeNS
            (String
            string, String
            string1) {
        return false;
    }

    /**************************************************************************************
     * Not supported below
     **************************************************************************************/

    //
    public void setAttribute
            (String
            string, String
            string1) throws DOMException {
        operationNotSupported();
    }

    public void removeAttribute
            (String
            string) throws DOMException {
        operationNotSupported();
    }

    public Attr setAttributeNode
            (Attr
            attr) throws DOMException {
        operationNotSupported();
        return null;
    }

    public Attr removeAttributeNode
            (Attr
            attr) throws DOMException {
        operationNotSupported();
        return null;
    }

    public void setAttributeNS(String string, String string1, String string2) throws DOMException {
        operationNotSupported();
    }

    public void removeAttributeNS(String string, String string1) throws DOMException {
        operationNotSupported();
    }

    public Attr setAttributeNodeNS(Attr attr) throws DOMException {
        operationNotSupported();
        return null;
    }

    protected List getAdapters() {
        initChildrenIfNessecary();
        return adapters;
    }

    protected abstract List buildChildrenAdapters();
}

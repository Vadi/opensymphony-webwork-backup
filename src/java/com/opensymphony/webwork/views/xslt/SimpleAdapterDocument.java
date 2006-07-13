/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.*;

import java.util.List;
import java.util.Arrays;

import com.opensymphony.webwork.WebWorkException;


/**
 * SimpleAdapterDocument adapted a Java object and presents it as
 * a Document.  This class represents the Document container and uses
 * the AdapterFactory to produce a child adapter for the wrapped object.
 * The adapter produced must be of an Element type or an exception is thrown.
 *
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * @author Pat Niemeyer (pat@pat.net)
 */
/*
	Note: in theory we could base this on AbstractAdapterElement and then allow
	the wrapped object to be a more general Node type.  We would just use
	ourselves as the root element.  However I don't think this is an issue as
	people expect Documents to wrap Elements.
*/
public class SimpleAdapterDocument extends AbstractAdapterNode implements Document {
    //~ Instance fields ////////////////////////////////////////////////////////

    private Element rootElement;

    //~ Constructors ///////////////////////////////////////////////////////////

    public SimpleAdapterDocument(
            AdapterFactory adapterFactory, AdapterNode parent, String propertyName, Object value) {
        setContext(adapterFactory, parent, propertyName, value);

    }

    public void setPropertyValue(Object prop) {
        super.setPropertyValue(prop);
        rootElement = null; // recreate the root element
    }

    /**
     * Lazily construct the root element adapter from the value object.
     */
    private Element getRootElement() {
        if (rootElement != null)
            return rootElement;

        Node node = getAdapterFactory().adaptNode(
                this, getPropertyName(), getPropertyValue());
        if (node instanceof Element)
            rootElement = (Element) node;
        else
            throw new WebWorkException(
                    "Document adapter expected to wrap an Element type.  Node is not an element:" + node);

        return rootElement;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    protected List getChildAdapters() {
        return Arrays.asList(new Object[]{getRootElement()});
    }

    public NodeList getChildNodes() {
        return new NodeList() {
            public Node item(int i) {
                return getRootElement();
            }

            public int getLength() {
                return 1;
            }
        };
    }

    public DocumentType getDoctype() {
        return null;
    }

    public Element getDocumentElement() {
        return getRootElement();
    }

    public Element getElementById(String string) {
        return null;
    }

    public NodeList getElementsByTagName(String string) {
        return null;
    }

    public NodeList getElementsByTagNameNS(String string, String string1) {
        return null;
    }

    public Node getFirstChild() {
        return getRootElement();
    }

    public DOMImplementation getImplementation() {
        return null;
    }

    public Node getLastChild() {
        return getRootElement();
    }

    public String getNodeName() {
        return "#document";
    }

    public short getNodeType() {
        return Node.DOCUMENT_NODE;
    }

    public Attr createAttribute(String string) throws DOMException {
        return null;
    }

    public Attr createAttributeNS(String string, String string1) throws DOMException {
        return null;
    }

    public CDATASection createCDATASection(String string) throws DOMException {
        return null;
    }

    public Comment createComment(String string) {
        return null;
    }

    public DocumentFragment createDocumentFragment() {
        return null;
    }

    public Element createElement(String string) throws DOMException {
        return null;
    }

    public Element createElementNS(String string, String string1) throws DOMException {
        return null;
    }

    public EntityReference createEntityReference(String string) throws DOMException {
        return null;
    }

    public ProcessingInstruction createProcessingInstruction(String string, String string1) throws DOMException {
        return null;
    }

    public Text createTextNode(String string) {
        return null;
    }

    public boolean hasChildNodes() {
        return true;
    }

    public Node importNode(Node node, boolean b) throws DOMException {
        return null;
    }

    public Node getChildAfter(Node child) {
        return null;
    }

    public Node getChildBefore(Node child) {
        return null;
    }

    // DOM level 3

    public String getInputEncoding() {
        throw operationNotSupported();
    }

    public String getXmlEncoding() {
        throw operationNotSupported();
    }

    public boolean getXmlStandalone() {
        throw operationNotSupported();
    }

    public void setXmlStandalone(boolean b) throws DOMException {
        throw operationNotSupported();
    }

    public String getXmlVersion() {
        throw operationNotSupported();
    }

    public void setXmlVersion(String string) throws DOMException {
        throw operationNotSupported();
    }

    public boolean getStrictErrorChecking() {
        throw operationNotSupported();
    }

    public void setStrictErrorChecking(boolean b) {
        throw operationNotSupported();
    }

    public String getDocumentURI() {
        throw operationNotSupported();
    }

    public void setDocumentURI(String string) {
        throw operationNotSupported();
    }

    public Node adoptNode(Node node) throws DOMException {
        throw operationNotSupported();
    }

    public DOMConfiguration getDomConfig() {
        throw operationNotSupported();
    }

    public void normalizeDocument() {
        throw operationNotSupported();
    }

    public Node renameNode(Node node, String string, String string1) throws DOMException {
        return null;
    }
    // end DOM level 3
}

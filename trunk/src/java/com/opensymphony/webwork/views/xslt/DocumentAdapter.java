/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.*;


/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 *         Date: 14.10.2003
 *         Time: 17:24:05
 */
public class DocumentAdapter extends DefaultAdapterNode implements Document {
    //~ Instance fields ////////////////////////////////////////////////////////

    private BeanAdapter rootElement;

    //~ Constructors ///////////////////////////////////////////////////////////

    public DocumentAdapter(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        super(rootAdapter, parent, propertyName, value);
        rootElement = new BeanAdapter(getRootAdapter(), this, getPropertyName(), getValue());
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public NodeList getChildNodes() {
        return new NodeList() {
            public Node item(int i) {
                return rootElement;
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
        return rootElement;
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
        return rootElement;
    }

    public DOMImplementation getImplementation() {
        return null;
    }

    public Node getLastChild() {
        return rootElement;
    }

    public Node getNextSibling(AdapterNode value) {
        return null;
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

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#getInputEncoding()
	 */
	public String getInputEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#getXmlEncoding()
	 */
	public String getXmlEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#getXmlStandalone()
	 */
	public boolean getXmlStandalone() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#setXmlStandalone(boolean)
	 */
	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#getXmlVersion()
	 */
	public String getXmlVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#setXmlVersion(java.lang.String)
	 */
	public void setXmlVersion(String xmlVersion) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#getStrictErrorChecking()
	 */
	public boolean getStrictErrorChecking() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#setStrictErrorChecking(boolean)
	 */
	public void setStrictErrorChecking(boolean strictErrorChecking) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#getDocumentURI()
	 */
	public String getDocumentURI() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#setDocumentURI(java.lang.String)
	 */
	public void setDocumentURI(String documentURI) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#adoptNode(org.w3c.dom.Node)
	 */
	public Node adoptNode(Node source) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#getDomConfig()
	 */
	public DOMConfiguration getDomConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#normalizeDocument()
	 */
	public void normalizeDocument() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Document#renameNode(org.w3c.dom.Node, java.lang.String, java.lang.String)
	 */
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
		// TODO Auto-generated method stub
		return null;
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
}

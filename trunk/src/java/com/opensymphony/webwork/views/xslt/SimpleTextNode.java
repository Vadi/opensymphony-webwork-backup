/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;


/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 *         Date: 10.10.2003
 *         Time: 19:45:12
 */
public class SimpleTextNode extends DefaultAdapterNode implements Text, AdapterNode {
    //~ Constructors ///////////////////////////////////////////////////////////

    public SimpleTextNode(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        super(rootAdapter, parent, propertyName, value);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setData(String string) throws DOMException {
        throw new RuntimeException("Operation not supported");
    }

    public String getData() throws DOMException {
        return getStringValue();
    }

    public int getLength() {
        return getStringValue().length();
    }

    public String getNodeName() {
        return "#text";
    }

    public short getNodeType() {
        return Node.TEXT_NODE;
    }

    public String getNodeValue() throws DOMException {
        return getStringValue();
    }

    public void appendData(String string) throws DOMException {
        throw new RuntimeException("Operation not supported");
    }

    public void deleteData(int i, int i1) throws DOMException {
        throw new RuntimeException("Operation not supported");
    }

    public void insertData(int i, String string) throws DOMException {
        throw new RuntimeException("Operation not supported");
    }

    public void replaceData(int i, int i1, String string) throws DOMException {
        throw new RuntimeException("Operation not supported");
    }

    public Text splitText(int i) throws DOMException {
        throw new RuntimeException("Operation not supported");
    }

    public String substringData(int beginIndex, int endIndex) throws DOMException {
        return getStringValue().substring(beginIndex, endIndex);
    }

    private String getStringValue() {
        return getValue().toString();
    }

	/* (non-Javadoc)
	 * @see org.w3c.dom.Text#isElementContentWhitespace()
	 */
	public boolean isElementContentWhitespace() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Text#getWholeText()
	 */
	public String getWholeText() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.Text#replaceWholeText(java.lang.String)
	 */
	public Text replaceWholeText(String content) throws DOMException {
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

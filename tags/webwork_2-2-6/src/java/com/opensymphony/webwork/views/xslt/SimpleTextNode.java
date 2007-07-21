/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import com.opensymphony.webwork.WebWorkException;


/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * @author Pat Niemeyer (pat@pat.net)
 */
public class SimpleTextNode extends AbstractAdapterNode implements Node, Text {
    //~ Constructors ///////////////////////////////////////////////////////////

    public SimpleTextNode(AdapterFactory rootAdapterFactory, AdapterNode parent, String propertyName, Object value) {
        setContext(rootAdapterFactory, parent, propertyName, value);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    protected String getStringValue() {
        return getPropertyValue().toString();
    }

    public void setData(String string) throws DOMException {
        throw new WebWorkException("Operation not supported");
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
        throw new WebWorkException("Operation not supported");
    }

    public void deleteData(int i, int i1) throws DOMException {
        throw new WebWorkException("Operation not supported");
    }

    public void insertData(int i, String string) throws DOMException {
        throw new WebWorkException("Operation not supported");
    }

    public void replaceData(int i, int i1, String string) throws DOMException {
        throw new WebWorkException("Operation not supported");
    }

    public Text splitText(int i) throws DOMException {
        throw new WebWorkException("Operation not supported");
    }

    public String substringData(int beginIndex, int endIndex) throws DOMException {
        return getStringValue().substring(beginIndex, endIndex);
    }

    // DOM level 3

    public boolean isElementContentWhitespace() {
        throw operationNotSupported();
    }

    public String getWholeText() {
        throw operationNotSupported();
    }

    public Text replaceWholeText(String string) throws DOMException {
        throw operationNotSupported();
    }
    // end DOM level 3

}

package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * Date: 10.10.2003
 * Time: 19:45:12
 */
public class SimpleTextNode extends DefaultAdapterNode implements Text, AdapterNode {
    public SimpleTextNode(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        super(rootAdapter, parent, propertyName, value);
    }

    public short getNodeType() {
        return Node.TEXT_NODE;
    }

    public String getNodeName() {
        return "#text";
    }

    public String getNodeValue() throws DOMException {
        return getStringValue();
    }

    public Text splitText(int i) throws DOMException {
        throw new RuntimeException("Operation not supported");
    }

    public String getData() throws DOMException {
        return getStringValue();
    }

    private String getStringValue() {
        return getValue().toString();
    }

    public void setData(String string) throws DOMException {
        throw new RuntimeException("Operation not supported");
    }

    public int getLength() {
        return getStringValue().length();
    }

    public String substringData(int beginIndex, int endIndex) throws DOMException {
        return getStringValue().substring(beginIndex, endIndex);
    }

    public void appendData(String string) throws DOMException {
        throw new RuntimeException("Operation not supported");
    }

    public void insertData(int i, String string) throws DOMException {
        throw new RuntimeException("Operation not supported");
    }

    public void deleteData(int i, int i1) throws DOMException {
        throw new RuntimeException("Operation not supported");

    }

    public void replaceData(int i, int i1, String string) throws DOMException {
        throw new RuntimeException("Operation not supported");
    }
}

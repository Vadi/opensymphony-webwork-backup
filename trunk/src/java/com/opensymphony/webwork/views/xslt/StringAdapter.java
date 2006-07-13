/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import com.opensymphony.util.XMLUtils;
import com.opensymphony.webwork.WebWorkException;

/**
 * StringAdapter adapts a Java String value to a DOM Element with the specified
 * property name containing the String's text.
 * e.g. a property <pre>String getFoo() { return "My Text!"; }</pre>
 * will appear in the result DOM as:
 * <foo>MyText!</foo>
 *
 * Subclasses may override the getStringValue() method in order to use StringAdapter
 * as a simplified custom XML adapter for Java types.  A subclass can enable XML
 * parsing of the value string via the setParseStringAsXML() method and then
 * override getStringValue() to return a String containing the custom formatted XML.
 *
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * @author Pat Niemeyer (pat@pat.net)
 */
public class StringAdapter extends AbstractAdapterElement {
    private Log log = LogFactory.getLog(this.getClass());
    boolean parseStringAsXML = false;

    //~ Constructors ///////////////////////////////////////////////////////////

    public StringAdapter() {
    }

    public StringAdapter(AdapterFactory adapterFactory, AdapterNode parent, String propertyName, String value) {
        setContext(adapterFactory, parent, propertyName, value);
    }

    /**
     * Get the object to be adapted as a String value.
     * <p/>
     * This method can be overridden by subclasses that wish to use StringAdapter
     * as a simplified customizable XML adapter for Java types. A subclass can
     * enable parsing of the value string as containing XML text via the
     * setParseStringAsXML() method and then override getStringValue() to return a
     * String containing the custom formatted XML.
     */
    protected String getStringValue() {
        return (String) getPropertyValue().toString();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    protected List buildChildAdapters() {
        Node node;
        if (getParseStringAsXML()) {
            log.debug("parsing string as xml: " + getStringValue());
            // Parse the String to a DOM, then proxy that as our child
            try {
                node = XMLUtils.parse(getStringValue());
            } catch (ParserConfigurationException e) {
                throw new WebWorkException(e);
            } catch (IOException e) {
                throw new WebWorkException(e);
            } catch (SAXException e) {
                throw new WebWorkException(e);
            }
            node = getAdapterFactory().proxyNode(this, node);
        } else {
            log.debug("using string as is: " + getStringValue());
            // Create a Text node as our child
            node = new SimpleTextNode(getAdapterFactory(), this, "text", getStringValue());
        }

        List children = new ArrayList();
        children.add(node);
        return children;
    }

    /**
     * Is this StringAdapter to interpret its string values as containing
     * XML Text?
     *
     * @see #setParseStringAsXML(boolean)
     */
    public boolean getParseStringAsXML() {
        return parseStringAsXML;
    }

    /**
     * When set to true the StringAdapter will interpret its String value
     * as containing XML text and parse it to a DOM Element.  The new DOM
     * Element will be a child of this String element. (i.e. wrapped in an
     * element of the property name specified for this StringAdapter).
     *
     * @param parseStringAsXML
     * @see #getParseStringAsXML()
     */
    public void setParseStringAsXML(boolean parseStringAsXML) {
        this.parseStringAsXML = parseStringAsXML;
    }

}

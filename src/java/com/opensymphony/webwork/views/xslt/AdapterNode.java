package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.Node;

/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * Date: 10.10.2003
 * Time: 19:41:49
 */
public interface AdapterNode extends Node {
    String getPropertyName();

    Node getNextSibling(AdapterNode child);

    DOMAdapter getRootAdapter();

    AdapterNode getParentAdapterNode();

    Object getValue();
}

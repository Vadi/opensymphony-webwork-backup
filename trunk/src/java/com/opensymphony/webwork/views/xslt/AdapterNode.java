/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.Node;


/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 *         Date: 10.10.2003
 *         Time: 19:41:49
 */
public interface AdapterNode extends Node {
    //~ Methods ////////////////////////////////////////////////////////////////

    Node getNextSibling(AdapterNode child);

    AdapterNode getParentAdapterNode();

    String getPropertyName();

    DOMAdapter getRootAdapter();

    Object getValue();
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;


/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 *         Date: 10.10.2003
 *         Time: 20:40:44
 */
public class SimpleNodeList implements NodeList {
    //~ Instance fields ////////////////////////////////////////////////////////
    private Log log = LogFactory.getLog(SimpleNodeList.class);

    private List nodes;

    //~ Constructors ///////////////////////////////////////////////////////////

    public SimpleNodeList(List nodes) {
        this.nodes = nodes;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public int getLength() {
        if (log.isTraceEnabled())
            log.trace("getLength: " + nodes.size());
        return nodes.size();
    }

    public Node item(int i) {
        log.trace("getItem: " + i);
        return (Node) nodes.get(i);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("SimpleNodeList: [");
        for (int i = 0; i < getLength(); i++)
            sb.append(item(i).getNodeName() + ",");
        sb.append("]");
        return sb.toString();
    }
}

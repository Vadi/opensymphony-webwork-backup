package com.opensymphony.webwork.views.xslt;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.util.List;

/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * Date: 10.10.2003
 * Time: 20:40:44
 */
public class CollectionNodeList implements NodeList {
    private List nodes;

    public CollectionNodeList(List nodes) {
        this.nodes = nodes;
    }

    public Node item(int i) {
        return (Node) nodes.get(i);
    }

    public int getLength() {
        return nodes.size();
    }
}

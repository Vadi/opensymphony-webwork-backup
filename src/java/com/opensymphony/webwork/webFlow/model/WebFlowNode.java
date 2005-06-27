package com.opensymphony.webwork.webFlow.model;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: plightbo
 * Date: Jun 26, 2005
 * Time: 4:49:14 PM
 */
public abstract class WebFlowNode implements Render {
    private SubGraph parent;
    private String name;
    private List links;

    public WebFlowNode(String name) {
        this.name = name;
        this.links = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public List getLinks() {
        return links;
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public void renderLinks(Writer writer) {
        for (Iterator iterator = links.iterator(); iterator.hasNext();) {
            Link link = (Link) iterator.next();

        }
    }
}

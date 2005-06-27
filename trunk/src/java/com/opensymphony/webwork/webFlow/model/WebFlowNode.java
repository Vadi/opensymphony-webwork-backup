package com.opensymphony.webwork.webFlow.model;

import java.io.IOException;
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
    private String name;
    private List links;
    private SubGraph parent;

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

    public SubGraph getParent() {
        return parent;
    }

    public void setParent(SubGraph parent) {
        this.parent = parent;
    }

    public void render(IndentWriter writer) throws IOException {
        String prefix = "";
        if (parent != null) {
            prefix = parent.getPrefix() + "_";
        }

        writer.write(prefix + name + getExt()
                + " [label=\"" + name + "\",color=\"" + getColor() + "\"];");
    }

    public abstract String getExt();

    public abstract String getColor();

    public void renderLinks(Writer writer) {
        for (Iterator iterator = links.iterator(); iterator.hasNext();) {
            Link link = (Link) iterator.next();

        }
    }
}

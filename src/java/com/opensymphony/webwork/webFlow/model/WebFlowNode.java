package com.opensymphony.webwork.webFlow.model;

import java.io.IOException;

/**
 * User: plightbo
 * Date: Jun 26, 2005
 * Time: 4:49:14 PM
 */
public abstract class WebFlowNode implements Render, Comparable {
    private String name;
    private SubGraph parent;

    public WebFlowNode(String name) {
        this.name = name;
    }

    public SubGraph getParent() {
        return parent;
    }

    public void setParent(SubGraph parent) {
        this.parent = parent;
    }

    public void render(IndentWriter writer) throws IOException {
        writer.write(getFullName() + " [label=\"" + name + "\",color=\"" + getColor() + "\"];");
    }

    public String getFullName() {
        String prefix = "";
        if (parent != null) {
            String parentPrefix = parent.getPrefix();
            if (!parentPrefix.equals("")) {
                prefix = parentPrefix + "_";
            }
        }
        return prefix + cleanName();
    }

    private String cleanName() {
        return name.replaceAll("[\\.\\/\\-\\$\\{\\}]", "_");
    }

    public abstract String getColor();

    public int compareTo(Object o) {
        return name.compareTo(((WebFlowNode) o).name);
    }
}

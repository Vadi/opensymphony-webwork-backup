package com.opensymphony.webwork.webFlow.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: plightbo
 * Date: Jun 26, 2005
 * Time: 4:53:57 PM
 */
public class SubGraph implements Render {
    private String name;
    private SubGraph parent;
    private List childSubGraphs;
    private List nodes;

    public SubGraph(String name) {
        this.name = name;
        this.childSubGraphs = new ArrayList();
        this.nodes = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void addChildSubGraph(SubGraph subGraph) {
        subGraph.setParent(this);
        childSubGraphs.add(subGraph);
    }

    public SubGraph getParent() {
        return parent;
    }

    public void setParent(SubGraph parent) {
        this.parent = parent;
    }

    public void addNode(WebFlowNode node) {
        node.setParent(this);
        nodes.add(node);
    }

    public void render(IndentWriter writer) throws IOException {
        // write the header
        writer.write("subgraph cluster_" + getPrefix() + " {", true);
        writer.write("color=grey;");
        writer.write("fontcolor=grey;");
        writer.write("label=\"" + name + "\";");

        IndentWriter iw = new IndentWriter(writer);

        // write out the subgraphs
        for (Iterator iterator = childSubGraphs.iterator(); iterator.hasNext();) {
            SubGraph subGraph = (SubGraph) iterator.next();
            subGraph.render(iw);
        }

        // write out the actions
        for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
            WebFlowNode webFlowNode = (WebFlowNode) iterator.next();
            webFlowNode.render(iw);
        }

        // .. footer
        writer.write("}", true);
    }

    public String getPrefix() {
        if (parent == null) {
            return name;
        } else {
            return parent.getPrefix() + "_" + name;
        }
    }

    public static SubGraph create(String namespace, Graph graph) {
        String[] parts = namespace.split("\\/");
        SubGraph last = null;
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.equals("")) {
                continue;
            }

            SubGraph subGraph = graph.findSubGraph(part);
            if (subGraph == null) {
                subGraph = new SubGraph(part);

                if (i == 1) {
                    graph.addSubGraph(subGraph);
                } else {
                    last.addChildSubGraph(subGraph);
                }
            }

            last = subGraph;
        }

        return last;
    }
}

package com.opensymphony.webwork.webFlow.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: plightbo
 * Date: Jun 26, 2005
 * Time: 4:58:30 PM
 */
public class Graph implements Render {
    private List subGraphs;
    private List nodes;

    public Graph() {
        this.subGraphs = new ArrayList();
        this.nodes = new ArrayList();
    }

    public void addSubGraph(SubGraph subGraph) {
        subGraphs.add(subGraph);
    }

    public void addNode(WebFlowNode node) {
        nodes.add(node);
    }

    public void render(IndentWriter writer) throws IOException {
        // write out the header
        writer.write("digraph mygraph {", true);
        writer.write("fontsize=10;");
        writer.write("fontname=helvetica;");
        writer.write("node [fontsize=10, fontname=helvetica, style=filled, shape=rectangle]");
        writer.write("edge [fontsize=10, fontname=helvetica]");

        IndentWriter iw = new IndentWriter(writer);

        // render all the subgraphs
        for (Iterator iterator = subGraphs.iterator(); iterator.hasNext();) {
            SubGraph subGraph = (SubGraph) iterator.next();
            subGraph.render(iw);
        }

        // render all the nodes
        for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
            WebFlowNode webFlowNode = (WebFlowNode) iterator.next();
            webFlowNode.render(writer);
        }

        // finally, render the links


        // and now the footer
        writer.write("}", true);
    }

    public SubGraph findSubGraph(String name) {
        for (Iterator iterator = subGraphs.iterator(); iterator.hasNext();) {
            SubGraph subGraph = (SubGraph) iterator.next();
            if (subGraph.getName().equals(name)) {
                return subGraph;
            }
        }

        return null;
    }
}

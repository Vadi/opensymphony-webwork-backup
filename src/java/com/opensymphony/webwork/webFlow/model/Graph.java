package com.opensymphony.webwork.webFlow.model;

import java.io.IOException;
import java.io.Writer;
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

    public void render(Writer writer) throws IOException {
        // write out the header
        writer.write("digraph mygraph {\n" +
                "  fontsize=10;\n" +
                "  fontname=helvetica;\n" +
                "  node [fontsize=10, fontname=helvetica, style=filled, shape=rectangle]\n" +
                "  edge [fontsize=10, fontname=helvetica]\n");

        // render all the subgraphs
        for (Iterator iterator = subGraphs.iterator(); iterator.hasNext();) {
            SubGraph subGraph = (SubGraph) iterator.next();
            subGraph.render(new IndentWriter(writer));
        }

        // render all the nodes
        for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
            WebFlowNode webFlowNode = (WebFlowNode) iterator.next();
            webFlowNode.render(writer);
        }

        // finally, render the links


        // and now the footer
        writer.write("}\n");
    }
}

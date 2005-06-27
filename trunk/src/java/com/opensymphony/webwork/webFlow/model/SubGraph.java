package com.opensymphony.webwork.webFlow.model;

import java.io.IOException;
import java.io.Writer;
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
    private List childSubGraphs;

    public SubGraph(String name) {
        this.name = name;
        this.childSubGraphs = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void addChildSubGraph(SubGraph subGraph) {
        childSubGraphs.add(subGraph);
    }

    public void render(Writer writer) throws IOException {
        // write the header
        writer.write("subgraph cluster_" + name + " {");
        writer.write("color=grey;");
        writer.write("fontcolor=grey;");
        writer.write("label=\"" + name + "\";");

        // write out the subgraphs
        for (Iterator iterator = childSubGraphs.iterator(); iterator.hasNext();) {
            SubGraph subGraph = (SubGraph) iterator.next();
            subGraph.render(new IndentWriter(writer));
        }

        // write out the actions
    }
}

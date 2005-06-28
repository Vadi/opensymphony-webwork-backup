package com.opensymphony.webwork.webFlow.model;

import java.io.IOException;
import java.util.*;

/**
 * User: plightbo
 * Date: Jun 26, 2005
 * Time: 4:58:30 PM
 */
public class Graph extends SubGraph {
    private Set links;
    public static Map nodeMap = new HashMap();

    public Graph() {
        super("");
        this.links = new HashSet();
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public void render(IndentWriter writer) throws IOException {
        // write out the header
        writer.write("digraph mygraph {", true);
        writer.write("fontsize=10;");
        writer.write("fontname=helvetica;");
        writer.write("node [fontsize=10, fontname=helvetica, style=filled, shape=rectangle]");
        writer.write("edge [fontsize=10, fontname=helvetica]");

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
        for (Iterator iterator = links.iterator(); iterator.hasNext();) {
            Link link = (Link) iterator.next();
            link.render(writer);
        }

        // and now the footer
        writer.write("}", true);
    }

    public WebFlowNode findNode(String location, WebFlowNode ref) {
        if (location.startsWith("/")) {
            location = location.substring(1);
        } else {
            // not absolute, so use the reference node
            String prefix = null;
            if (ref.getParent() != null) {
                prefix = ref.getParent().getPrefix();
                location = prefix + "_" + location;
            }
        }

        location = location.replaceAll("[\\.\\/\\-\\$\\{\\}]", "_");

        return (WebFlowNode) nodeMap.get(location);
    }
}

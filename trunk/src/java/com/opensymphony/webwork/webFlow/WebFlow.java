/*
 * Created on Aug 12, 2004 by mgreer
 */
package com.opensymphony.webwork.webFlow;

import com.opensymphony.util.FileUtils;
import com.opensymphony.webwork.webFlow.renderers.DOTRenderer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * // START SNIPPET: javadocs-intro
 * WebFlow is a tool that renders out GraphViz-generated images depicting your
 * WebWork-powered web application's flow.
 * // END SNIPPET: javadocs-intro
 * <p/>
 * // START SNIPPET: javadocs-api
 * If you wish to use WebFlow through its API...
 * // END SNIPPET: javadocs-api
 */
public class WebFlow {

    private static final Log LOG = LogFactory.getLog(WebFlow.class);

    private String configDir;
    private String views;
    private String output;
    private String namespace;
    private Writer writer;

    public WebFlow(String configDir, String views, String output, String namespace) {
        this.configDir = configDir;
        this.views = views;
        this.output = output;
        this.namespace = namespace;
    }

    public static void main(String[] args) {
        LOG.info("WebFlow starting...");

        if (args.length != 8 && args.length != 6) {
            URL resource = WebFlow.class.getResource("webflow-usage.txt");
            File file = null;
            try {
                file = new File(new URI(resource.toExternalForm()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String usage = FileUtils.readFile(file);
            System.out.println(usage.replaceAll("//.*\n", ""));
            return;
        }

        String configDir = getArg(args, "config");
        String views = getArg(args, "views");
        String output = getArg(args, "output");
        String namespace = getArg(args, "ns");

        WebFlow webFlow = new WebFlow(configDir, views, output, namespace);
        webFlow.prepare();
        webFlow.render();
    }

    private static String getArg(String[] args, String arg) {
        for (int i = 0; i < args.length; i++) {
            if (("-" + arg).equals(args[i]) && ((i + 1) < args.length)) {
                return args[i + 1];
            }
        }

        return "";
    }

    public void prepare() {
        if (writer == null) {
            try {
                writer = new FileWriter(output + "/out.dot");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        XWorkConfigRetriever.setConfiguration(configDir, views.split("[, ]+"));
        DOTRenderer renderer = new DOTRenderer(writer);
        renderer.render(namespace);
    }

    public void render() {
        try {
            Runtime.getRuntime().exec("dot -o" + output + "/out.gif -Tgif " + output + "/out.dot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}
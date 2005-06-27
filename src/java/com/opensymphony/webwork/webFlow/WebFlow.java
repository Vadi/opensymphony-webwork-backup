/*
 * Created on Aug 12, 2004 by mgreer
 */
package com.opensymphony.webwork.webFlow;

import com.opensymphony.webwork.webFlow.renderers.DOTRenderer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * TODO Describe WebFlow
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

        if (args.length != 8) {
            System.out.println("Usage: -config CONFIG_DIR -views VIEWS_DIRS -output OUTPUT -ns NAMESPACE");
            System.out.println("       CONFIG_DIR => a directory containing xwork.xml");
            System.out.println("       VIEWS_DIRS => comma seperated list of dirs containing JSPs, VMs, etc");
            System.out.println("       OUPUT      => the directory where the output should go");
            System.out.println("       NAMESPACE  => the namespace path restriction (/, /foo, etc)");
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
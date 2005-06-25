/*
 * Created on Aug 12, 2004 by mgreer
 */
package com.opensymphony.webwork.webFlow;

import com.opensymphony.webwork.webFlow.renderers.DOTRenderer;
import com.opensymphony.webwork.webFlow.renderers.Renderer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * TODO Describe WebFlow
 */
public class WebFlow {

    private static final Log LOG = LogFactory.getLog(WebFlow.class);

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

        XWorkConfigRetriever.setConfiguration(configDir, views.split("[, ]+"));
        Renderer renderer = new DOTRenderer(output);
        renderer.render(namespace);

        try {
            Runtime.getRuntime().exec("dot -o" + output + "/out.gif -Tgif " + output + "/out.dot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getArg(String[] args, String arg) {
        for (int i = 0; i < args.length; i++) {
            if (("-" + arg).equals(args[i]) && ((i + 1) < args.length)) {
                return args[i + 1];
            }
        }

        return "";
    }
}
/*
 * Created on Aug 12, 2004 by mgreer
 */
package com.opensymphony.webwork.webFlow;

import com.opensymphony.webwork.webFlow.renderers.DOTRenderer;
import com.opensymphony.webwork.webFlow.renderers.Renderer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO Describe WebFlow
 */
public class WebFlow {

    private static final Log LOG = LogFactory.getLog(WebFlow.class);

    public static void main(String[] args) {
        LOG.info("WebFlow starting...");
        String configFilePath = null;//"/Users/mgreer/Sandbox/martin2/"
        if (args.length > 0) {
            configFilePath = (String) args[0];
            LOG.info("configFilePath=" + configFilePath);
        }/*else{
            File file = new File("xwork.xml");
            configFilePath = file.getParent();
        }*/
        if (configFilePath != null) {
            XWorkConfigRetriever.setBasePath(configFilePath);
            Renderer renderer = new DOTRenderer();
            renderer.render();
        }
    }
}
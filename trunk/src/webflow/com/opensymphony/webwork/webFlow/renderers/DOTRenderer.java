/*
 * Created on Aug 12, 2004 by mgreer
 */
package com.opensymphony.webwork.webFlow.renderers;

import com.opensymphony.webwork.webFlow.XWorkConfigRetriever;
import com.opensymphony.webwork.webFlow.entities.View;
import com.opensymphony.xwork.config.entities.ActionConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * Renders flow diagram to the console at info level
 */
public class DOTRenderer implements Renderer {

    private static final Log LOG = LogFactory.getLog(DOTRenderer.class);

    /**
     * Default constructor
     */
    public DOTRenderer() {
    }

    public void render() {
        Set namespaces = XWorkConfigRetriever.getNamespaces();
        for (Iterator iter = namespaces.iterator(); iter.hasNext();) {
            String namespace = (String) iter.next();
            String fileName = "default";
            if (namespace.length() > 0)
                fileName = namespace.substring(1);
            LOG.info("fileName=" + fileName);
            File namespaceFile = new File(XWorkConfigRetriever.getBasePath() + fileName + ".dot");
            FileWriter out = null;
            try {
                out = new FileWriter(namespaceFile);
                out.write("graph " + fileName + "{\n");
                Set actionNames = XWorkConfigRetriever.getActionNames(namespace);
                for (Iterator iterator = actionNames.iterator(); iterator.hasNext();) {
                    String actionName = (String) iterator.next();
                    ActionConfig actionConfig = XWorkConfigRetriever.getActionConfig(namespace,
                            actionName);
                    //out.write( actionName + " -- " );
                    Set resultNames = actionConfig.getResults().keySet();
                    for (Iterator iterator2 = resultNames.iterator(); iterator2.hasNext();) {
                        String resultName = (String) iterator2.next();
                        View view = XWorkConfigRetriever
                                .getView(namespace, actionName, resultName);
                        if (resultName != "login" && resultName != "access" && view != null) {
                            //out.write( actionName + " -- " + view +" [label="+resultName+"];\n");
//							out.write( "\t\t-> " + resultName + "\t-> " + view + "\t-> "
//									+ view.getTargets() + "\n" );
                            Set targetActions = view.getTargets();
                            for (Iterator iterator3 = targetActions.iterator(); iterator3.hasNext();) {
                                String targetAction = (String) iterator3.next();
                                out.write(actionName + " -- " + targetAction + ";\n");
                            }
                        }
                    }
                }
                out.write("}");
                out.close();
            } catch (IOException e) {
                LOG.error("Error writing to " + namespace, e);
            }
        }
    }

}
package com.opensymphony.webwork.views.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;

import com.opensymphony.webwork.config.Configuration;

/**
 * <p>
 * Denali - A CarShare Reservation System developed by EngineGreen
 * Copyright 2003, EngineGreen.  All Rights Reserved.
 * </p>
 *
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class ToolContext extends VelocityContext {
    private static final Log log = LogFactory.getLog(VelocityManager.class);
    final private String TOOL_PREFIX = "webwork.velocity.tool.";

    Map toolMap = new HashMap();

    public ToolContext() {
        init();
    }

    protected void init() {
        for (Iterator iterator = Configuration.list(); iterator.hasNext();) {
            String key = iterator.next().toString();
            if (key.startsWith(TOOL_PREFIX)) {
                addTool(key.substring(TOOL_PREFIX.length()), Configuration.get(key).toString());
            }
        }
    }

    protected void addTool(String key, String classname) {
        Object tool = null;
        try {
             tool = Class.forName(classname).newInstance();
        } catch (Exception e) {
            tool = classname;
        }

        log.info("Registering tool, " + classname + " [" + tool.getClass().getName() + "], as " + key);
        toolMap.put(key, tool);
    }

    public boolean internalContainsKey(Object key) {
        System.out.println("checking to see if we contain the key, " + key);
        System.out.println("we actual contain -- " + toolMap);
        return toolMap.containsKey(key);
    }

    public Object internalGet(String key) {
        return toolMap.get(key);
    }

    public Object internalPut(String s, Object o) {
        // do nothing
        return null;
    }
}

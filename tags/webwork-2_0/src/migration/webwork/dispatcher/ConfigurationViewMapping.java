/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.dispatcher;

import webwork.action.Action;
import com.opensymphony.webwork.config.Configuration;

/**
 * View mapping that uses the Configuration. This maps {action name, view name} tuples to views, e.g. JSP files.
 *
 * The algorithm first queries the configuration for the actionName.viewName entry. If found, then that is used.
 * If not found, then parts of the action name is removed until a match is found. For example, if there is a
 * configuration entry for "foo.success", and the action name is "foo.bar" and the view is "success", then
 * there will be a miss when looking for "foo.bar.success" but a hit for "foo.success", which will thus be used.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class ConfigurationViewMapping implements ViewMapping {
    // ViewMapping implementation ------------------------------------
    /**
     * Get view corresponding to given action and view names
     */
    public Object getView(String anActionName, String aViewName) {
        String actionName = anActionName;

        String command = null;
        int idx;
        // Check if the actionName ends with a command specification
        if ((idx = actionName.lastIndexOf("!")) != -1) {
            command = actionName.substring(idx + 1);
            actionName = actionName.substring(0, idx);
        }
      
        // Enter a loop to check for mappings from actionName. If no mapping is found
        // the last part of the actionName is stripped off and a new check is made
        while (true) {
            // Get the index 
            idx = actionName.lastIndexOf('.');
            try {
                Object returnValue = null;
                if (command != null) {
                    returnValue = Configuration.get(actionName + "!" + command + "." + aViewName);
                } else {
                    returnValue = Configuration.get(actionName + "." + aViewName);
                }
            
                // If we get here we found a view. Just return it then
                return returnValue;
            } catch (IllegalArgumentException e) {
                // This happens if a mapping for the action and view cannot be
                // found in the Configuration object
                if (command != null) {
                    // If we tried with a command before, then try a lookup without
                    // the command this time.
                    try {
                        return Configuration.get(actionName + "." + aViewName);
                    } catch (IllegalArgumentException e1) {
                        // Ignore, since the code below will handle it
                    }
                }
                // No mapping could be found, with or without command
                // Now try again but this time strip off the last part of the actionName
                if (idx == -1)
                    break;
                actionName = actionName.substring(0, idx);
            }
        }

        // No view with given name for specified action
        try {
            // Try without action
            // This is for defaults, and is mostly useful for standard error views
            return Configuration.get(aViewName);
        } catch (IllegalArgumentException ex) {
            // No view found
            if (aViewName.equals(Action.NONE)) {
                return null;
            }
            if (aViewName.equals(Action.INPUT)) {
                try {
                    return getView(anActionName, Action.ERROR);
                } catch (IllegalArgumentException ex2) {
                    throw new IllegalArgumentException("No 'input' view found for action '" + anActionName + "'");
                }
            }
            throw new IllegalArgumentException("No '" + aViewName + "' view found for action '" + anActionName + "'");
        }
    }
}

/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.factory;

import com.opensymphony.webwork.config.Configuration;
import org.apache.commons.logging.LogFactory;
import webwork.action.Action;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Locate an action based on the configured list of package prefixes.
 * The configuration property <code>webwork.action.packages</code> is used
 * to specify a comma separated list of packages that should be considered
 * as prefixes.  The default package prefix list is: <code>webwork.action.test</code>
 * and <code>webwork.action.standard</code>.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class PrefixActionFactoryProxy
        extends ActionFactoryProxy {
    // Attributes ----------------------------------------------------
    static String actionPrefix = "webwork.action.test,webwork.action.standard";
    String[] actionPrefixes;

    Map actionNames = new Hashtable();

    // Constructors --------------------------------------------------
    public PrefixActionFactoryProxy(ActionFactory aFactory) {
        super(aFactory);

        // Get list of prefixes to try
        try {
            actionPrefix += "," + (Configuration.getString("webwork.action.packages")).trim();
        } catch (IllegalArgumentException e) {
            LogFactory.getLog(this.getClass()).info("Error loading action prefixes. Only using default prefixes:" + actionPrefix);
        }

        StringTokenizer tokens = new StringTokenizer(actionPrefix, ", ");
        ArrayList prefixes = new ArrayList();
        while (tokens.hasMoreTokens()) {
            prefixes.add(tokens.nextToken());
        }
        actionPrefixes = new String[prefixes.size()];
        //Category.getInstance(this.getClass()).debug(prefixes.toString());
        prefixes.toArray(actionPrefixes);
    }

    // ActionFactory overrides ---------------------------------------
    /**
     * Returns an action class instance after searching through a list of
     * package prefixes from the configuration properties.  If no action
     * was found, get the action from the action factory proxy chain.
     *
     * @param aName
     * @return action whose name may be prefixed by this factory
     * @throws Exception
     */
    public Action getActionImpl(String aName)
            throws Exception {
        // Check cache
        String actionName = (String) actionNames.get(aName);

        // Find class
        if (actionName == null) {
            Action action = null;
            for (int i = 0; i < actionPrefixes.length; i++) {
                try {
                    action = getNextFactory().getActionImpl(actionPrefixes[i] + "." + aName);
                    if (action != null) {
                        // Put in cache
                        actionNames.put(aName, actionPrefixes[i] + "." + aName);
                        break;
                    }
                } catch (Exception e) {
                    // Not found with this prefix
                }
            }

            // Try without prefix
            if (action == null) {
                action = getNextFactory().getActionImpl(aName);
                // Put in cache
                actionNames.put(aName, aName);
            }

            // Return the action
            return action;
        } else {
            return getNextFactory().getActionImpl(actionName);
        }
    }
}

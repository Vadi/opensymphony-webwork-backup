/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.factory;

import webwork.action.Action;
import webwork.action.standard.Script;

/**
 * Obtains the Script action if the suffix is a supported scripting suffix.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class ScriptActionFactoryProxy
        extends ActionFactoryProxy {
    // Attributes ----------------------------------------------------

    // Constructors --------------------------------------------------
    public ScriptActionFactoryProxy(ActionFactory aFactory) {
        super(aFactory);
    }

    // ActionFactory overrides ---------------------------------------
    /**
     * If the suffix of the action is a scripting extension, get the Script
     * action and then load the script.  Currently, <code>".js"</code>
     * is the only suffix supported.
     *
     * @param aName
     * @return the script action or an action corresponding to the given name
     * @throws Exception
     */
    public Action getActionImpl(String aName)
            throws Exception {
        // Check for scripting extension
        if (aName.endsWith(".js")) {
            Script script = (Script) ActionFactory.getAction("Script");
            script.setScript(aName);

            if (script.getScriptURL() == null)
                throw new IllegalArgumentException("Script '" + aName + "' does not exist");

            return script;
        } else {
            return getNextFactory().getActionImpl(aName);
        }
    }
}

/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.factory;

import webwork.action.Action;
import webwork.action.CommandDriven;
import com.opensymphony.xwork.ActionContext;

/**
 * Executes a "command" within an action specified either as part of the
 * action name or as a parameter value.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class CommandActionFactoryProxy
        extends ActionFactoryProxy {
    // Attributes ----------------------------------------------------

    // Constructors --------------------------------------------------
    public CommandActionFactoryProxy(ActionFactory aFactory) {
        super(aFactory);
    }

    // ActionFactory overrides ---------------------------------------
    /**
     * Locates the matching action object from the action factory proxy chain
     * and then executes a command on it if the {@link CommandDriven} interface
     * is implemented.  The command is determined either by using the text
     * after the <code>"!"</code> in the action name or from the value of the
     * first parameter named
     * <code>"command."</code>
     *
     * @param  aName
     * @return action from the next action factory proxy
     * @exception  Exception
     */
    public Action getActionImpl(String aName)
            throws Exception {
        // Set command that was used as part of action name
        int idx = aName.lastIndexOf("!");
        if (idx != -1) {
            String command = aName.substring(idx + 1);
            String actionName = aName.substring(0, idx);
            Action action = getNextFactory().getActionImpl(actionName);
            if (action instanceof CommandDriven) {
                ((CommandDriven) action).setCommand(command);
            }

            return action;
        }

        Action action = getNextFactory().getActionImpl(aName);
        if (action instanceof CommandDriven) {
            // Set command that was sent in as a parameter
            String[] commandParam = ((String[]) ActionContext.getContext().getParameters().get("command"));
            if (commandParam != null) {
                ((CommandDriven) action).setCommand(commandParam[0]);
            }
        }

        return action;
    }
}

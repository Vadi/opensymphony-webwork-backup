/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.factory;

import webwork.action.*;

import java.util.Map;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.webwork.ServletActionContext;

/**
 * Initializes an action for each implemented <code>*Aware</code> interface
 * after first locating the action through the action factory proxy chain.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class ContextActionFactoryProxy
        extends ActionFactoryProxy {
    // Attributes ----------------------------------------------------
    Map applicationMap;

    // Constructors --------------------------------------------------
    public ContextActionFactoryProxy(ActionFactory aFactory) {
        super(aFactory);
    }

    // ActionFactory implementation -------------------------------
    public Action getActionImpl(String aName)
            throws Exception {
        // Get action from factory
        Action action = getNextFactory().getActionImpl(aName);

        // Give action contextual info
        setActionContext(action);

        // Return action
        return action;
    }

    // Protected -----------------------------------------------------
    /**
     * Initializes the specified action object for each <code>*Aware</code>
     * interface it implements.  The following interfaces are supported and
     * initialized in the this order:
     * <ul>
     *   <li>{@link ServletRequestAware}</li>
     *   <li>{@link ServletResponseAware}</li>
     *   <li>{@link SessionAware}</li>
     *   <li>{@link ApplicationAware}</li>
     *   <li>{@link ParameterAware}</li>
     *   <li>{@link com.opensymphony.xwork.LocaleAware}</li>
     * </ul>
     */
    protected void setActionContext(Object anAction) {
        ActionContext context = ActionContext.getContext();

        // Check if servlet aware action
        if (anAction instanceof ServletRequestAware) {
            //LogFactory.getLog(this.getClass()).debug("Action is servlet request aware");

            ((ServletRequestAware) anAction).setServletRequest(ServletActionContext.getRequest());
        }

        // Check if servlet aware action
        if (anAction instanceof ServletResponseAware) {
            //Category.getInstance(this.getClass()).debug("Action is servlet response aware");

            ((ServletResponseAware) anAction).setServletResponse(ServletActionContext.getResponse());
        }

        // Check if session aware action
        if (anAction instanceof SessionAware) {
            //Category.getInstance(this.getClass()).debug("Action is session aware");
            ((SessionAware) anAction).setSession(context.getSession());
        }

        // Check if application aware action
        if (anAction instanceof ApplicationAware) {
            //Category.getInstance(this.getClass()).debug("Action is application aware");
            ((ApplicationAware) anAction).setApplication(context.getApplication());
        }

        // Check if parameter aware action
        if (anAction instanceof ParameterAware) {
            //Category.getInstance(this.getClass()).debug("Action is parameter aware");

            ((ParameterAware) anAction).setParameters(context.getParameters());
        }

        // Check if locale aware action
        if (anAction instanceof LocaleAware) {
            //Category.getInstance(this.getClass()).debug("Action is locale aware");

            ((LocaleAware) anAction).setLocale(context.getLocale());
        }
    }

}

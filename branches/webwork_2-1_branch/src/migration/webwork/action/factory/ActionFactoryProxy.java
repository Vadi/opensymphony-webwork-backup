/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.factory;


/**
 * Extends ActionFactory to provide proxy support.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public abstract class ActionFactoryProxy
        extends ActionFactory {
    // Attributes ----------------------------------------------------
    private ActionFactory nextFactory;

    // Constructors --------------------------------------------------
    /**
     * Initialize the proxy with the previous proxy in the delegation chain.
     *
     * @param aFactory next action factory proxy
     */
    protected ActionFactoryProxy(ActionFactory aFactory) {
        nextFactory = aFactory;
    }

    // Protected -----------------------------------------------------
    /**
     * Returns the next action factory proxy in the delegation chain.
     *
     * @return next action factory proxy
     */
    protected ActionFactory getNextFactory() {
        return nextFactory;
    }
}

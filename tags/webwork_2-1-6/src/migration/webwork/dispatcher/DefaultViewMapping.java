/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.dispatcher;

/**
 * Default view mapping.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class DefaultViewMapping
        implements ViewMapping {
    ViewMapping delegate;

    public DefaultViewMapping() {
        delegate = new ConfigurationViewMapping();
        delegate = new CachingViewMapping(delegate);
        delegate = new DynamicViewMapping(delegate);
    }

    // ViewMapping implementation ------------------------------------
    /**
     * Get view corresponding to given action and view names
     */
    public Object getView(String anActionName, String aViewName) {
        return delegate.getView(anActionName, aViewName);
    }
}

/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.dispatcher;

/**
 * Implementations of this interface provide a mapping from action+result
 * to a particular view that should be used to present the results to the
 * end user.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public interface ViewMapping {
    // Public ---------------------------------------------------------
    /**
     * Get view corresponding to given action and view names.
     */
    public Object getView(String anActionName, String aViewName);
}

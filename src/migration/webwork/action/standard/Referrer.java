/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.standard;

import webwork.action.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * Redirect to the page that referred this action invocation.
 * <p/>
 * This action is useful to set as result of an action which
 * perform some update of state on a page (such as removing or updating data).
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class Referrer
        extends Redirect
        implements ServletRequestAware {
    // Attributes ----------------------------------------------------
    HttpServletRequest request;

    // Implements ServletRequestAware --------------------------------
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    // Action implementation -----------------------------------------
    /**
     * Redirect to URL
     */
    protected String doExecute()
            throws Exception {
        setUrl(request.getHeader("referer"));

        return super.doExecute();
    }
}


/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.standard;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import webwork.action.Action;
import com.opensymphony.webwork.ServletActionContext;

/**
 *	Set the response status to 204 NO_CONTENT.
 * This tells the browser to not update the current page.
 * This can be useful if you have a form that you want to submit
 * but you do not want to reload the whole page just because of that.
 * You can then use some client side Javascript to notify the
 * user that the form was submitted.
 *
 *	@author Dick Zetterberg (dick@transitor.se)
 *	@version $Revision$
 */
public class NoContent implements Action {
    protected static Log log = LogFactory.getLog(NoContent.class);

    public String execute() {
        log.debug("NoContent action setting HTTP response to 204");
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        // Return NONE so that no view is used by the dispatcher
        return NONE;
    }
}


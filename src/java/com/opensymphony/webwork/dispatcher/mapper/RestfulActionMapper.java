package com.opensymphony.webwork.dispatcher.mapper;

import javax.servlet.http.HttpServletRequest;

/**
 * An action mapper that follows a REST-ful approach (see the original article on REST
 * <a href="http://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm">here</a>). This
 * mapper is not truly REST-ful because it does does not actually take advantage of PUT,
 * POST, GET, DELETE; but rather uses nice URL namespacing and unique rules to create a
 * simple URL scheme for your application.
 * <p/>
 * The rules of this action mapper are:
 * - If only a namespace is provided, the action is assumed to be
 * "list". Ex: /people
 * - If only a parameter is provided, the action is assumed to be
 * "get". Ex: /people/1
 * - Ex. /people/1/address
 *
 * @author Patrick Lightbody
 */
public class RestfulActionMapper implements ActionMapper {
    public ActionMapping getMapping(HttpServletRequest request) {
        return null;
    }

    public String getUriFromActionMapping(ActionMapping mapping) {
        return null;
    }
}

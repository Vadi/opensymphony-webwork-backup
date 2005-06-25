package com.opensymphony.webwork.dispatcher.mapper;

import javax.servlet.http.HttpServletRequest;

/**
 * User: plightbo
 * Date: Jun 20, 2005
 * Time: 6:56:53 PM
 */
public interface ActionMapper {
    ActionMapping getMapping(HttpServletRequest request);

    String getUriFromActionMapping(ActionMapping mapping);
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import javax.servlet.http.HttpServletResponse;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public interface HttpServletResponseAware {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void setHttpServletResponse(HttpServletResponse response);
}

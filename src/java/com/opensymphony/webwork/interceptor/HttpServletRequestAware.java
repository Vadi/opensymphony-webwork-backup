/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import javax.servlet.http.HttpServletRequest;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public interface HttpServletRequestAware {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void setHttpServletRequest(HttpServletRequest request);
}

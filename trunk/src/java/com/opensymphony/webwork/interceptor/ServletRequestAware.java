/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import javax.servlet.http.HttpServletRequest;


/**
 * All Actions that want to have access to the servlet request
 * objects must implement this interface.
 *
 * This interface is only relevant if the Action is used in a servlet
 * environment.
 *
 * Note that using this interface makes the Action tied to a servlet
 * environment, so it should be avoided if possible.
 *
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 */

public interface ServletRequestAware {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void setServletRequest(HttpServletRequest request);
}

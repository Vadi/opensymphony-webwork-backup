/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import java.util.Map;


/**
 *        Actions that want access to the users session should implement this interface.
 *
 *        @author Rickard Öberg (rickard@middleware-company.com)
 *        @version $Revision$
 */
public interface SessionAware {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void setSession(Map session);
}

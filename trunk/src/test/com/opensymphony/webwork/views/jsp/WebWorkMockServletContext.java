/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockServletContext;


/**
 * WebWorkMockServletContext
 * @author Jason Carreira
 * Created Jun 6, 2003 12:32:10 AM
 */
public class WebWorkMockServletContext extends MockServletContext {
    //~ Instance fields ////////////////////////////////////////////////////////

    String realPath;

    //~ Methods ////////////////////////////////////////////////////////////////

    public String getRealPath(String string) {
        return realPath;
    }

    public void setupGetRealPath(String value) {
        realPath = value;
    }
}

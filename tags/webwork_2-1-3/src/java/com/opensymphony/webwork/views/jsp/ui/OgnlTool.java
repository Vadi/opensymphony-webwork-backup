/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlUtil;
import ognl.Ognl;
import ognl.OgnlException;


/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public class OgnlTool {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static OgnlTool instance = new OgnlTool();

    //~ Constructors ///////////////////////////////////////////////////////////

    private OgnlTool() {
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public static OgnlTool getInstance() {
        return instance;
    }

    public Object findValue(String expr, Object context) {
        try {
            return Ognl.getValue(OgnlUtil.compile(expr), context);
        } catch (OgnlException e) {
            return null;
        }
    }
}

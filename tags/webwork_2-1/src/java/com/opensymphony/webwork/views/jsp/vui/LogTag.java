/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.vui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Voice UI for log tag
 *
 * @author Jeff Haynie (jhaynie@vocalocity.net)
 * @version $Revision$
 */
public class LogTag extends AbstractVUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    // Attributes ----------------------------------------------------
    protected static Log log = LogFactory.getLog(LogTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String exprAttr;
    protected String labelAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setExpr(String aName) {
        exprAttr = aName;
    }

    public String getExpr() {
        return exprAttr;
    }

    public String getFooterTemplate() {
        return "log-footer.jsp";
    }

    // Public --------------------------------------------------------
    public String getHeaderTemplate() {
        return "log-header.jsp";
    }

    public void setLabel(String aName) {
        labelAttr = aName;
    }

    public String getLabel() {
        return labelAttr;
    }

    protected void initializeAttributes() {
        getSetParameter(labelAttr, "label");
        getSetParameter(exprAttr, "expr");
    }
}

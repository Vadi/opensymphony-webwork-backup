package com.opensymphony.webwork.views.jsp.vui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Voice UI for log tag
 *
 * @author Jeff Haynie (jhaynie@vocalocity.net)
 * @version $Revision$
 */
public class LogTag
        extends AbstractVUITag {
    // Attributes ----------------------------------------------------
    protected static Log log = LogFactory.getLog(LogTag.class);
    protected String labelAttr;
    protected String exprAttr;

    // Public --------------------------------------------------------

    public String getHeaderTemplate() {
        return "log-header.jsp";
    }

    public String getFooterTemplate() {
        return "log-footer.jsp";
    }

    public void setLabel(String aName) {
        labelAttr = aName;
    }

    public String getLabel() {
        return labelAttr;
    }

    public void setExpr(String aName) {
        exprAttr = aName;
    }

    public String getExpr() {
        return exprAttr;
    }

    protected void initializeAttributes() {
        getSetParameter(labelAttr, "label");
        getSetParameter(exprAttr, "expr");
    }
}

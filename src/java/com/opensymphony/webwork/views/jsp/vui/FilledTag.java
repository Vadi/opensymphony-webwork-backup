/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.vui;

import com.opensymphony.webwork.views.util.UrlHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Voice UI for filled tag
 *
 * @author Jeff Haynie (jhaynie@vocalocity.net)
 * @version $Revision$
 */
public class FilledTag extends AbstractVUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    // Attributes ----------------------------------------------------
    protected static Log log = LogFactory.getLog(FilledTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String actionAttr;
    protected String actionaudioAttr;
    protected String modeAttr;
    protected String namelistAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAction(String aName) {
        actionAttr = aName;
    }

    public String getAction() {
        return actionAttr;
    }

    public void setActionaudio(String aName) {
        actionaudioAttr = aName;
    }

    public String getActionaudio() {
        return actionaudioAttr;
    }

    public String getFooterTemplate() {
        return "filled-footer.jsp";
    }

    // Public --------------------------------------------------------
    public String getHeaderTemplate() {
        return "filled-header.jsp";
    }

    public void setMode(String aName) {
        modeAttr = aName;
    }

    public String getMode() {
        return modeAttr;
    }

    public void setNamelist(String aName) {
        namelistAttr = aName;
    }

    public String getNamelist() {
        return namelistAttr;
    }

    protected void initializeAttributes() {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

        String url = ((actionAttr != null) ? UrlHelper.buildUrl((String) findValue(actionAttr), request, response, null) : null);

        if (url != null) {
            addParameter("action", url);
            url = ((actionaudioAttr != null) ? UrlHelper.buildUrl((String) findValue(actionaudioAttr), request, response, null) : null);

            if (url != null) {
                addParameter("actionaudio", url);
            }
        }

        getSetParameter(namelistAttr, "namelist");
        getSetParameter(modeAttr, "mode");
    }
}

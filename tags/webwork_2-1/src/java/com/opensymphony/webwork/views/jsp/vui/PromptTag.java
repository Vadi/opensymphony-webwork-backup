/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.vui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Voice UI for prompt tag
 *
 * @author Jeff Haynie (jhaynie@vocalocity.net)
 * @version $Revision$
 */
public class PromptTag extends AbstractVUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    // Attributes ----------------------------------------------------
    protected static Log log = LogFactory.getLog(PromptTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String bargeinAttr;
    protected String condAttr;
    protected String countAttr;
    protected String langAttr;
    protected String timeoutAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setBargein(String aName) {
        bargeinAttr = aName;
    }

    public String getBargein() {
        return bargeinAttr;
    }

    public void setCond(String aName) {
        condAttr = aName;
    }

    public String getCond() {
        return condAttr;
    }

    public void setCount(String aName) {
        countAttr = aName;
    }

    public String getCount() {
        return countAttr;
    }

    public String getFooterTemplate() {
        return "prompt-footer.jsp";
    }

    // Public --------------------------------------------------------
    public String getHeaderTemplate() {
        return "prompt-header.jsp";
    }

    public void setLang(String aName) {
        langAttr = aName;
    }

    public String getLang() {
        return langAttr;
    }

    public void setTimeout(String aName) {
        timeoutAttr = aName;
    }

    public String getTimeout() {
        return timeoutAttr;
    }

    protected void initializeAttributes() {
        getSetParameter(bargeinAttr, "bargein");
        getSetParameter(timeoutAttr, "timeout");
        getSetParameter(langAttr, "lang");
        getSetParameter(condAttr, "cond");
        getSetParameter(countAttr, "count");
    }
}

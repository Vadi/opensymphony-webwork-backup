package com.opensymphony.webwork.views.jsp.vui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Voice UI for prompt tag
 *
 * @author Jeff Haynie (jhaynie@vocalocity.net)
 * @version $Revision$
 */
public class PromptTag
        extends AbstractVUITag {
    // Attributes ----------------------------------------------------
    protected static Log log = LogFactory.getLog(PromptTag.class);
    protected String bargeinAttr;
    protected String timeoutAttr;
    protected String langAttr;
    protected String condAttr;
    protected String countAttr;

    // Public --------------------------------------------------------

    public String getHeaderTemplate() {
        return "prompt-header.jsp";
    }

    public String getFooterTemplate() {
        return "prompt-footer.jsp";
    }

    public void setBargein(String aName) {
        bargeinAttr = aName;
    }

    public String getBargein() {
        return bargeinAttr;
    }

    public void setTimeout(String aName) {
        timeoutAttr = aName;
    }

    public String getTimeout() {
        return timeoutAttr;
    }

    public void setLang(String aName) {
        langAttr = aName;
    }

    public String getLang() {
        return langAttr;
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

    protected void initializeAttributes() {
        getSetParameter(bargeinAttr, "bargein");
        getSetParameter(timeoutAttr, "timeout");
        getSetParameter(langAttr, "lang");
        getSetParameter(condAttr, "cond");
        getSetParameter(countAttr, "count");
    }
}

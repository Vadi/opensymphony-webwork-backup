/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.vui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Voice UI for grammar tag
 *
 * @author Jeff Haynie (jhaynie@vocalocity.net)
 * @version $Revision$
 */
public class AudioTag extends AbstractVUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    // Attributes ----------------------------------------------------
    protected static Log log = LogFactory.getLog(AudioTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String exprAttr;
    protected String fetchhintAttr;
    protected String fetchtimeoutAttr;
    protected String maxageAttr;
    protected String maxstaleAttr;
    protected String personaAttr;
    protected String srcAttr;
    protected String ttsAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setExpr(String aName) {
        exprAttr = aName;
    }

    public String getExpr() {
        return exprAttr;
    }

    public void setFetchhint(String aName) {
        fetchhintAttr = aName;
    }

    public String getFetchhint() {
        return fetchhintAttr;
    }

    public void setFetchtimeout(String aName) {
        fetchtimeoutAttr = aName;
    }

    public String getFetchtimeout() {
        return fetchtimeoutAttr;
    }

    public String getFooterTemplate() {
        return "audio-footer.jsp";
    }

    // Public --------------------------------------------------------
    public String getHeaderTemplate() {
        return "audio-header.jsp";
    }

    public void setMaxage(String aName) {
        maxageAttr = aName;
    }

    public String getMaxage() {
        return maxageAttr;
    }

    public void setMaxstale(String aName) {
        maxstaleAttr = aName;
    }

    public String getMaxstale() {
        return maxstaleAttr;
    }

    public void setPersona(String aName) {
        personaAttr = aName;
    }

    public String getPersona() {
        return personaAttr;
    }

    public void setSrc(String aName) {
        srcAttr = aName;
    }

    public String getSrc() {
        return srcAttr;
    }

    public void setTts(String aName) {
        ttsAttr = aName;
    }

    public String getTts() {
        return ttsAttr;
    }

    protected void initializeAttributes() {
        getSetParameter(srcAttr, "src");
        getSetParameter(exprAttr, "expr");
        getSetParameter(ttsAttr, "tts");
        getSetParameter(personaAttr, "persona");
        getSetParameter(fetchhintAttr, "fetchhint");
        getSetParameter(fetchtimeoutAttr, "fetchtimeout");
        getSetParameter(maxageAttr, "maxage");
        getSetParameter(maxstaleAttr, "maxstale");
    }
}

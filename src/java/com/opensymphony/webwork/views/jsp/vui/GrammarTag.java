package com.opensymphony.webwork.views.jsp.vui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Voice UI for grammar tag
 *
 * @author Jeff Haynie (jhaynie@vocalocity.net)
 * @version $Revision$
 */
public class GrammarTag
        extends AbstractVUITag {
    // Attributes ----------------------------------------------------
    protected static Log log = LogFactory.getLog(GrammarTag.class);
    protected String nameAttr;
    protected String langAttr;
    protected String modelAttr;
    protected String modeAttr;
    protected String weightAttr;

    // Public --------------------------------------------------------

    public String getHeaderTemplate() {
        return "grammar-header.jsp";
    }

    public String getFooterTemplate() {
        return "grammar-footer.jsp";
    }

    public void setName(String aName) {
        nameAttr = aName;
    }

    public String getName() {
        return nameAttr;
    }

    public void setLang(String aName) {
        langAttr = aName;
    }

    public String getLang() {
        return langAttr;
    }

    public void setModel(String aName) {
        modelAttr = aName;
    }

    public String getModel() {
        return modelAttr;
    }

    public void setMode(String aName) {
        modeAttr = aName;
    }

    public String getMode() {
        return modeAttr;
    }

    public void setWeight(String aName) {
        weightAttr = aName;
    }

    public String getWeight() {
        return weightAttr;
    }

    protected void initializeAttributes() {
        getSetParameter(nameAttr, "name");
        getSetParameter(langAttr, "lang");
        getSetParameter(modelAttr, "model");
        getSetParameter(modeAttr, "mode");
        getSetParameter(weightAttr, "weight");
    }
}

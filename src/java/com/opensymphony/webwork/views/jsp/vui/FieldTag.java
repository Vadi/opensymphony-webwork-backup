package com.opensymphony.webwork.views.jsp.vui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Voice UI for field tag
 *
 * @author Jeff Haynie (jhaynie@vocalocity.net)
 * @version $Revision$
 */
public class FieldTag
        extends AbstractVUITag {
    // Attributes ----------------------------------------------------
    protected static Log log = LogFactory.getLog(FieldTag.class);
    protected String nameAttr;
    protected String slotAttr;
    protected String modalAttr;
    protected String condAttr;
    protected String typeAttr;

    // Public --------------------------------------------------------

    public String getHeaderTemplate() {
        return "field-header.jsp";
    }

    public String getFooterTemplate() {
        return "field-footer.jsp";
    }

    public void setName(String aName) {
        nameAttr = aName;
    }

    public void setModal(String aName) {
        modalAttr = aName;
    }

    public String getModal() {
        return modalAttr;
    }

    public void setSlot(String aName) {
        slotAttr = aName;
    }

    public String getSlot() {
        return slotAttr;
    }

    public void setCond(String aName) {
        condAttr = aName;
    }

    public String getCond() {
        return condAttr;
    }

    public void setType(String aName) {
        typeAttr = aName;
    }

    public String getType() {
        return typeAttr;
    }

    protected void initializeAttributes() {
        getSetParameter(nameAttr, "name");
        getSetParameter(condAttr, "cond");
        getSetParameter(modalAttr, "modal");
        getSetParameter(slotAttr, "slot");
        getSetParameter(typeAttr, "type");
    }
}

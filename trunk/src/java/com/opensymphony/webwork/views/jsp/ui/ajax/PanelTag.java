package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag;
import com.opensymphony.webwork.components.ajax.ContentPane;
import com.opensymphony.webwork.components.ajax.JavascriptEmitter;

import javax.servlet.jsp.JspException;

/**
 * PanelTag
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 * @author <a href="ian@fdar.com">Ian Roughley</a>
 */
public class PanelTag extends AbstractClosingUITag implements Cloneable, ContentPane {

    private String tabName;



    /**
     * Set the displayable name for the tab.
     *
     * @param tabName the displayable name for the tab
     */
    public void setTabName(String tabName) {
        this.tabName = findString(tabName);
    }

    /**
     * Note: no idea why this.clone() needs to be used, but when it wasn't the same
     * tag instance was re-used for each tag cause incorrect javascript to be generated.
     *
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        subscribeTopicName = ((TabbedPanelTag)getParent()).getTopicName();
        try {
            ((TabbedPanelTag)getParent()).addTab((ContentPane)this.clone());
            ((TopicScopeTag)findAncestorWithClass( this, TopicScopeTag.class )).addEmitter((JavascriptEmitter)this.clone());
        } catch (CloneNotSupportedException e) {
            throw new JspException(e);
        }
        return super.doEndTag();
    }

    /**
     * @see ContentPane#getId()
     */
    public String getId() {
        return id;
    }

    /**
     * @see ContentPane#getTabName()
     */
    public String getTabName() {
        return tabName;
    }

}

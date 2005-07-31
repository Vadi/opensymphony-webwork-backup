package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag;
import com.opensymphony.webwork.views.jsp.ui.AbstractUITag;
import com.opensymphony.webwork.components.ajax.ContentPane;
import com.opensymphony.webwork.components.ajax.JavascriptEmitter;

import javax.servlet.jsp.JspException;

/**
 * PanelTag
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 * @author <a href="ian@fdar.com">Ian Roughley</a>
 */
public class PanelTag extends AbstractUITag {

    private String tabName;



    public void setTabName(String tabName) {
        this.tabName = findString(tabName);
    }

    public String getId() {
        return id;
    }

    public String getTabName() {
        return tabName;
    }

}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

import java.util.Map;
import java.util.Vector;

import com.opensymphony.webwork.views.jsp.ui.ComponentTag;
import com.opensymphony.webwork.util.TabbedPane;

/**
 *	TabbedPane tag.
 *	@author Onyeje Bose (digi9ten@yahoo.com)
 *	@version $Revision$
 */

public class TabbedPaneTag extends ComponentTag {
    private final static String TEMPLATE = "tabbedpane.vm";

    // Protected --------------------------------------------------------
    protected TabbedPane tabPane;
    protected String contentName;

    protected String strVal(String objName) {
        try {
            return String.valueOf(findValue(objName));
        } catch (Exception e) {
            return objName;
        }
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public String getSelectedUrl() {
        Map.Entry me = (Map.Entry) this.getContent().elementAt(this.getSelectedIndex());
        return me.getValue().toString();
    }

    public int getColSpanLength() {
        return (this.getTabAlign().compareToIgnoreCase("CENTER") == 0 ? this.getContent().size() + 2 : this.getContent().size() + 1);
    }

    public String getIndexLink() {
        return ("TABBEDPANE_" + getId() + "_INDEX");
    }

    public String getContentName() {
        return this.contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = strVal(contentName);

        Object obj = findValue(this.contentName);
        if (obj instanceof Vector)
            this.setContent((Vector) obj);
    }

    public int getSelectedIndex() {
        return tabPane.getSelectedIndex();
    }

    public void setSelectedIndex(int selectedIndex) {
        tabPane.setSelectedIndex(selectedIndex);
    }

    public java.util.Vector getContent() {
        return tabPane.getContent();
    }

    public void setContent(java.util.Vector content) {
        tabPane.setContent(content);
    }

    public String getTabAlign() {
        return tabPane.getTabAlign();
    }

    public void setTabAlign(String tabAlign) {
        tabPane.setTabAlign(strVal(tabAlign));
    }

    // BodyTagSupport overrides --------------------------------------
    public void setPageContext(PageContext aPageContext) {
        tabPane = new TabbedPane(0);
        contentName = null;
        setSelectedIndex(0);
        setTabAlign("'CENTER'");

        super.setPageContext(aPageContext);
    }

    // BodyTag implementation ----------------------------------------
    public int doStartTag() throws JspException {
        String indexStr = pageContext.getRequest().getParameter(getIndexLink());
        if (indexStr != null) {
            try {
                int index = Integer.parseInt(indexStr);
                this.setSelectedIndex((index < 0 ? 0 : index));
            } catch (Exception e) {
                throw new JspTagException("TabbedPane Error: " + e.toString());
            }
        }
        return super.doStartTag();
    }

    // IncludeTag overrides ------------------------------------------
    public void release() {
        this.setSelectedIndex(0);

        if (this.getTabAlign() == null)
            this.setTabAlign("'CENTER'");
    }
}

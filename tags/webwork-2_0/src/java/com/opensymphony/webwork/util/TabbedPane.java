/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import java.util.Vector;


/**
 * A bean that helps implement a tabbed pane
 *        @author Onyeje Bose (digi9ten@yahoo.com)
 *        @author Rickard �berg (rickard@middleware-company.com)
 *        @version $Revision$
 */
public class TabbedPane {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected String tabAlign = null;

    // Attributes ----------------------------------------------------
    protected Vector content = null;
    protected int selectedIndex = 0;

    //~ Constructors ///////////////////////////////////////////////////////////

    // Public --------------------------------------------------------
    public TabbedPane(int defaultIndex) {
        selectedIndex = defaultIndex;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setContent(Vector content) {
        this.content = content;
    }

    public Vector getContent() {
        return content;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setTabAlign(String tabAlign) {
        this.tabAlign = tabAlign;
    }

    public String getTabAlign() {
        return tabAlign;
    }
}

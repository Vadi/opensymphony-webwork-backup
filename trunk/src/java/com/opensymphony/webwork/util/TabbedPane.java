package com.opensymphony.webwork.util;

import java.util.Vector;

/**
 * A bean that helps implement a tabbed pane
 *	@author Onyeje Bose (digi9ten@yahoo.com)
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 */
public class TabbedPane {
    // Attributes ----------------------------------------------------
    protected Vector content = null;
    protected int selectedIndex = 0;
    protected String tabAlign = null;

    // Public --------------------------------------------------------
    public TabbedPane(int defaultIndex) {
        selectedIndex = defaultIndex;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public Vector getContent() {
        return content;
    }

    public void setContent(Vector content) {
        this.content = content;
    }

    public String getTabAlign() {
        return tabAlign;
    }

    public void setTabAlign(String tabAlign) {
        this.tabAlign = tabAlign;
    }
}

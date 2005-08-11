package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.TabbedPanel;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.Panel;
import com.opensymphony.xwork.util.OgnlValueStack;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TabbedPanelTag
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 * @author <a href="ian@fdar.com">Ian Roughley</a>
 */
public class TabbedPanelTag extends AbstractUITag {

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new TabbedPanel(stack, req, res);
    }

    /**
     * Get the list of panel tabs for this tab panel.
     * @return the list of panel tabs for this tab panel
     */
    public List getTabs() {
        return ((TabbedPanel) bean).getTabs();
    }

    /**
     * Add a new panel to be rendered.
     *
     * @param pane a new panel to be rendered
     */
    public void addTab( Panel pane ) {
        ((TabbedPanel) bean).addTab(pane);
    }

}

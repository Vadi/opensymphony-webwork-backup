package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.TabbedPanel;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ian Roughley
 * @version $Id$
 */
public class TabbedPanelDirective extends AbstractDirective {
    public String getBeanName() {
        return "tabbedpanel";
    }

    protected UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new TabbedPanel(stack, req, res);
    }
}

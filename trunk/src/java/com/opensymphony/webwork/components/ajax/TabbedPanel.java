package com.opensymphony.webwork.components.ajax;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.ClosingUIBean;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:23:18 AM
 */
public class TabbedPanel extends ClosingUIBean {
    public static final String TEMPLATE_CLOSE = "tabbedpanel-close";
    public static final String COMPONENT_JS = "tabbedpanel-js.vm";
    final private static String COMPONENT_NAME = TabbedPanel.class.getName();

    public String getTopicName() {
        return "topic_tab_" + id + "_selected";
    }

    public String getDefaultOpenTemplate() {
        return null;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }
}

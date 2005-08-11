package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:23:18 AM
 */
public class TabbedPanel extends ClosingUIBean {
    public static final String TEMPLATE = "tabbedpanel";
    public static final String TEMPLATE_CLOSE = "tabbedpanel-close";
    final private static String COMPONENT_NAME = TabbedPanel.class.getName();

    protected List tabs = new ArrayList();

    public TabbedPanel(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public void addTab(Panel pane) {
        tabs.add(pane);
    }

    public List getTabs() {
        return tabs;
    }

    public String getTopicName() {
        return "topic_tab_" + id + "_selected";
    }

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        addParameter("topicName", "topic_tab_" + id + "_selected");
        addParameter("tabs", tabs);

    }

    public String getDefaultOpenTemplate() {
        return TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    public String getComponentName() {
        return COMPONENT_NAME;
    }
}

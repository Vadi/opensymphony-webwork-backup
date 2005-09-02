package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:22:54 AM
 */
public class Panel extends Div {
    private static final Log LOG = LogFactory.getLog(Panel.class);

    public static final String TEMPLATE = "tab";
    public static final String TEMPLATE_CLOSE = "tab-close";
    public static final String COMPONENT_NAME = Panel.class.getName();

    protected String tabName;
    protected String subscribeTopicName;
    protected String remote;

    public Panel(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate() {
        return TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    public void end(Writer writer, String body) {
        TabbedPanel tabbedPanel = ((TabbedPanel) findLastAncestor(TabbedPanel.class));
        subscribeTopicName = tabbedPanel.getTopicName();
        tabbedPanel.addTab(this);

        super.end(writer, body);
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (tabName != null) {
            addParameter("tabName", findString(tabName));
        }

        if (subscribeTopicName != null) {
            addParameter("subscribeTopicName", subscribeTopicName);
        }

        if (remote != null && "true".equalsIgnoreCase(remote)) {
            addParameter("remote", "true");
        } else {
            addParameter("remote", "false");
        }
    }

    public String getTabName() {
        return findString(tabName);
    }

    public String getComponentName() {
        return COMPONENT_NAME;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public void setSubscribeTopicName(String subscribeTopicName) {
        this.subscribeTopicName = subscribeTopicName;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }
}

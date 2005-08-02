package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.ajax.RemotePanel;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ian Roughley
 * @version $Id$
 */
public class RemotePanelTag extends RemoteUpdateDivTag {
    protected String tabName;
    protected String subscribeTopicName;

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new RemotePanel(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        RemotePanel panel = ((RemotePanel) bean);
        panel.setTabName(tabName);
        panel.setSubscribeTopicName(subscribeTopicName);
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public void setSubscribeTopicName(String subscribeTopicName) {
        this.subscribeTopicName = subscribeTopicName;
    }
}

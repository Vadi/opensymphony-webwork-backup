package com.opensymphony.webwork.config_browser;

import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.ActionSupport;

/**
 * ShowConfigAction
 * @author Jason Carreira
 * Created Aug 11, 2003 9:42:12 PM
 */
public class ShowConfigAction extends ActionSupport {
    private String namespace;
    private String actionName;
    private ActionConfig config;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public ActionConfig getConfig() {
        return config;
    }

    public String execute() throws Exception {
        config = ConfigurationHelper.getActionConfig(namespace,actionName);
        return SUCCESS;
    }
}

package com.opensymphony.webwork.config_browser;

import com.opensymphony.xwork.ActionSupport;

import java.util.Set;
import java.util.TreeSet;

/**
 * ActionNamesAction
 * @author Jason Carreira
 * Created Aug 11, 2003 9:35:15 PM
 */
public class ActionNamesAction extends ActionSupport {
    private Set actionNames;
    private String namespace;

    public Set getActionNames() {
        return actionNames;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String execute() throws Exception {
        actionNames = new TreeSet(ConfigurationHelper.getActionNames(namespace));
        return SUCCESS;
    }
}

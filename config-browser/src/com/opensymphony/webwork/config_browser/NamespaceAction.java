package com.opensymphony.webwork.config_browser;

import com.opensymphony.xwork.ActionSupport;

import java.util.Set;

/**
 * NamespaceAction
 * @author Jason Carreira
 * Created Aug 11, 2003 8:37:31 PM
 */
public class NamespaceAction extends ActionSupport {
    private Set namespaces;

    public Set getNamespaces() {
        return namespaces;
    }

    public String execute() throws Exception {
        namespaces = ConfigurationHelper.getNamespaces();
        return SUCCESS;
    }
}

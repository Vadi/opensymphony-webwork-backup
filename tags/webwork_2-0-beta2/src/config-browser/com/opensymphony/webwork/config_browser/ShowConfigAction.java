package com.opensymphony.webwork.config_browser;

import java.beans.PropertyDescriptor;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork.config.entities.ActionConfig;

/**
 * ShowConfigAction
 * 
 * @author Jason Carreira Created Aug 11, 2003 9:42:12 PM
 */
public class ShowConfigAction extends ActionNamesAction {
    private String namespace;
    private String actionName;
    private ActionConfig config;
    private Set actionNames;
    private String detailView = "results";
    private PropertyDescriptor[] properties;
    private static Log log = LogFactory.getLog(ShowConfigAction.class);

    public String getDetailView() {
        return detailView;
    }

    public void setDetailView(String detailView) {
        this.detailView = detailView;
    }

    public Set getActionNames() {
        return actionNames;
    }

    public String getNamespace() {
        return namespace;
    }

    public String stripPackage(Class clazz) {
        return clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1);
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

    public PropertyDescriptor[] getProperties() {
        return properties;
    }


    public String execute() throws Exception {
        super.execute();
        config = ConfigurationHelper.getActionConfig(namespace, actionName);
        actionNames =
                new TreeSet(ConfigurationHelper.getActionNames(namespace));
        try {
            properties =
                    PropertyUtils.getPropertyDescriptors(getConfig().getClazz());
        } catch (Exception e) {
            log.error("Unable to get properties for action " + actionName, e);
            addActionError(
                    "Unable to retrieve action properties: " + e.toString());
        }

        if (hasErrors()) //super might have set some :)
            return ERROR;
        else
            return SUCCESS;
    }
}

package com.opensymphony.webwork.config_browser;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.config.entities.ActionConfig;

/**
 * ActionNamesAction
 * 
 * @author Jason Carreira Created Aug 11, 2003 9:35:15 PM
 */
public class ActionNamesAction extends ActionSupport {
    private Set actionNames;
    private String namespace;
    private Set namespaces;
    private static Log log = LogFactory.getLog(ActionNamesAction.class);

    public Set getActionNames() {
        return actionNames;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public ActionConfig getConfig(String actionName) {
        return ConfigurationHelper.getActionConfig(namespace, actionName);
    }

    public Set getNamespaces() {
        return namespaces;
    }

    /**
     * Extremely quick hack 
     */
/*	public boolean resultContains(String actionName, String location) {
		if (location == null) return false;
		try {
			InputStream in = ServletActionContext.getServletContext().getResourceAsStream(location);
			StringBuffer content = new StringBuffer();
			byte[] buf = new byte[1024];
			int read;
			while ((read = in.read(buf)) > 0) {
				content.append(new String(buf, 0, read));
			}
			return (content.toString().indexOf(actionName) > 0);
		} catch (Exception e) {
			log.error("Unable to get resouce " + location, e);
			return false;
		}
	}*/
    public String execute() throws Exception {
        namespaces = ConfigurationHelper.getNamespaces();
        if (namespaces.size() == 0) {
            addActionError("There are no namespaces in this configuration");
            return ERROR;
        }
        if (namespace == null) {
            namespace = (String) namespaces.iterator().next();
        }
        actionNames =
                new TreeSet(ConfigurationHelper.getActionNames(namespace));
        return SUCCESS;
    }
}

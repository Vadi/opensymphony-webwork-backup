package com.opensymphony.webwork.plugin;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.config.ConfigurationManager;

import javax.swing.*;

public class ActionBrowser implements ProjectComponent, Configurable {
    Project project;

    public ActionBrowser() {
    }

    public ActionBrowser(Project project) {
        System.out.println("ActionBrowser.ActionBrowser");
        this.project = project;
    }

    public void projectOpened() {
        ConfigurationManager.clearConfigurationProviders();
        String base = "c:\\docs\\opensource\\test";
        ConfigurationManager.addConfigurationProvider(new PluginConfigProvider(base + "\\src"));
        ObjectFactory.setObjectFactory(new PluginObjectFactory(base + "\\classes"));
        ActionsPanel ap = new ActionsPanel(project, new String[] { "webapp" });
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).registerToolWindow("Actions", ap, ToolWindowAnchor.BOTTOM);
    }

    public void projectClosed() {
        ToolWindowManager.getInstance(project).unregisterToolWindow("Actions");
    }

    public String getComponentName() {
        return "ActionBrowser";
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    public String getDisplayName() {
        return "WebWork Action Browser";
    }

    public Icon getIcon() {
        return null;
    }

    public String getHelpTopic() {
        return null;
    }

    public JComponent createComponent() {
        return null;
    }

    public boolean isModified() {
        return false;
    }

    public void apply() throws ConfigurationException {
    }

    public void reset() {
    }

    public void disposeUIResources() {
    }

}

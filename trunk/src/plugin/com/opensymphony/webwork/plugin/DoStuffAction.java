package com.opensymphony.webwork.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class DoStuffAction extends AnAction {
    public void actionPerformed(AnActionEvent event) {
        System.out.println("DoStuffAction.actionPerformed");
    }
}

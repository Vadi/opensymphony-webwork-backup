package com.opensymphony.webwork.plugin;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class ActionBrowser implements FileEditorProvider, FileEditor, ApplicationComponent {
    public String getComponentName() {
        return "WebWork-ActionBrowser";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void initComponent() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void disposeComponent() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean accept(Project project, VirtualFile virtualFile) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public FileEditor createEditor(Project project, VirtualFile virtualFile) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void disposeEditor(FileEditor fileEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public FileEditorState readState(org.jdom.Element element, Project project, VirtualFile virtualFile) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeState(FileEditorState fileEditorState, Project project, org.jdom.Element element) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getEditorTypeId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public FileEditorPolicy getPolicy() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JComponent getComponent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JComponent getPreferredFocusedComponent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public FileEditorState getState(FileEditorStateLevel fileEditorStateLevel) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setState(FileEditorState fileEditorState) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isModified() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isValid() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void selectNotify() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deselectNotify() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

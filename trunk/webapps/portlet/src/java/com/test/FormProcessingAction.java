package com.test;

import com.opensymphony.xwork.ActionSupport;

public class FormProcessingAction extends ActionSupport {
    private String checkbox;

    private String file;

    private String hidden;

    private String password;

    private String radio;

    private String select;

    private String textarea;

    private String textfield;

    public String getCheckbox() {
        return checkbox;
    }

    public String getFile() {
        return file;
    }

    public String getHidden() {
        return hidden;
    }

    public String getPassword() {
        return password;
    }

    public String getRadio() {
        return radio;
    }

    public String getSelect() {
        return select;
    }

    public String getTextarea() {
        return textarea;
    }

    public String getTextfield() {
        return textfield;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public void setTextarea(String textarea) {
        this.textarea = textarea;
    }

    public void setTextfield(String textfield) {
        this.textfield = textfield;
    }

    public String execute() throws Exception {
        return SUCCESS;
    }

    public String myMethod() throws Exception {
        file = file + "Hello";
        return SUCCESS;
    }
}
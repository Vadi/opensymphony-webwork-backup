package com.opensymphony.webwork.example;

import webwork.action.ActionSupport;

public class JavascriptValidationAction extends ActionSupport {
    String test;

    public String execute() throws Exception {
        System.out.println("You entered: " + test);
        return SUCCESS;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}

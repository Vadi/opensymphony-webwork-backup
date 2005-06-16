package com.opensymphony.webwork.example.tutorial.ajax;

import com.opensymphony.xwork.ActionSupport;

public class HelloWorld extends ActionSupport {
    private String name;
    private String message;

    public String doDefault() throws Exception {
        return INPUT;
    }

    public String execute() throws Exception {
        message = "Hello, " + name;

        return SUCCESS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

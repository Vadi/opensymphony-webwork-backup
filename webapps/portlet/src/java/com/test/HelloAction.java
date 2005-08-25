package com.test;

import com.opensymphony.xwork.ActionSupport;

import java.util.Date;

public class HelloAction extends ActionSupport {
    private String _userName;

    public void setUserName(String name) {
        _userName = name;
    }

    public String getUserName() {
        return _userName;
    }

    public Date getNow() {
        return new Date(System.currentTimeMillis());
    }

    public String execute() throws Exception {
        return SUCCESS;
    }
} 


package com.test;

import com.opensymphony.xwork.ActionSupport;
import com.test.bo.User;

public class RegisterActionSupport extends ActionSupport {

    private User user = new User();
    private String verifyPassword;

    public User getUser() {
        return this.user;
    }

    public String execute() {
        return SUCCESS;
    }

    public String getVerifyPassword() {
        return this.verifyPassword;
    }

    public void setVerifyPassword(String verPassword) {
        this.verifyPassword = verPassword;
    }
}

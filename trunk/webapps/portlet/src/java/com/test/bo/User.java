package com.test.bo;

public class User {

    private String username;
    private String password;
    private String email;
    private int age;


    public String toString() {
        return "username=" + username
                + ";password=" + password
                + ";email=" + email
                + ";age=" + age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

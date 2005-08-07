package com.opensymphony.webwork.sandbox;

/**
 * User: plightbo
 * Date: Aug 6, 2005
 * Time: 7:12:36 PM
 */
public class Person {
    long ID;
    String name;

    public Person(long ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}

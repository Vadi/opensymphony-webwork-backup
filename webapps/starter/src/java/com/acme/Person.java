package com.acme;

/**
 * User: plightbo
 * Date: Aug 6, 2005
 * Time: 7:12:36 PM
 */
public class Person {
    long id;
    String name;

    public Person() {
    }

    public Person(long ID, String name) {
        this.id = ID;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

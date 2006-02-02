package com.opensymphony.webwork.showcase.person;

/**
 * User: plightbo
 * Date: Aug 6, 2005
 * Time: 7:12:36 PM
 */
public class Person {
    Long id;
    String name;
    String lastName;

    public Person() {
    }

    public Person(Long id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

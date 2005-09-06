package com.opensymphony.webwork.sandbox.typeconversion;

/**
 * User: plightbo
 * Date: Sep 5, 2005
 * Time: 5:43:07 PM
 */
public class Bean {
    long id;
    String name;
    String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

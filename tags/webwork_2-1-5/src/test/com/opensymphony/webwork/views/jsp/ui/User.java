/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


/**
 * @author Mark Woon
 */
public class User {
    //~ Instance fields ////////////////////////////////////////////////////////

    private String name;

    //~ Constructors ///////////////////////////////////////////////////////////

    public User() {
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

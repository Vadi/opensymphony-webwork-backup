/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionSupport;

import java.util.HashMap;
import java.util.Map;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class TestAction extends ActionSupport {
    //~ Instance fields ////////////////////////////////////////////////////////

    private String foo;
    private String result;
    private String[] array;
    private String[][] list;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setArray(String[] array) {
        this.array = array;
    }

    public String[] getArray() {
        return array;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return foo;
    }

    public void setList(String[][] list) {
        this.list = list;
    }

    public String[][] getList() {
        return list;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public String execute() throws Exception {
        if (result == null) {
            result = Action.SUCCESS;
        }

        return result;
    }
}

/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.ajax;

import com.opensymphony.xwork.Action;


/**
 * @author Ian Roughley
 * @version $Id$
 */
public class AjaxTestAction implements Action {

    private static int counter = 0;
    private String data;

    public long getServerTime() {
        return System.currentTimeMillis();
    }

    public int getCount() {
        return ++counter;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String execute() throws Exception {
        return SUCCESS;
    }
}

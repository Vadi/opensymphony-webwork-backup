package com.opensymphony.webwork.example.tutorial.ajax;

import com.opensymphony.xwork.Action;


/**
 * @author		Ian Roughley
 * @version		$Id$
 */
public class AjaxTestAction implements Action {

    private static int counter = 0;

    public long getServerTime() {
        return System.currentTimeMillis();
    }

    public int getCount() {
        return ++counter;
    }

    public String execute() throws Exception {
        return SUCCESS;
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.example.counter;

import com.opensymphony.xwork.Action;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class SimpleCounter implements Action {
    //~ Instance fields ////////////////////////////////////////////////////////

    private Counter counter;
    private String foo;

    //~ Methods ////////////////////////////////////////////////////////////////

    public SimpleCounter(Counter counter) {
        this.counter = counter;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return foo;
    }

    public String execute() throws Exception {
        counter.incrementCount();

        return SUCCESS;
    }
}

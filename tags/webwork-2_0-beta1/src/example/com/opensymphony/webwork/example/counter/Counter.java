/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.example.counter;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class Counter {
    //~ Instance fields ////////////////////////////////////////////////////////

    private int count;

    //~ Methods ////////////////////////////////////////////////////////////////

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }
}

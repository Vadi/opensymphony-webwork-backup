/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 6/09/2003
 *
 */
package com.opensymphony.webwork.views.freemarker;


/**
 * @author CameronBraid
 *
 */
public class ListEntry {
    //~ Instance fields ////////////////////////////////////////////////////////

    final private Object key;
    final private Object value;

    //~ Constructors ///////////////////////////////////////////////////////////

    public ListEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}

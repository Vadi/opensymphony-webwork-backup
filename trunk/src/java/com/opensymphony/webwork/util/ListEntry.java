/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 6/09/2003
 *
 */
package com.opensymphony.webwork.util;


/**
 * @author CameronBraid
 *
 */
public class ListEntry {
    //~ Instance fields ////////////////////////////////////////////////////////

    final private Object key;
    final private Object value;
    final private boolean isSelected;

    //~ Constructors ///////////////////////////////////////////////////////////

    public ListEntry(Object key, Object value, boolean isSelected) {
        this.key = key;
        this.value = value;
        this.isSelected = isSelected;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public boolean getIsSelected() {
        return isSelected;
    }
}

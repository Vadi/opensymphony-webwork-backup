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
 */
public class ListEntry {

    final private Object key;
    final private Object value;
    final private boolean isSelected;


    public ListEntry(Object key, Object value, boolean isSelected) {
        this.key = key;
        this.value = value;
        this.isSelected = isSelected;
    }


    public boolean getIsSelected() {
        return isSelected;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}

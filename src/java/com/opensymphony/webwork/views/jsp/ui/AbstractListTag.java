/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import java.lang.reflect.Array;

import java.util.Collection;
import java.util.Map;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public abstract class AbstractListTag extends AbstractUITag {
    //~ Instance fields ////////////////////////////////////////////////////////

    private Object listValue;
    private String list;
    private String listKey;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setList(String list) {
        this.list = list;
    }

    public String getList() {
        return list;
    }

    public void setListKey(String listKey) {
        this.listKey = listKey;
    }

    public String getListKey() {
        return listKey;
    }

    public void setListValue(Object listValue) {
        this.listValue = listValue;
    }

    public Object getListValue() {
        return listValue;
    }

    public boolean contains(Object obj1, Object obj2) {
        if ((obj1 == null) || (obj2 == null)) {
            return false;
        }

        if (obj1 instanceof Map) {
            if (((Map) obj1).containsValue(obj2)) {
                return true;
            }
        } else if (obj1 instanceof Collection) {
            if (((Collection) obj1).contains(obj2)) {
                return true;
            }
        } else if (obj1.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(obj1); i++) {
                Object value = null;
                value = Array.get(obj1, i);

                if (value.toString().equals(obj2.toString())) {
                    return true;
                }
            }
        } else if (obj1.equals(obj2)) {
            return true;
        } else if (obj1.toString().equals(obj2.toString())) {
            return true;
        }

        return false;
    }

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        this.list = null;
        this.listKey = null;
        this.listValue = null;
    }
}

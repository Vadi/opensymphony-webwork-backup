/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.util.MakeIterator;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.lang.reflect.Array;

import java.util.Collection;
import java.util.Map;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public abstract class AbstractListTag extends AbstractUITag {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected String listAttr;
    protected String listKeyAttr;
    protected String listValueAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setList(String list) {
        this.listAttr = list;
    }

    public void setListKey(String listKey) {
        this.listKeyAttr = listKey;
    }

    public void setListValue(String listValue) {
        this.listValueAttr = listValue;
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
        }

        return false;
    }

    public void evaluateExtraParams(OgnlValueStack stack) {
        if (listAttr != null) {
            addParam("list", MakeIterator.convert(stack.findValue(listAttr)));
        }

        if (listKeyAttr != null) {
            addParam("listKey", listKeyAttr);
        }

        if (listValueAttr != null) {
            addParam("listValue", listValueAttr);
        }
    }

    protected Class getValueClassType() {
        return null; // don't convert nameValue to anything, we need the raw value
    }
}

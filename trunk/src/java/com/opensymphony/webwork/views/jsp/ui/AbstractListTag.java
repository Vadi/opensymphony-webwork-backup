/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.util.ContainUtil;
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
        return ContainUtil.contains(obj1, obj2);
    }

    public void evaluateExtraParams(OgnlValueStack stack) {
        Object value = findValue(listAttr);

        if (listAttr != null) {
            if (value instanceof Collection) {
                addParameter("list", value);
                addParameter("listSize", new Integer(((Collection) value).size()));
            } else {
                addParameter("list", MakeIterator.convert(value));
            }
        }

        if (listKeyAttr != null) {
            addParameter("listKey", listKeyAttr);
        } else if (value instanceof Map) {
            addParameter("listKey", "key");
        }

        if (listValueAttr != null) {
            addParameter("listValue", listValueAttr);
        } else if (value instanceof Map) {
            addParameter("listValue", "value");
        }
    }

    protected Class getValueClassType() {
        return null; // don't convert nameValue to anything, we need the raw value
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.util.MakeIterator;
import com.opensymphony.webwork.util.ContainUtil;

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
        if (listAttr != null) {
            addParam("list", MakeIterator.convert(findValue(listAttr)));
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

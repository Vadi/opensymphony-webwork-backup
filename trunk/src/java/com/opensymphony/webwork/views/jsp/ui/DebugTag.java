/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlUtil;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * DebugTag
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class DebugTag extends AbstractUITag {
    public static final String TEMPLATE = "debug";

    private List stackValues;

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public List getStackValues() {
        return stackValues;
    }

    public int doStartTag() throws JspException {
        OgnlValueStack stack = getStack();
        Iterator iter = stack.getRoot().iterator();
        stackValues = new ArrayList(stack.getRoot().size());
        while (iter.hasNext()) {
            Object o = iter.next();
            Map values;
            try {
                values = OgnlUtil.getBeanMap(o);
            } catch (Exception e) {
                throw new JspException("Caught an exception while getting the property values of " + o, e);
            }
            stackValues.add(new DebugMapEntry(o.getClass().getName(),values));
        }
        return super.doStartTag();
    }

    private class DebugMapEntry implements Map.Entry {
        private Object key;
        private Object value;

        DebugMapEntry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        public Object getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public Object setValue(Object newVal) {
            Object oldVal = value;
            value = newVal;
            return oldVal;
        }
    }
}

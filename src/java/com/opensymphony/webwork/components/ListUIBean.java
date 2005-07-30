package com.opensymphony.webwork.components;

import com.opensymphony.webwork.util.MakeIterator;
import com.opensymphony.webwork.util.ContainUtil;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:58:43 AM
 */
public abstract class ListUIBean extends UIBean {
    protected String list;
    protected String listKey;
    protected String listValue;

    protected ListUIBean(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public void evaluateExtraParams() {
        Object value = findValue(list);

        if (list != null) {
            if (value instanceof Collection) {
                addParameter("list", value);
            } else {
                addParameter("list", MakeIterator.convert(value));
            }

            if (value instanceof Collection) {
                addParameter("listSize", new Integer(((Collection) value).size()));
            } else if (value instanceof Map) {
                addParameter("listSize", new Integer(((Map) value).size()));
            } else if (value != null && value.getClass().isArray()) {
                addParameter("listSize", new Integer(Array.getLength(value)));
            }
        }

        if (listKey != null) {
            addParameter("listKey", listKey);
        } else if (value instanceof Map) {
            addParameter("listKey", "key");
        }

        if (listValue != null) {
            addParameter("listValue", listValue);
        } else if (value instanceof Map) {
            addParameter("listValue", "value");
        }
    }

    public boolean contains(Object obj1, Object obj2) {
        return ContainUtil.contains(obj1, obj2);
    }

    protected Class getValueClassType() {
        return null; // don't convert nameValue to anything, we need the raw value
    }

    public void setList(String list) {
        this.list = list;
    }

    public void setListKey(String listKey) {
        this.listKey = listKey;
    }

    public void setListValue(String listValue) {
        this.listValue = listValue;
    }
}

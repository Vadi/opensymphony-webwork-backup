package com.opensymphony.webwork.components;

import com.opensymphony.webwork.util.ContainUtil;
import com.opensymphony.webwork.util.MakeIterator;
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
    protected Object list;
    protected String listKey;
    protected String listValue;

    protected ListUIBean(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public void evaluateExtraParams() {
        Object value = null;

        if (list == null) {
            list = parameters.get("list");
        }

        if (list instanceof String) {
            value = findValue((String) list);
        } else if (list instanceof Collection) {
            value = list;
        } else if (MakeIterator.isIterable(list)) {
            value = MakeIterator.convert(list);
        }

        if (value == null) {
            // will throw an exception if not found
            value = findValue((list == null) ? (String) list : list.toString(), "list",
                    "You must specify a collection/array/map/enumeration/iterator. " +
                    "Example: people or people.{name}");
        }

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

    public void setList(Object list) {
        this.list = list;
    }

    public void setListKey(String listKey) {
        this.listKey = listKey;
    }

    public void setListValue(String listValue) {
        this.listValue = listValue;
    }
}

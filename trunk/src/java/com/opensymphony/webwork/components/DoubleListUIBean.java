package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 8:20:18 AM
 */
public abstract class DoubleListUIBean extends ListUIBean {
    protected String doubleList;
    protected String doubleListKey;
    protected String doubleListValue;
    protected String doubleName;
    protected String doubleValue;
    protected String formName;

    public DoubleListUIBean(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        Object doubleName = null;

        if (this.doubleName != null) {
            addParameter("doubleName", findString(this.doubleName));
        }

        if (doubleList != null) {
            addParameter("doubleList", doubleList);
        }

        if (doubleListKey != null) {
            addParameter("doubleListKey", doubleListKey);
        }

        if (doubleListValue != null) {
            addParameter("doubleListValue", doubleListValue);
        }

        if (formName != null) {
            addParameter("formName", findString(formName));
        }

        Class valueClazz = getValueClassType();

        if (valueClazz != null) {
            if (doubleValue != null) {
                addParameter("doubleNameValue", findValue(doubleValue, valueClazz));
            } else if (doubleName != null) {
                addParameter("doubleNameValue", findValue(doubleName.toString(), valueClazz));
            }
        } else {
            if (doubleValue != null) {
                addParameter("doubleNameValue", findValue(doubleValue));
            } else if (doubleName != null) {
                addParameter("doubleNameValue", findValue(doubleName.toString()));
            }
        }
    }

    public String getDoubleList() {
        return doubleList;
    }

    public void setDoubleList(String doubleList) {
        this.doubleList = doubleList;
    }

    public String getDoubleListKey() {
        return doubleListKey;
    }

    public void setDoubleListKey(String doubleListKey) {
        this.doubleListKey = doubleListKey;
    }

    public String getDoubleListValue() {
        return doubleListValue;
    }

    public void setDoubleListValue(String doubleListValue) {
        this.doubleListValue = doubleListValue;
    }

    public String getDoubleName() {
        return doubleName;
    }

    public void setDoubleName(String doubleName) {
        this.doubleName = doubleName;
    }

    public String getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(String doubleValue) {
        this.doubleValue = doubleValue;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}

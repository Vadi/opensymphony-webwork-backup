/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.util.MakeIterator;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.util.Collection;

/**
 *
 *
 * @author <a href="mailto:m.bogaert@intrasoft.be">Mathias Bogaert</a>
 * @version $Revision$
 */
public abstract class AbstractDoubleListTag extends AbstractListTag {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected String doubleNameAttr;
    protected String doubleListAttr;
    protected String doubleListKeyAttr;
    protected String doubleListValueAttr;
    protected String doubleValueAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setDoubleName(String aName) {
        doubleNameAttr = aName;
    }
    
    public void setDoubleList(String list) {
        this.doubleListAttr = list;
    }

    public void setDoubleListKey(String listKey) {
        this.doubleListKeyAttr = listKey;
    }

    public void setDoubleListValue(String listValue) {
        this.doubleListValueAttr = listValue;
    }
    
    public void setDoubleValue(String doubleValue) {
        this.doubleValueAttr = doubleValue;
    }
    
    public void evaluateExtraParams(OgnlValueStack stack) {
        super.evaluateExtraParams(stack);
        
        Object doubleName = null;
        
        if (doubleNameAttr != null) {
            doubleName = findValue(doubleNameAttr, String.class);
            addParam("doubleName", doubleName);
        }
        
        if (doubleListAttr != null) {
            Object value = findValue(doubleListAttr);
            addParam("doubleList", MakeIterator.convert(value));
            if (value instanceof Collection) {
                addParam("doubleListSize", new Integer(((Collection) value).size()));
            }
        }

        if (doubleListKeyAttr != null) {
            addParam("doubleListKey", doubleListKeyAttr);
        }

        if (doubleListValueAttr != null) {
            addParam("doubleListValue", doubleListValueAttr);
        }
        
        Class valueClazz = getValueClassType();

        if (valueClazz != null) {
            if (doubleValueAttr != null) {
                addParam("doubleNameValue", findValue(doubleValueAttr, valueClazz));
            } else if (doubleName != null) {
                addParam("doubleNameValue", findValue(doubleName.toString(), valueClazz));
            }
        } else {
            if (doubleValueAttr != null) {
                addParam("doubleNameValue", findValue(doubleValueAttr));
            } else if (doubleName != null) {
                addParam("doubleNameValue", findValue(doubleName.toString()));
            }
        }
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * @author <a href="mailto:m.bogaert@memenco.com">Mathias Bogaert</a>
 */
public abstract class AbstractDoubleListTag extends AbstractListTag {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected String doubleList;
    protected String doubleListKey;
    protected String doubleListValue;
    protected String doubleName;
    protected String doubleValue;
    protected String formName;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setDoubleList(String list) {
        this.doubleList = list;
    }

    public void setDoubleListKey(String listKey) {
        this.doubleListKey = listKey;
    }

    public void setDoubleListValue(String listValue) {
        this.doubleListValue = listValue;
    }

    public void setDoubleName(String aName) {
        doubleName = aName;
    }

    public void setDoubleValue(String doubleValue) {
        this.doubleValue = doubleValue;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}

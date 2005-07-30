/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.DoubleSelect;
import com.opensymphony.webwork.components.DoubleListUIBean;


/**
 * @author <a href="mailto:m.bogaert@memenco.com">Mathias Bogaert</a>
 */
public abstract class AbstractDoubleListTag extends AbstractListTag {

    protected String doubleList;
    protected String doubleListKey;
    protected String doubleListValue;
    protected String doubleName;
    protected String doubleValue;
    protected String formName;

    protected void populateParams() {
        super.populateParams();

        DoubleListUIBean bean = ((DoubleSelect) this.bean);
        bean.setDoubleList(doubleList);
        bean.setDoubleListKey(doubleListKey);
        bean.setDoubleListValue(doubleListValue);
        bean.setDoubleName(doubleName);
        bean.setDoubleValue(doubleValue);
        bean.setFormName(formName);
    }

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

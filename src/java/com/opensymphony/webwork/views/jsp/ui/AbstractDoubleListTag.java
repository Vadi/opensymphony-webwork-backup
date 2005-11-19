/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.DoubleListUIBean;
import com.opensymphony.webwork.components.DoubleSelect;


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

        DoubleListUIBean bean = ((DoubleSelect) this.component);
        bean.setDoubleList(doubleList);
        bean.setDoubleListKey(doubleListKey);
        bean.setDoubleListValue(doubleListValue);
        bean.setDoubleName(doubleName);
        bean.setDoubleValue(doubleValue);
        bean.setFormName(formName);
    }

    /**
     * @jsp.attribute required="true"  rtexprvalue="true"
     */
    public void setDoubleList(String list) {
        this.doubleList = list;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setDoubleListKey(String listKey) {
        this.doubleListKey = listKey;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setDoubleListValue(String listValue) {
        this.doubleListValue = listValue;
    }

    /**
     * @jsp.attribute required="true"  rtexprvalue="true"
     */
    public void setDoubleName(String aName) {
        doubleName = aName;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setDoubleValue(String doubleValue) {
        this.doubleValue = doubleValue;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setFormName(String formName) {
        this.formName = formName;
    }
}

/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;


/**
 *
 *
 * @author <a href="mailto:m.bogaert@intrasoft.be">Mathias Bogaert</a>
 * @version $Revision$
 */
public abstract class AbstractDoubleListTag extends AbstractListTag {
    //~ Instance fields ////////////////////////////////////////////////////////

    private Object doubleListValue;
    private Object doubleName;
    private String doubleList;
    private String doubleListKey;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setDoubleList(String doubleList) {
        this.doubleList = doubleList;
    }

    public String getDoubleList() {
        return doubleList;
    }

    public void setDoubleListKey(String doubleListKey) {
        this.doubleListKey = doubleListKey;
    }

    public String getDoubleListKey() {
        return doubleListKey;
    }

    public void setDoubleListValue(Object doubleListValue) {
        this.doubleListValue = doubleListValue;
    }

    public Object getDoubleListValue() {
        return doubleListValue;
    }

    public void setDoubleName(Object doubleName) {
        this.doubleName = doubleName;
    }

    public Object getDoubleName() {
        return doubleName;
    }
}

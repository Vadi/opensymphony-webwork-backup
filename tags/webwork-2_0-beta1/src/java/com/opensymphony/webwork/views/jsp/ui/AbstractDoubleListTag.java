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

    public Object DoubleList(String list) {
        setDoubleList(list);

        return this;
    }

    public Object DoubleListKey(String listKey) {
        setDoubleListKey(listKey);

        return this;
    }

    public Object DoubleListValue(Object listValue) {
        this.setDoubleListValue(listValue);

        return this;
    }

    /**
     * simple setter method for Velocity
     * @param name
     */
    public Object DoubleName(Object doubleName) {
        setDoubleName(doubleName);

        return this;
    }

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        doubleName = null;
        doubleListValue = null;
        doubleList = null;
        doubleListKey = null;
    }
}

/*
 * Copyright (c) 2002-2005 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.ListUIBean;

/**
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 * @version $Id$
 */
public abstract class AbstractListTag extends AbstractUITag {
    protected String list;
    protected String listKey;
    protected String listValue;

    protected void populateParams() {
        super.populateParams();

        ListUIBean listUIBean = ((ListUIBean) component);
        listUIBean.setList(list);
        listUIBean.setListKey(listKey);
        listUIBean.setListValue(listValue);
    }

    /**
     * @jsp.attribute required="true"  rtexprvalue="true"
     */
    public void setList(String list) {
        this.list = list;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setListKey(String listKey) {
        this.listKey = listKey;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setListValue(String listValue) {
        this.listValue = listValue;
    }
}

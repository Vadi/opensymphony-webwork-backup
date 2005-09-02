/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Submit;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @see Submit
 */
public class SubmitTag extends AbstractUITag {
    protected String align;
    protected String resultDivId;
    protected String onLoadJS;
    protected String notifyTopics;
    protected String listenTopics;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Submit(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        ((Submit) component).setAlign(align);
        ((Submit) component).setResultDivId(resultDivId);
        ((Submit) component).setOnLoadJS(onLoadJS);
        ((Submit) component).setNotifyTopics(notifyTopics);
        ((Submit) component).setListenTopics(listenTopics);
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setResultDivId(String resultDivId) {
        this.resultDivId = resultDivId;
    }

    public void setOnLoadJS(String onLoadJS) {
        this.onLoadJS = onLoadJS;
    }

    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }

    public void setListenTopics(String listenTopics) {
        this.listenTopics = listenTopics;
    }
}

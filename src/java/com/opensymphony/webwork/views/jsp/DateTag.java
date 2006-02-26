package com.opensymphony.webwork.views.jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Date;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @see Date
 */
public class DateTag extends TextTag {

    protected String name;
    protected String format;
    protected boolean nice;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Date(stack);
    }

    protected void populateParams() {
        super.populateParams();

        ((Date) component).setName(name);
        ((Date) component).setFormat(format);
        ((Date) component).setNice(nice);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setNice(boolean nice) {
        this.nice = nice;
    }
}

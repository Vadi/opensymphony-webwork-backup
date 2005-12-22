package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Head;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @see Head
 */
public class HeadTag extends AbstractUITag {

    private String calendarcss;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Head(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();
        if (calendarcss != null) {
        	((Head) component).setCalendarcss(calendarcss);
        }
    }

    public String getCalendarcss() {
        return calendarcss;
    }

    public void setCalendarcss(String calendarcss) {
        this.calendarcss = calendarcss;
    }
}

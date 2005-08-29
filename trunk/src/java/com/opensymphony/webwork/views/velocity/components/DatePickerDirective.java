package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.DatePicker;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Aug 7, 2005
 * Time: 5:47:08 PM
 */
public class DatePickerDirective extends TextFieldDirective {
    protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new DatePicker(stack, req, res);
    }

    public String getBeanName() {
        return "datepicker";
    }
}

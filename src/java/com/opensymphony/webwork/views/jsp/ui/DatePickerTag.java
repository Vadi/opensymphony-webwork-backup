package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.DatePicker;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Aug 7, 2005
 * Time: 5:45:58 PM
 */
public class DatePickerTag extends TextFieldTag {
    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new DatePicker(stack, req, res);
    }
}

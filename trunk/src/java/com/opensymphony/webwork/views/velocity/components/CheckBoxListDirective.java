package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.CheckboxList;
import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: patrick
 * Date: Aug 5, 2005
 * Time: 8:58:20 AM
 */
public class CheckBoxListDirective extends AbstractDirective {
    protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new CheckboxList(stack, req, res);
    }

    public String getBeanName() {
        return "checkboxlist";
    }
}

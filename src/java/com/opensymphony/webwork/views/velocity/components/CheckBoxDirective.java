package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.Checkbox;
import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: patrick
 * Date: Aug 5, 2005
 * Time: 8:58:09 AM
 */
public class CheckBoxDirective extends AbstractDirective {
    protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Checkbox(stack, req, res);
    }

    public String getBeanName() {
        return "checkbox";
    }
}

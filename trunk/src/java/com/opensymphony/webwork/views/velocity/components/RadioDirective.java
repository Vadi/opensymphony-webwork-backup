package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Radio;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: patrick
 * Date: Aug 5, 2005
 * Time: 8:56:10 AM
 */
public class RadioDirective extends AbstractDirective {
    public String getBeanName() {
        return "radio";
    }

    protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Radio(stack, req, res);
    }
}

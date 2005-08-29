package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.ActionComponent;
import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Aug 28, 2005
 * Time: 6:13:14 PM
 */
public class ActionDirective extends AbstractDirective {
    public String getBeanName() {
        return "action";
    }

    protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new ActionComponent(stack, req, res);
    }
}

package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.TextArea;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: patrick
 * Date: Aug 5, 2005
 * Time: 8:55:39 AM
 */
public class TextAreaDirective extends AbstractDirective {
    public String getBeanName() {
        return "textarea";
    }

    protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new TextArea(stack, req, res);
    }
}

package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.Div;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ian Roughley
 * @version $Id$
 */
public class DivDirective extends AbstractDirective {
    public String getBeanName() {
        return "div";
    }

    protected UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Div(stack, req, res);
    }
}

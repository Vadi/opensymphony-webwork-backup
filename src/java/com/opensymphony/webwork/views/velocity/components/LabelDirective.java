package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.Label;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: patrick
 * Date: Aug 5, 2005
 * Time: 8:56:25 AM
 */
public class LabelDirective extends AbstractDirective {
    public String getBeanName() {
        return "label";
    }

    protected UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Label(stack, req, res);
    }
}

package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.Select;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: patrick
 * Date: Aug 5, 2005
 * Time: 8:56:02 AM
 */
public class SelectDirective extends AbstractDirective {
    public String getBeanName() {
        return "select";
    }

    protected UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Select(stack, req, res);
    }
}

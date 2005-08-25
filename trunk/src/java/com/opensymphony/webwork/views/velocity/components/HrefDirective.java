package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.Href;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ian Roughley
 * @version $Id$
 */
public class HrefDirective extends AbstractDirective {
    public String getBeanName() {
        return "href";
    }

    protected UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Href(stack, req, res);
    }

    public int getType() {
        return BLOCK;
    }
}

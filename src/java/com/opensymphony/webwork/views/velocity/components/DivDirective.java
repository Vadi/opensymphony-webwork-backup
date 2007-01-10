package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Div;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @see Div
 */
public class DivDirective extends AbstractDirective {
    public String getBeanName() {
        return "div";
    }

    protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Div(stack, req, res);
    }
    
    /**
     * A Div component, is really a Block element, hence overriding here to
     * return a BLOCK.
     */
    public int getType() {
        return BLOCK;
    }
}

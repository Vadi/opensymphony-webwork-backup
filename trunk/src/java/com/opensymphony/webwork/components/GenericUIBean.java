package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.util.ContainUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 8:15:46 AM
 */
public class GenericUIBean extends UIBean {
    private final static String TEMPLATE = "empty";

    public GenericUIBean(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public boolean contains(Object obj1, Object obj2) {
        return ContainUtil.contains(obj1, obj2);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}

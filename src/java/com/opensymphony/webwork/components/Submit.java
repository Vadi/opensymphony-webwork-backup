package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 6:46:38 AM
 */
public class Submit extends UIBean {
    final public static String TEMPLATE = "submit";

    protected String align;

    public Submit(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateParams() {
        if (align == null) {
            align = "right";
        }

        if (value == null) {
            value = "Submit";
        }

        super.evaluateParams();

        addParameter("align", findString(align));

        Form form = (Form) findAncestor(Form.class);
        if (form != null) {
            addParameter("formId", ((Form) form).getId());
        }
    }

    public void setAlign(String align) {
        this.align = align;
    }
}

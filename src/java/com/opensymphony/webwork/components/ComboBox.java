package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 8:13:41 AM
 */
public class ComboBox extends TextField {
    final public static String TEMPLATE = "combobox";

    protected String list;

    public ComboBox(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (list != null) {
            addParameter("list", findValue(list));
        }
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}

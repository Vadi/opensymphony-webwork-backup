package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.webwork.components.ComboBox;
import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Aug 1, 2005
 * Time: 8:46:40 PM
 */
public class ComboBoxModel extends TagModel {
    public ComboBoxModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack, req, res);
    }

    protected Component getBean() {
        return new ComboBox(stack, req, res);
    }
}

package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.TextField;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Jul 18, 2005
 * Time: 8:00:08 PM
 */
public class TextFieldModel extends TagModel {
    public TextFieldModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack, req, res);
    }

    protected UIBean getBean() {
        return new TextField(stack, req, res);
    }
}

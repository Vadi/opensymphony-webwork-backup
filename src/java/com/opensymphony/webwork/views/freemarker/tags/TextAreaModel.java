package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.TextArea;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Aug 1, 2005
 * Time: 8:48:47 PM
 */
public class TextAreaModel extends TagModel {
    public TextAreaModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack, req, res);
    }

    protected UIBean getBean() {
        return new TextArea(stack, req, res);
    }
}

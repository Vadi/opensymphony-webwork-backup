package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:10:12 AM
 */
public class File extends UIBean {
    final public static String TEMPLATE = "file";

    protected String accept;
    protected String size;

    public File(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateParams() {
        super.evaluateParams();

        if (accept != null) {
            addParameter("accept", findString(accept));
        }

        if (size != null) {
            addParameter("size", findString(size));
        }
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public void setSize(String size) {
        this.size = size;
    }
}

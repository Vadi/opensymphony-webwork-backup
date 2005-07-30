package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:12:39 AM
 */
public class Label extends UIBean {
    final public static String TEMPLATE = "label";

    protected String forAttr;

    public Label(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (forAttr != null) {
            addParameter("for", findString(forAttr));
        }

        // try value first, then name (this overrides the default behavior in the superclass)
        if (value != null) {
            addParameter("nameValue", findString(value));
        } else if (name != null) {
            String expr = name;
            if (ALT_SYNTAX) {
                expr = "%{" + expr + "}";
            }

            addParameter("nameValue", findString(expr));
        }
    }

    public void setFor(String forAttr) {
        this.forAttr = forAttr;
    }
}

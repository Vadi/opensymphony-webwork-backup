/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.views.velocity.Renderer;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.velocity.context.Context;

import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class ParamTag extends TagSupport implements Renderer {
    //~ Instance fields ////////////////////////////////////////////////////////

    String name;
    String value;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int doStartTag() throws JspException {
        ParameterizedTag parent = (ParameterizedTag) findAncestorWithClass(this, ParameterizedTag.class);

        if (parent != null) {
            OgnlValueStack vs = ActionContext.getContext().getValueStack();

            if (vs != null) {
                Object o = null;

                if (value == null) {
                    o = vs.getRoot().peek();
                } else {
                    o = vs.findValue(value);
                }

                parent.addParam(name, o);

                /**
                * If the page has been accessed directly and there is no ValueStack, we should just add the name/value pair
                * to the parent as is
                */
            } else {
                parent.addParam(name, value);
            }
        }

        return SKIP_BODY;
    }

    /**
    * Clears all the instance variables to allow this instance to be reused.
    */
    public void release() {
        super.release();
        this.name = null;
        this.value = null;
    }

    public void render(Context context, Writer writer) throws Exception {
        doStartTag();
    }
}

package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.io.Writer;

/**
 * User: plightbo
 * Date: Sep 1, 2005
 * Time: 7:54:06 PM
 */
public class Push extends Component {
    protected String value;
    protected boolean pushed;

    public Push(OgnlValueStack stack) {
        super(stack);
    }

    public void start(Writer writer) {
        super.start(writer);

        OgnlValueStack stack = getStack();

        if (stack != null) {
            stack.push(findValue(value));
            pushed = true;
        } else {
            pushed = false; // need to ensure push is assigned, otherwise we may have a leftover value
        }
    }

    public void end(Writer writer, String body) {
        OgnlValueStack stack = getStack();

        if (pushed && (stack != null)) {
            stack.pop();
        }

        super.end(writer, body);
    }

    public void setValue(String value) {
        this.value = value;
    }
}

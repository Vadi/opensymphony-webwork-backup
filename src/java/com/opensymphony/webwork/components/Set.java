package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.io.Writer;

/**
 * User: plightbo
 * Date: Sep 1, 2005
 * Time: 7:53:56 PM
 */
public class Set extends Component {
    protected String name;
    protected String scope;
    protected String value;

    public Set(OgnlValueStack stack) {
        super(stack);
    }

    public void end(Writer writer, String body) {
        OgnlValueStack stack = getStack();

        if (value == null) {
            value = "top";
        }

        Object o = findValue(value);

        String name;
        if (altSyntax()) {
            name = findString(this.name, "name", "Name is required");
        } else {
            name = this.name;

            if (this.name == null) {
                throw fieldError("name", "Name is required", null);
            }
        }

        if ("application".equalsIgnoreCase(scope)) {
            stack.setValue("#application['" + name + "']", o);
        } else if ("session".equalsIgnoreCase(scope)) {
            stack.setValue("#session['" + name + "']", o);
        } else if ("request".equalsIgnoreCase(scope)) {
            stack.setValue("#request['" + name + "']", o);
        } else if ("page".equalsIgnoreCase(scope)) {
            stack.setValue("#attr['" + name + "']", o, false);
        } else {
            stack.getContext().put(name, o);
        }

        super.end(writer, body);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

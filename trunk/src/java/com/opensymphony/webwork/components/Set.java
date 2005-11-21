package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.io.Writer;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * The set tag assigns a value to a variable in a specified scope. It is useful when you wish to assign a variable to a
 * complex expression and then simply reference that variable each time rather than the complex expression. This is
 * useful in both cases: when the complex expression takes time (performance improvement) or is hard to read (code
 * readability improvement).
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Parameters</b>
 *
 * <!-- START SNIPPET: params -->
 *
 * <ul>
 *
 * <li>name* (String): The name of the new variable that is assigned the value of <i>value</i></li>
 *
 * <li>value (Object): The value that is assigned to the variable named <i>name</i></li>
 *
 * <li>scope (String): The scope in which to assign the variable. Can be <b>application</b>, <b>session</b>,
 * <b>request</b>, <b>page</b>, or <b>action</b>. By default it is <b>action</b>.</li>
 *
 * </ul>
 *
 * <!-- END SNIPPET: params -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:set name="personName" value="person.name"/&gt;
 * Hello, &lt;ww:property value="#personName"/&gt;. How are you?
 * <!-- END SNIPPET: example -->
 * </pre>
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

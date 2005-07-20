package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.config.Configuration;

import java.util.Stack;
import java.util.Iterator;
import java.util.Map;
import java.io.Writer;

/**
 * User: plightbo
 * Date: Jul 2, 2005
 * Time: 5:52:26 PM
 */
public class Component {
    public static final boolean ALT_SYNTAX = "true".equals(Configuration.getString("webwork.tag.altSyntax"));
    public static final String COMPONENT_STACK = "__component_stack";

    protected OgnlValueStack stack;

    public Component(OgnlValueStack stack) {
        this.stack = stack;
        stack.push(this);
        getComponentStack().push(this);
    }

    public OgnlValueStack getStack() {
        return stack;
    }

    private Stack getComponentStack() {
        Stack componentStack = (Stack) stack.getContext().get(COMPONENT_STACK);
        if (componentStack == null) {
            componentStack = new Stack();
            stack.getContext().put(COMPONENT_STACK, componentStack);
        }
        return componentStack;
    }

    public void start(Writer writer) {
    }

    public void end(Writer writer) {
        getComponentStack().pop();
        stack.pop();
    }

    public Component findAncestor(Class clazz) {
        Stack componentStack = getComponentStack();
        for (Iterator iterator = componentStack.iterator(); iterator.hasNext();) {
            Component component = (Component) iterator.next();
            if (component.getClass().isAssignableFrom(clazz)) {
                return component;
            }
        }

        return null;
    }

    protected String findString(String expr) {
        return (String) findValue(expr, String.class);
    }

    protected Object findValue(String expr) {
        if (ALT_SYNTAX) {
            // does the expression start with %{ and end with }? if so, just cut it off!
            if (expr.startsWith("%{") && expr.endsWith("}")) {
                expr = expr.substring(2, expr.length() - 1);
            }
        }

        return getStack().findValue(expr);
    }

    protected Object findValue(String expr, Class toType) {
        if (ALT_SYNTAX && toType == String.class) {
            return translateVariables(expr, getStack());
        } else {
            if (ALT_SYNTAX) {
                // does the expression start with %{ and end with }? if so, just cut it off!
                if (expr.startsWith("%{") && expr.endsWith("}")) {
                    expr = expr.substring(2, expr.length() - 1);
                }
            }

            return getStack().findValue(expr, toType);
        }
    }

    public static String translateVariables(String expression, OgnlValueStack stack) {
        while (true) {
            int x = expression.indexOf("%{");
            int y = expression.indexOf("}", x);

            if ((x != -1) && (y != -1)) {
                String var = expression.substring(x + 2, y);

                Object o = stack.findValue(var, String.class);

                if (o != null) {
                    expression = expression.substring(0, x) + o + expression.substring(y + 1);
                } else {
                    // the variable doesn't exist, so don't display anything
                    expression = expression.substring(0, x) + expression.substring(y + 1);
                }
            } else {
                break;
            }
        }

        return expression;
    }

    public void copyParams(Map params) {
        for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            stack.setValue(key, entry.getValue());
        }
    }

}
